package uk.qub.se.board.area.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.qub.se.board.area.Area;
import uk.qub.se.utils.ReflectionsFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class AreaFactory {

    private final Map<String, AreaFactoryMethod> map = new ConcurrentHashMap<>();
    private final ReflectionsFactory reflectionsFactory;

    public AreaFactory(final ReflectionsFactory reflectionsFactory) {
        if (reflectionsFactory == null) {
            throw new IllegalArgumentException("Reflections factory may not be null");
        }

        this.reflectionsFactory = reflectionsFactory;
        registerAreaFactories();
    }

    private void registerAreaFactories() {
        final Set<Class<? extends Area>> areas = reflectionsFactory.findDerivedClasses(Area.class);
        for (final Class<? extends Area> area : areas) {
            registerAreaFactory(area);
        }
    }

    private void registerAreaFactory(final Class<? extends Area> area) {
        if (area.isInterface()) {
            return;
        }

        try {
            area.getMethod(Area.REGISTRAR_METHOD_NAME, AreaFactory.class).invoke(null, this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Error while initializing factory", e);
        }
    }

    public void registerFactory(final String key, final AreaFactoryMethod factory) {
        if (key == null || factory == null){
            return;
        }

        map.put(key, factory);
    }

    public Area construct(final String key, final String json, final ObjectMapper mapper) {
        if (key == null) {
            throw new IllegalArgumentException("Key may not be null");
        }

        final AreaFactoryMethod factory = getAreaFactoryMethod(key);
        return constructArea(key, json, mapper, factory);
    }

    private Area constructArea(final String key, final String json, final ObjectMapper mapper, final AreaFactoryMethod factory) {
        try {
            return factory.construct(json, mapper);
        } catch (JsonProcessingException e) {
            throw new AreaConstructionException("Error while constructing area with key: " + key, e);
        }
    }

    private AreaFactoryMethod getAreaFactoryMethod(final String key) {
        final AreaFactoryMethod factory = map.getOrDefault(key, null);
        if (factory == null) {
            throw new IllegalArgumentException("No factory for key " + key + "registered");
        }

        return factory;
    }
}
