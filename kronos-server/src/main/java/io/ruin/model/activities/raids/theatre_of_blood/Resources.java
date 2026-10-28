package io.ruin.model.activities.raids.theatre_of_blood;

public enum Resources {

    STAMINA(12625, 1),
    PRAYER(2434, 2),
    BREW(6685, 3),
    RESTORE(3024, 3),
    MUSHROOM_POTATO(7058, 1),
    SHARK(385, 1),
    SEA_TURTLE(397, 2),
    MANTA_RAY(391, 2);

    public int model, price;

    private Resources(int model, int price) {
        this.model = model;
        this.price = price;
    }
}
