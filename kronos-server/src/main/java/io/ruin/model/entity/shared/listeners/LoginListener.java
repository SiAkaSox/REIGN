package io.ruin.model.entity.shared.listeners;

import com.google.api.client.util.Lists;
import io.ruin.model.entity.player.Player;

import java.util.ArrayList;

public interface LoginListener {

    ArrayList<LoginListener> LISTENERS = Lists.newArrayList();

    void onLogin(Player player);

    static void register(LoginListener listener) {
        LISTENERS.add(listener);
        LISTENERS.trimToSize(); //only okay because on startup
    }

    static void executeAll(Player player) {
        for(LoginListener listener : LISTENERS) {
            if (listener == null)
                continue;
            listener.onLogin(player);
        }
    }

}