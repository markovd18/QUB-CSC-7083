package uk.qub.se.board.area.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import uk.qub.se.board.area.Area;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.reflections.scanners.Scanners.SubTypes;


public class AreaFactory {

    private final Map<String, AreaFactoryMethod> map = new ConcurrentHashMap<>();
    private static AreaFactory instance;

    public synchronized static AreaFactory getInstance() {
        if (instance == null) {
            instance = new AreaFactory();
            instance.initialize();
        }
        return instance;
    }

    private void initialize() {
        Reflections reflections = new Reflections("uk.qub.se.board.area");
        Set<Class<?>> areas =
                reflections.get(SubTypes.of(Area.class).asClass());
        for (Class<?> area : areas) {
            try {
                area.getMethod(Area.REGISTRAR_METHOD_NAME).invoke(null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Error while initializing factory: " + e.getMessage());
                System.exit(3);
            }
        }
    }

    private AreaFactory() {
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

        var factory = map.getOrDefault(key, null);
        if (factory == null) {
            throw new IllegalArgumentException("No factory for key " + key + "registered");
        }

        try {
            return factory.construct(json, mapper);
        } catch (JsonProcessingException e) {
            throw new AreaConstructionException("Error while constructing area with key: " + key, e);
        }
    }
}
