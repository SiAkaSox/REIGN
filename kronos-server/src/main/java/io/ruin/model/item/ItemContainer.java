package io.ruin.model.item;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class ItemContainer extends ItemContainerG<Item> {

    @Override
    protected Item newItem(int id, int amount, Map<String, String> attributes) {
        return new Item(id, amount, attributes);
    }

    @Override
    protected Item[] newArray(int size) {
        return new Item[size];
    }

    public int getNumberOf(int itemId) {
        return this.getNumberOf(itemId, false);
    }

    public int getNumberOf(int itemId, boolean ignoreUniques) {
        int amount = 0;
        Iterator var4 = Arrays.stream(this.items).iterator();

        while(true) {
            Item item;
            do {
                do {
                    if (!var4.hasNext()) {
                        return amount;
                    }

                    item = (Item)var4.next();
                } while(item == null);
            } while(!ignoreUniques && item.getUniqueValue() != 0);

            if (item.getId() == itemId) {
                amount += item.getAmount();
            }
        }
    }

}
