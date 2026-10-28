package io.ruin.network;

import io.ruin.model.entity.player.Player;
import java.util.ArrayList;

public class ClientScriptBuilder {
    private int scriptId;
    private String types;
    private ArrayList<Object> values;

    public ClientScriptBuilder(int scriptId) {
        this.scriptId = scriptId;
        this.types = "";
        this.values = new ArrayList();
    }

    public ClientScriptBuilder addInt(int value) {
        this.types = this.types + "i";
        this.values.add(value);
        return this;
    }

    public ClientScriptBuilder addString(String value) {
        this.types = this.types + "s";
        this.values.add(value);
        return this;
    }

    public ClientScriptBuilder addBoolean(boolean value) {
        String var10001 = this.types;
        this.types = var10001 + "i";
        this.values.add(value ? 1 : 0);
        return this;
    }

    public ClientScriptBuilder addWidget(int parentId, int childId) {
        return this.addWidget(parentId << 16 | childId);
    }

    public ClientScriptBuilder addWidget(int hash) {
        this.types = this.types + "I";
        this.values.add(hash);
        return this;
    }

    public ClientScriptBuilder addInventory(int parentId, int childId) {
        return this.addInventory(parentId << 16 | childId);
    }

    public ClientScriptBuilder addInventory(int hash) {
        this.types = this.types + "v";
        this.values.add(hash);
        return this;
    }

    public void send(Player player) {
        player.getPacketSender().sendClientScript(this.scriptId, this.types, this.values.toArray());
        this.values = null;
        this.types = null;
    }
}
