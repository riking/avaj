package org.riking.home._42.avaj;

import java.util.ArrayList;
import java.util.List;

public class Tower {
    private List<Flyable> observers;
    private List<Flyable> toRemove;

    public Tower() {
        this.observers = new ArrayList<>();
    }

    public void register(Flyable flyable) {
        this.logf("%s registered to weather tower.", flyable.getDescriptor());
        observers.add(flyable);
    }
    public void unregister(Flyable flyable) {
        this.logf("%s unregistered from weather tower.", flyable.getDescriptor());
        toRemove.add(flyable);
    }

    protected void conditionsChanged() {
        toRemove = new ArrayList<>();
        for (Flyable flyable : observers) {
            flyable.updateConditions();
        }
        observers.removeAll(toRemove);
        this.toRemove = null;
    }

    private void log(String message) {
        Logger.getLogger().getOutput().printf("Tower says: %s\n", message);
    }

    private void logf(String format, Object... args) {
        Logger.getLogger().getOutput().printf("Tower says: " + format + "\n", args);
    }
}
