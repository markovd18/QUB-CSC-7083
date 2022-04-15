package uk.qub.se.board.area;

import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.board.area.random.ActionConstructionException;
import uk.qub.se.board.area.random.RandomAction;
import uk.qub.se.player.Player;
import uk.qub.se.utils.ReflectionsFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RandomEventArea implements Area {

    private List<RandomAction> actions;
    private Iterator<RandomAction> previousEvent;
    private ReflectionsFactory reflectionsFactory;

    public RandomEventArea() {
        initiate();
    }

    private void initiate() {
        actions = new ArrayList<>();
        fillActions();

        Collections.shuffle(actions);
        previousEvent = actions.iterator();
    }

    @Override
    public BoardMovementResult acceptPlayer(final Player player) {
        if (player == null) {
            return null;
        }

        if (!previousEvent.hasNext()) {
            previousEvent = actions.iterator();
        }

        return previousEvent.next().initiate(player);
    }

    @Override
    public void validate() {
        //??
    }

    @SuppressWarnings("unused")
    public static void registerToFactory(final AreaFactory factory) {
        if (factory == null) {
            return;
        }

        factory.registerFactory("random", (json, mapper) -> new RandomEventArea());
    }

    private void fillActions() {
        final Set<Class<? extends RandomAction>> actionClasses = getActionClasses();
        for (var clazz : actionClasses) {
            actions.add(createActionInstance(clazz));
        }
    }

    private Set<Class<? extends RandomAction>> getActionClasses() {
        if (reflectionsFactory == null) {
            reflectionsFactory = new ReflectionsFactory();
        }

        return reflectionsFactory.findDerivedClasses(RandomAction.class);
    }

    private RandomAction createActionInstance(final Class<? extends RandomAction> clazz)  {
        try {
            final Constructor<? extends RandomAction> constructor =  clazz.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ActionConstructionException("Error while constructing random action instance", e);
        }
    }
}
