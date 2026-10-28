package io.ruin.process.event;

import kilim.Fiber;
import kilim.Pausable;
import kilim.Task;

@FunctionalInterface
public interface EventConsumer {

    void accept(Event event) throws Pausable;

    default void accept(Event var1, Fiber var2) throws Pausable {
        Task.errNotWoven();
    }

}