package io.ruin.model.skills.farming.patch.impl;

import com.google.api.client.util.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.model.stat.StatType;
import io.ruin.utility.StringUtils;
import io.ruin.utility.TimeUtils;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.patch.Patch;

import java.util.List;

public class CompostBin extends Patch {

    private static final int BUCKET = 1925;

    @Expose
    private CompostType compostType = CompostType.COMPOST;

    public int volcanicAsh;

    @Override
    public int getVarpbitValue() {
        if (getProduceCount() == 0)
            return 0;
        if (stage == 0) {
            int value = 0;
            if (compostType.getType() == 1)
                value |= 1 << 5;
            else if (compostType.getType() == 2)
                value |= 1 << 7;
            value |= getProduceCount();
            return value;
        } else if (stage == 1) {
            return 31;
        } else if (stage == 2) {
            if (compostType.getType() != 2)
                return (1 << 5) | (1 << 4) | (getProduceCount() - 1);
            else
                return 144 | (getProduceCount() - 1);
        }
        return 0;
    }

    @Override
    public void interact() {
        switch (stage) {

            case 0:
                if (getProduceCount() != 15)
                    return;
                player.startEvent(event -> {
                    player.animate(810);
                    event.delay(1);
                    advanceStage();
                    setTimePlanted(System.currentTimeMillis());
                    update();
                });
                break;

            case 1:
                player.sendMessage("The vegetation hasn't finished rotting yet.");
                break;

            case 2:

                player.startEvent(event -> {
                    while (getProduceCount() > 0 && player.getInventory().hasRoomFor(compostType.getItemId())) {
                        player.animate(832);
                        event.delay(1);
                        player.getInventory().add(compostType.getItemId(), 1);
                        if (compostType.getExperience() != -1)
                            player.getStats().addXp(StatType.Farming, compostType.getExperience(), true);
                        removeProduce();
                        update();
                    }
                    if (getProduceCount() == 0) {
                        reset(false);
                        update();
                        player.sendMessage("The compost bin is now empty.");
                    }
                });
                break;
            default:
                player.sendMessage("nothing..... ");
                break;
        }
    }

    @Override
    public void objectAction(int option) {
        if (option == 1)
            interact();
        else if (option == 5)
            dump();
    }

    public void dump() {
        player.dialogue(
                new OptionsDialogue("Dump the entire contents of the bin?",
                        new Option("Yes, throw it away.", () -> player.startEvent(event -> {
                            player.animate(832);
                            event.delay(1);
                            reset(false);
                        })),
                        new Option("No, keep it.", player::closeDialogue))
        );
    }

