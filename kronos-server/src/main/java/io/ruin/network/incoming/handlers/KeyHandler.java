package io.ruin.network.incoming.handlers;

import io.ruin.network.io.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = { 40 })
public class KeyHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        in.skip(1);//junk
        int lastKeystroke = in.readUnsignedShort();//correct
        int lastKeyID = in.readUnsignedByteS();//correct

        if (lastKeyID == 82 && player.get("ctrlSound") != null) {
            Integer sound = player.get("ctrlSound", 0);
            player.sendMessage("playing soundId="+sound);
            player.privateSound(sound);
            player.set("ctrlSound", sound + 1);
            return;
        }
    }

    private void handleKey(Player player, int key) {
        //if (key >= 1 && key <= 13) {
           // int tab = Config.KEYBINDS[key].get(player);
           // player.sendMessage("  tab=" + tab);
       //     /* keybind keys */
       // }
    }

}