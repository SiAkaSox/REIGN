package io.ruin.network.incoming;

import io.ruin.Server;
import io.ruin.network.io.InBuffer;
import io.ruin.utility.PackageLoader;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.IdHolder;

import static io.ruin.Server.STARTUP_THREAD;

public interface Incoming {

    Incoming[] HANDLERS = new Incoming[256];

    int[] OPTIONS = new int[256];

    boolean[] IGNORED = new boolean[256];

    int[] SIZES = new int[256];

    static void load() throws Exception {
        try {
            for (Class c : PackageLoader.load("io.ruin.network.incoming.handlers", Incoming.class)) {
                Incoming incoming = (Incoming) c.newInstance();
                IdHolder idHolder = (IdHolder) c.getAnnotation(IdHolder.class);
                if (idHolder == null) {
                    /* handler is disabled, most likely for upgrading */
                    continue;
                }
                int option = 1;
                for (int id : idHolder.ids()) {
                    HANDLERS[id] = incoming;
                    OPTIONS[id] = option++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * Ignored
         */
        int[] ignored = {
                3,//empty, looks like ping packet.
                4,//something to do with the client feature 'mouserecorder'
                23,//just pending packet writes (empty packet)
                41//never called even when unchecked.. it's used for idling, sends runelite notification for logout, resets when your mouse moves within the frame.

        };
        for (int opcode : ignored)
            IGNORED[opcode] = true;
        /**
         * Sizes
         */
        for (int i = 0; i < SIZES.length; i++)
            SIZES[i] = Byte.MIN_VALUE;
        SIZES[0] = 4;
        SIZES[1] = 6;
        SIZES[2] = 8;
        SIZES[3] = -1;
        SIZES[4] = -1;
        SIZES[5] = 8;
        SIZES[6] = 7;
        SIZES[7] = 9;
        SIZES[8] = 4;
        SIZES[9] = 2;
        SIZES[10] = 8;
        SIZES[11] = 11;
        SIZES[12] = -1;
        SIZES[13] = 0;
        SIZES[14] = 8;
        SIZES[15] = 1;
        SIZES[16] = 8;
        SIZES[17] = -1;
        SIZES[18] = 7;
        SIZES[19] = 8;
        SIZES[20] = 8;
        SIZES[21] = 3;
        SIZES[22] = -1;
        SIZES[23] = 0;
        SIZES[24] = 7;
        SIZES[25] = 4;
        SIZES[26] = 8;
        SIZES[27] = 7;
        SIZES[28] = 8;
        SIZES[29] = 8;
        SIZES[30] = 8;
        SIZES[31] = 8;
        SIZES[32] = -1;
        SIZES[33] = 8;
        SIZES[34] = 13;
        SIZES[35] = 3;
        SIZES[36] = 2;
        SIZES[37] = 3;
        SIZES[38] = 9;
        SIZES[39] = -1;
        SIZES[40] = -2;
        SIZES[41] = 0;
        SIZES[42] = 7;
        SIZES[43] = 3;
        SIZES[44] = 13;
        SIZES[45] = -1;
        SIZES[46] = 17;
        SIZES[47] = 8;
        SIZES[48] = -1;
        SIZES[49] = 0;
        SIZES[50] = 7;
        SIZES[51] = 7;
        SIZES[52] = 3;
        SIZES[53] = -1;
        SIZES[54] = 2;
        SIZES[55] = 9;
        SIZES[56] = -1;
        SIZES[57] = 0;
        SIZES[58] = 3;
        SIZES[59] = 11;
        SIZES[60] = 8;
        SIZES[61] = 3;
        SIZES[62] = 14;
        SIZES[63] = 7;
        SIZES[64] = 16;
        SIZES[65] = 8;
        SIZES[66] = 8;
        SIZES[67] = -1;
        SIZES[68] = 15;
        SIZES[69] = 4;
        SIZES[70] = 8;
        SIZES[71] = 3;
        SIZES[72] = 5;
        SIZES[73] = 8;
        SIZES[74] = 7;
        SIZES[75] = 10;
        SIZES[76] = 8;
        SIZES[77] = 9;
        SIZES[78] = 3;
        SIZES[79] = 3;
        SIZES[80] = -1;
        SIZES[81] = 3;
        SIZES[82] = 15;
        SIZES[83] = 2;
        SIZES[84] = -1;
        SIZES[85] = 16;
        SIZES[86] = -2;
        SIZES[87] = 8;
        SIZES[88] = 8;
        SIZES[89] = 3;
        SIZES[90] = 3;
        SIZES[91] = -1;
        SIZES[92] = -1;
        SIZES[93] = -2;
        SIZES[94] = 16;
        SIZES[95] = 7;
        SIZES[96] = 3;
        SIZES[97] = 7;
        SIZES[98] = -1;
        SIZES[99] = 4;
        SIZES[100] = -1;
        SIZES[101] = 1;
    }

    /**
     * Separator
     */

    void handle(Player player, InBuffer in, int opcode);

}
