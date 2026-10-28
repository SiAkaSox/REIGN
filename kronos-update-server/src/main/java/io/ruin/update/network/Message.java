package io.ruin.update.network;

import io.ruin.update.network.io.InBuffer;

public class Message {

    public final int opcode;

    public InBuffer buffer;

    public Message(int opcode, byte[] payload) {
        this.opcode = opcode;
        this.buffer = new InBuffer(payload);
    }

}