    @Override
    public void handleItem(Item item) {
        switch (stage) {

            case 0:
                if (getProduceCount() >= 15) {
                    player.sendMessage("The compost bin is too full to put anything else in it.");
                    return;
                }

                CompostType cType = CompostType.NOTHING;
                switch (item.getId()) {
                    case 6055://Weeds
                    case 1947://Grain
                    case 1971://Kebab
                    case 1779://Flax
                    case 1951://Redberries
                    case 753://Cadava Berries
                    case 2126://Dwellberries
                    case 1942://Potato
                    case 1957://Onion
                    case 1965://Cabbage
                    case 5986://Sweetcorn
                    case 5504://Strawberry
                    case 2102://Lemon
                    case 2120://Lime
                    case 1955://Cooking Apple
                    case 1963://Banana
                    case 2108://Orange
                    case 5970://Curry Leaf
                    case 6883://Peach
                    case 5984://Watermelon Slice
                    case 1527://Grimy Ardrigal
                    case 1528://Ardrigal
                    case 1525://Grimy Snake Weed
                    case 1526://Snake Weed
                    case 1531://Grimy Volencia Moss
                    case 1532://Volencia Moss
                    case 1533://Grimy Rogue's Purse
                    case 1534://Rogue's Purse
                    case 1529://Grimy Sito Foil
                    case 1530://Sito Foil
                    case 199://Grimy Guam Leaf
                    case 249://Guam Leaf
                    case 201://Grimy Marrentill
                    case 251://Marrentill
                    case 203://Grimy Tarromin
                    case 253://Tarromin
                    case 205://Grimy Harralander
                    case 255://Harralander
                    case 207://Grimy Ranarr Weed
                    case 257://Ranarr Weed
                    case 209://Grimy Irit Leaf
                    case 259://Irit Leaf
                    case 6006://Barley
                    case 6008://Barley Malt
                    case 5994://Hammerstone Hops
                    case 5996://Asgarnina Hops
                    case 5931://Jute Fibre
                    case 6113://Kelda Hops
                    case 5998://Yanillian Hops
                    case 6000://Krandorian Hops
                    case 6002://Wildblood Hops
                    case 6010://Marigolds
                    case 6014://Rosemary
                    case 6012://Nasturtiums
                    case 1793://Woad Leaf
                    case 225://Limpwurt Root
                    case 5933://Willow Branch
                    case 1984://Rotten Apple
                    case 5992://Apple Mush
                    case 401://Seaweed
                    case 403://Edible Seaweed
                    case 21504://Giant Seaweed
                    case 3138://Potato Cactus
                    case 6020://Leaves
                        cType = CompostType.COMPOST;
                        break;
                    case 2114://Pineapple
                    case 1851://Tenti Pineapple
                    case 2982://Watermelon
                    case 231://Snape Grass
                    case 5972://Papaya fruit
                    case 5974://Coconut
                    case 5978://Coconut Shell
                    case 5976://Half Coconut
                    case 22929://Dragonfruit
                    case 6004://Mushroom
                    case 247://Jangerberries
                    case 239://White Berries
                    case 6018://Poison Ivy Berries
                    case 3049://Grimy Toadflax
                    case 2998://Toadflax
                    case 211://Grimy Avantoe
                    case 261://Avantoe
                    case 213://Grimy Kwuarm
                    case 263://Kwuarm
                    case 3051://Grimy Snapdragon
                    case 3000://Snapdragon
                    case 215://Grimy Cadantine
                    case 265://Cadantine
                    case 2485://Grimy Lantadyme
                    case 2481://Lantadyme
                    case 217://Grimy Dwarf weed
                    case 267://Dwarf weed
                    case 219://Grimy Torstol
                    case 269://Torstol
                    case 6043://Oak Roots
                    case 6045://Willow Roots
                    case 6047://Maple Roots
                    case 6049://Yew Roots
                    case 6051://Magic Roots
                    case 22935://Celastrus Bark
                    case 5980://Calquat fruit
                    case 6469://White tree fruit
                    case 22932://White lily
                        cType = CompostType.SUPERCOMPOST;
                        break;
                    case 1982://tomato atleast 1 other item to make compost else 15 will make rotten tomato
                        cType = CompostType.ROTTEN_TOMATOES;
                        break;
                    default:
                        cType = CompostType.NOTHING;
                        break;
                }
                if (cType == CompostType.NOTHING) {
                    player.sendMessage("There's no reason to put that in a compost bin.");
                    return;
                }

                if (compostType != cType && compostType != CompostType.NOTHING) {
                    player.dialogue(
                            new MessageDialogue("With the contents currently in this bin, it will produce " + (compostType.getType() == 1 ? "supercompost" : "rotten tomatoes") + ".<br>Adding " + item.getDef().name + " will change the product to regular compost.<br>Are you sure you want to continue?"),
                            new OptionsDialogue(
                                    new Option("Yes.", () -> addItemToCompostBin(item, CompostType.COMPOST)),
                                    new Option("No.", player::closeDialogue)));
                    return;
                }
                addItemToCompostBin(item, cType);
                break;

            case 2:
                boolean addingVolcanicAsh = false;
                if (item.getId() == 21622) {
                    if (volcanicAsh == 25) {
                        player.sendMessage("You have already turned the super compost into ultra compost.");
                        return;
                    }
                    addingVolcanicAsh = true;
                }
                if (item.getId() != BUCKET && !addingVolcanicAsh) {
                    player.sendMessage("You'll need a bucket instead.");
                    return;
                }
                if (compostType == CompostType.ROTTEN_TOMATOES) {
                    player.sendMessage("You don't need a bucket to take the rotten tomatoes, just use your hands instead.");
                    return;
                }
                if (compostType == CompostType.SUPERCOMPOST) {
                    if (addingVolcanicAsh) {
                        player.startEvent(event -> {
                            while (volcanicAsh < 25) {
                                event.delay(2);
                                Item vAsh = player.getInventory().findItem(21622);
                                if (vAsh == null) {
                                    break;
                                }
                                player.animate(832);
                                volcanicAsh++;
                                player.sendMessage("You add the Volcanic Ash to the compost bin.");
                                vAsh.remove(1);
                                if (volcanicAsh == 25) {
                                    player.sendMessage("After adding the Volcanic Ash, your super compost turned into Ultracompost.");
                                    compostType = CompostType.ULTRACOMPOST;
                                    setNeedsUpdate(true);
                                    break;
                                }
                            }
                        });
                        return;
                    }
                }
                break;
            default:
                player.sendMessage("should never be here..");
                break;
        }
    }

    public void addItemToCompostBin(Item item, CompostType type) {
        if (getProduceCount() > 0 && compostType != CompostType.ROTTEN_TOMATOES) {
            if (type == CompostType.ROTTEN_TOMATOES) {
                type = compostType;
            }
        }
        CompostType finalType = type;
        player.startEvent(event -> {
            while (getProduceCount() < 15) {
                event.delay(2);
                if (player.getInventory().getAmount(item.getId()) == 0) {
                    break;
                }
                player.animate(832);
                this.compostType = finalType;
                setProduceCount(getProduceCount() + 1);
                player.getInventory().remove(item.getId(), 1);
                update();
            }
        });
    }

    @Override
    public void tick() {
        if (isNextStageReady()) {
            advanceStage();
            if (stage == 2) {
                player.sendMessage(Color.DARK_RED.wrap("Your compost bin is ready to collect. ("+ StringUtils.capitalizeFirst(compostType.name().toLowerCase().replaceAll("_", " "))+")"));
            }
            setNeedsUpdate(true);
        }
    }

    @Override
    protected boolean isNextStageReady() {
        if (getTimePlanted() <= 0 || stage != 1)
            return false;
        return getTimeElapsed() >= TimeUtils.getMinutesToMillis(15);
    }

    @Override
    public void reset(boolean weeds) {
        compostType = CompostType.NOTHING;
        stage = 0;
        volcanicAsh = 0;
        setProduceCount(0);
        setTimePlanted(0);
        update();
    }

    @Override
    public int getCropVarpbitValue() {
        throw new IllegalStateException();
    }

    @Override
    public void cropInteract() {

    }

    @Override
    public boolean canPlant(Crop crop) {
        return false;
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        return 15;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public String getPatchName() {
        return "";
    }
}
