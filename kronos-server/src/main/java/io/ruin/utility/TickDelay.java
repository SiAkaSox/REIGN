package io.ruin.utility;

import io.ruin.Server;

public class TickDelay {

    public long timeLeft = 0L;
    private transient long endTime = 0L;

    private long end;

    public void reset() {
        end = 0;
    }

    public void setEnd(long newEnd) {
        end = newEnd;
    }

    public void delay(int ticks) {
        end = Server.getEnd(ticks);
    }

    public void delaySeconds(int seconds) {
        delay(Server.toTicks(seconds));
    }

    public void addDelaySeconds(int seconds) {
        end += Server.toTicks(seconds);
    }

    public boolean isDelayed() {
        return !Server.isPast(end);
    }

    public boolean isDelayed(int extra) {
        return !Server.isPast(end + extra);
    }

    public int remaining() {
        return (int) (end - Server.currentTick());
    }

    public int remainingToMins() {
        return remaining() / (1000 * 60 / 600);
    }

    public boolean finished() {
        return Server.isPast(end);
    }

    public void update() {
        if (this.endTime == 0L) {
            if (this.timeLeft != 0L) {
                this.endTime = System.currentTimeMillis() + this.timeLeft;
            }
        } else if (Math.abs(this.endTime) <= Math.abs(System.currentTimeMillis())) {
            this.endTime = 0L;
            this.timeLeft = 0L;
        } else {
            this.timeLeft = this.endTime - System.currentTimeMillis();
        }

    }

    public long getTimeLeft() {
        this.update();
        return this.timeLeft;
    }

    public boolean isActive() {
        return this.getTimeLeft() > 0L;
    }
}
