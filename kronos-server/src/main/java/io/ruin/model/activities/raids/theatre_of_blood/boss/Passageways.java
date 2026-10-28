package io.ruin.model.activities.raids.theatre_of_blood.boss;

public enum Passageways
{
    THE_MAIDEN_OF_SUGADINTI(new int[] { 41, 6, 0 }, new int[] { 58, 31, 0 }, 0, 1, true),
    PESTILENT_BLOAT(new int[] { 5, 31, 0 }, new int[] { 95, 59, 0 }, 1,1, false),
    NYLOCAS_VASILIAS(new int[] { 103, 50, 0 }, new int[] { 15, 6, 0 }, 1, 2, true),
    SOTETSEG(new int[] { 15, 6, 0 }, new int[] { 97, 23, 1 }, 2, 2, true),
    XARPUS(new int[] { 99, 48, 1 }, new int[] { 31, 10, 0 }, 3, 2, true);

    private int[] locDelta, destDelta;

    private int room, boss;

    private boolean across;

    private Passageways(int[] locDelta, int[] destDelta, int room, int boss, boolean across)
    {
        this.locDelta = locDelta;
        this.destDelta = destDelta;
        this.room = room;
        this.boss = boss;
        this.across = across;
    }

    public int[] getLocDelta()
    {
        return locDelta;
    }

    public int getRoom()
    {
        return room;
    }

    public int getBoss()
    {
        return boss;
    }

    public boolean isAcross()
    {
        return across;
    }

    public int[] getDestDelta()
    {
        return destDelta;
    }
}
