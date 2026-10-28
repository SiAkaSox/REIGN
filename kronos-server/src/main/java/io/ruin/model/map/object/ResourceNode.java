package io.ruin.model.map.object;

import io.ruin.model.map.Position;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ReverendDread
 * Aug 17, 2019
 */
public class ResourceNode extends GlobalObject {

    /**
     * The amount of resources remaining in the node.
     */
    @Getter @Setter private int remainingResources;

    /**
     *
     * @param id
     * @param location
     */
    public ResourceNode(int id, int resourceAmount, Position location) {
        super(id, location);
        this.remainingResources = resourceAmount;
    }

    /**
     * Returns if the node has any amount of resources remaining.
     * @return
     */
    public boolean hasResources() {
        return getRemainingResources() > 0;
    }

    /**
     * Deincrements the resources by one, and returns the amount remaining.
     * @return
     */
    public int deincrementAndGet() {
        return remainingResources--;
    }

    /**
     * Increments the resources by one, and returns the amount remaining.
     * @return
     */
    public int incrementAndGet() {
        return remainingResources++;
    }

}
