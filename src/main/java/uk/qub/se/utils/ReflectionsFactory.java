package uk.qub.se.utils;

import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.SubTypes;

public class ReflectionsFactory {

    public <T> Set<Class<? extends T>> findDerivedClasses(final Class<T> clazz) {
        if (clazz == null) {
            return null;
        }

        final var reflections = new Reflections(clazz.getPackageName());
        final Set<Class<?>> derivedClasses = reflections.get(SubTypes.of(clazz).asClass());
        //noinspection unchecked
        return derivedClasses.stream()
                .map(derivedClass -> (Class<? extends T>) derivedClass)
                .collect(Collectors.toSet());
    }
}
