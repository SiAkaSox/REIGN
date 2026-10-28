package io.ruin.model.inter.handlers.shopinterface;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.shop.Currency;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum CustomShop {
    MELEE_STORE(
            1,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(1153,150),
                    new ShopItem(1115,450),
                    new ShopItem(1067,400),
                    new ShopItem(1191,400),
                    new ShopItem(3105,0),
                    new ShopItem(1163,40500),
                    new ShopItem(1127,0),
                    new ShopItem(1079,0),
                    new ShopItem(1201,0),
                    new ShopItem(4131,20000),

                    new ShopItem(1149,120000),
                    new ShopItem(3140,1500000),
                    new ShopItem(1187,400000),
                    new ShopItem(21298,750000),
                    new ShopItem(21301,1000000),
                    new ShopItem(21304,1000000),
                    new ShopItem(6524,500000),
                    new ShopItem(6568,750000),

                    new ShopItem(3751,75000),
                    new ShopItem(3753,75000),
                    new ShopItem(10828,0),
                    new ShopItem(7458,1000),
                    new ShopItem(7461,0),
                    new ShopItem(7462,500000),
                    new ShopItem(23389,800000),
                    new ShopItem(23246,3000000),
                    new ShopItem(1540,1000),

                    new ShopItem(544,0),
                    new ShopItem(542,1000),
                    new ShopItem(4502,10000),
                    new ShopItem(7534,5000),
                    new ShopItem(5698,33000),
                    new ShopItem(1305,90000),
                    new ShopItem(4587,0),

                    new ShopItem(1323,750),
                    new ShopItem(1327,2000),
                    new ShopItem(1329,2800),
                    new ShopItem(1331,3500),
                    new ShopItem(1333,20000),
                    new ShopItem(1249,60000),
                    new ShopItem(3204,150000),
                    new ShopItem(7158,1500000),
                    new ShopItem(1434,60000),
                    new ShopItem(6527,18000),
                    new ShopItem(6528,30000),
                    new ShopItem(6525,15000),
                    new ShopItem(10887,138000),
                    new ShopItem(1319,50000),
                    new ShopItem(20727,84000),
                    new ShopItem(2402,50000),
                    new ShopItem(6746,150000),
                    new ShopItem(4153,31000),

                    new ShopItem(6317,55000),
                    new ShopItem(11037,50000),
                    new ShopItem(6523, 75000),
                    new ShopItem(22398, 1250000),
                    new ShopItem(2550, 2000),
                    new ShopItem(2552, 2000),
                    new ShopItem(1725, 0),
                    new ShopItem(11978, 14000),
                    new ShopItem(3853, 2000),
                    new ShopItem(11968, 14000),
            }
    ),
    RANGED_STORE(
            2,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(1169,1000),
                    new ShopItem(1129,1000),
                    new ShopItem(1095, 1000),
                    new ShopItem(1133, 1500),
                    new ShopItem(1097, 1500),
                    new ShopItem(6328,0),
                    new ShopItem(1135,4680),
                    new ShopItem(1099,2340),
                    new ShopItem(1065,2340),
                    new ShopItem(22275, 5000),

                    new ShopItem(2499,5616),
                    new ShopItem(2493,2592),
                    new ShopItem(2487,2592),
                    new ShopItem(22278, 5800),


                    new ShopItem(2501,6738),
                    new ShopItem(2495,3108),
                    new ShopItem(2489,3108),
                    new ShopItem(22281, 6750),

                    new ShopItem(2503,0),
                    new ShopItem(2497,0),
                    new ShopItem(2491, 3732),
                    new ShopItem(22284, 21000),

                    new ShopItem(8880, 1200),
                    new ShopItem(9185, 0),

                    new ShopItem(10499, 0),
                    new ShopItem(3749, 0),
                    new ShopItem(841, 30),
                    new ShopItem(843, 60),
                    new ShopItem(849, 120),
                    new ShopItem(853, 240),
                    new ShopItem(861, 0),

                    new ShopItem(10034, 1225),
                    new ShopItem(11959, 2450),
                    new ShopItem(884, 12),
                    new ShopItem(890, 48),
                    new ShopItem(892, 0),
                    new ShopItem(21326, 1250),
                    new ShopItem(11212, 4875),
                    new ShopItem(863, 120),

                    new ShopItem(868, 450),
                    new ShopItem(810, 256),
                    new ShopItem(811, 952),
                    new ShopItem(11230, 6000),
                    new ShopItem(826, 120),
                    new ShopItem(830, 752),
                    new ShopItem(19484, 5000),
                    new ShopItem(22804, 8000),
                    new ShopItem(20849, 8000),
                    new ShopItem(8882, 12),

                    new ShopItem(9142, 42),
                    new ShopItem(9143, 150),
                    new ShopItem(9144, 280),
                    new ShopItem(9244, 0),
                    new ShopItem(9242, 1000),
                    new ShopItem(9243, 1000),
                    new ShopItem(2, 500)
            }
    ),
    MAGIC_STORE(
            3,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(556,15),
                    new ShopItem(558, 15),
                    new ShopItem(554, 15),
                    new ShopItem(555, 0),
                    new ShopItem(557, 0),
                    new ShopItem(562, 0),
                    new ShopItem(560, 0),
                    new ShopItem(565,0),
                    new ShopItem(564,0),
                    new ShopItem(566,212),

                    new ShopItem(561,200),
                    new ShopItem(563,15),
                    new ShopItem(9075,0),
                    new ShopItem(21880,300),
                    new ShopItem(4699,30),
                    new ShopItem(4695,30),
                    new ShopItem(4694,30),
                    new ShopItem(4698,30),
                    new ShopItem(4697,30),
                    new ShopItem(4696,30),

                    new ShopItem(579,125),
                    new ShopItem(4089,46000),
                    new ShopItem(4109,46000),
                    new ShopItem(4099,46000),
                    new ShopItem(23047,46000),
                    new ShopItem(13385,8000),
                    new ShopItem(6109,0),
                    new ShopItem(6107,0),
                    new ShopItem(6108,0),
                    new ShopItem(6106,0),
//tops
                    new ShopItem(577,500),
                    new ShopItem(4091,84000),
                    new ShopItem(4111, 84000),
                    new ShopItem(4101, 84000),
                    new ShopItem(23050, 84000),
                    new ShopItem(13387, 15000),
                    //staffs
                    new ShopItem(2417,80000),
                    new ShopItem(2416,80000),
                    new ShopItem(2415,80000),
                    new ShopItem(1381,2000),


//legs
                    new ShopItem(1011,325),
                    new ShopItem(4093,84000),
                    new ShopItem(4113,84000),
                    new ShopItem(4103,84000),
                    new ShopItem(23053,84000),
                    new ShopItem(13389,12000),
                    //staffs
                    new ShopItem(1383,2000),
                    new ShopItem(1387,2000),
                    new ShopItem(3054,32000),
                    new ShopItem(1409,25000),

    //boots
                    new ShopItem(2579,150000),
                    new ShopItem(4097,20000),
                    new ShopItem(4117,20000),
                    new ShopItem(4107,20000),
                    new ShopItem(23059,20000),

                    //shields
                    new ShopItem(20718,5000),
                    new ShopItem(4675,0),
                    new ShopItem(4156,5000),
                    new ShopItem(9731,10000),
                    new ShopItem(12829,250000),
            }
    ),
    BLOOD_MONEY_STORE(
            4,
            Currency.BLOOD_MONEY,
            new ShopItem[]{
                    new ShopItem(22647,10000),//zuriels staff
                    new ShopItem(22613,22000),//vls
                    new ShopItem(22622,17500),//stat warhammer
                    new ShopItem(11802,7000),//ags
                    new ShopItem(13652,22000),//claws
                    new ShopItem(12877,1250),//dh set
                    new ShopItem(12881,1250),//ahrims set
                    new ShopItem(12883,1250),//karils set
                    new ShopItem(11840,500),//d boots
                    new ShopItem(6920,750),//infinity boots

                    new ShopItem(12002,2500),//occult
                    new ShopItem(6914,750),//master wand
                    new ShopItem(6889,750),//mages book
                    new ShopItem(20716,1000),//tome of fire
                    new ShopItem(20718,5),//burnt page
                    new ShopItem(21902,1250),//dragon crossbow
                    new ShopItem(19481,1250),//heavy ballista
                    new ShopItem(22296,2000),//sol
                    new ShopItem(22804,5),//dragon knife
                    new ShopItem(20849,5),//dragon thrownaxe

                    new ShopItem(19484,5),//dragon javelin
                    new ShopItem(11230,10),//dragon dart
                    new ShopItem(11212, 5),//dragon arrow
                    new ShopItem(21948, 5),//dragonstone dragon bolt (e)
                    new ShopItem(21944, 5),//ruby dragon bolt (e)
                    new ShopItem(21932, 5),//Opal dragon bolt (e)
                    new ShopItem(21946, 5),//diamond dragon bolt (e)
                    new ShopItem(10156, 500),//hunters crossbow
                    new ShopItem(24607, 2),//blightest ice sack
                    new ShopItem(24615, 2),//blighted teleport sack

                    new ShopItem(24621, 2),//blighted vengeance sack
                    new ShopItem(24613, 2),//blighted entangle sack
                    new ShopItem(13442, 10),//anglerfish
                    new ShopItem(3145, 8),//karambwan
                    new ShopItem(12696, 10),//super combats
                    new ShopItem(12914, 10),//anti-venom+
                    new ShopItem(10926,10),//sanfew serum
                    new ShopItem(22462,10),//bastion potion
                    new ShopItem(22450,10),//battlemage potion
                    new ShopItem(12791,500),//rune pouch
                    new ShopItem(10551,350),//fighter torso
                    new ShopItem(6731,500),//seers ring
                    new ShopItem(6733,1000),//archers ring
                    new ShopItem(6735,150),//warrior ring
                    new ShopItem(6737,1000),//berserker ring

                    new ShopItem(24201,250),//halo
                    new ShopItem(24192,250),//halo
                    new ShopItem(24195,250),//halo
                    new ShopItem(12639,250),//halo
                    new ShopItem(24198,250),//halo
                    new ShopItem(12638,250),//halo

                    new ShopItem(26500,250),//Blood money booster
                    new ShopItem(25087,350),//Revenant booster
                    new ShopItem(24460,500),//Great vault booster
                    new ShopItem(20238,150)//Clue key boost
            }
    ),
    UNTRADEABLE_STORE(
            5,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(10548,350000),
                    new ShopItem(10550,350000),
                    new ShopItem(2414,35000),
                    new ShopItem(2412,35000),
                    new ShopItem(2413,35000),
                    new ShopItem(7409,3500000),
                    new ShopItem(4081,150000),
                    new ShopItem(10588,2550000),
                    new ShopItem(12018,5500000),
                    new ShopItem(22114,85000),
                    new ShopItem(11200,50000),
                    new ShopItem(11061,125000),
                    new ShopItem(4224,750000),
                    new ShopItem(4212,1000000),
                    new ShopItem(19564,50000),
                    new ShopItem(3842,15000),
                    new ShopItem(3844,15000),
                    new ShopItem(12612,15000),
                    new ShopItem(12610,15000),
                    new ShopItem(12608,15000),
                    new ShopItem(3840,15000),
                    new ShopItem(9625, 2500000)

            }
    ),
    SLAYER_REWARDS(
            6,
            Currency.SLAYER_POINTS,
            new ShopItem[]{
                    new ShopItem(12017,250),
                    new ShopItem(12863,300),
                    new ShopItem(11942,20),
                    new ShopItem(24266,50),
                    new ShopItem(11908,1000),
                    new ShopItem(786,15),
                    new ShopItem(11665,100),
                    new ShopItem(11664,100),
                    new ShopItem(11663,100),
                    new ShopItem(8839,100),
                    new ShopItem(8840,100),
                    new ShopItem(8842,50),
                    new ShopItem(13072,400),
                    new ShopItem(13073,400)
            }
    ),
    CONSUMABLES(
            7,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(391,250),
                    new ShopItem(385,500),
                    new ShopItem(7946,250),
                    new ShopItem(373,350),
                    new ShopItem(379,250),
                    new ShopItem(333,75),
                    new ShopItem(2301,375),
                    new ShopItem(7218,325),
                    new ShopItem(4417,0),
                    new ShopItem(2436,0),
                    new ShopItem(2440,0),
                    new ShopItem(2442,0),
                    new ShopItem(2444,0),
                    new ShopItem(3008,850),
                    new ShopItem(2446, 650),
                    new ShopItem(2434,8000),
                    new ShopItem(2452,4000),
                    new ShopItem(5952,850)

            }
    ),
    SKILLING_SUPPLIES(
            8,
            Currency.COINS,
            new ShopItem[] {
                    new ShopItem(8013,486),
                    new ShopItem(8015,729),
                    new ShopItem(233, 100),
                    new ShopItem(4155,10),
                    new ShopItem(314,3),
                    new ShopItem(1523,180),
                    new ShopItem(7937,10),
                    new ShopItem(228, 15),
                    new ShopItem(590,90),
                    new ShopItem(2347,72),
                    new ShopItem(952,166),
                    new ShopItem(946,67),
                    new ShopItem(1755,70),
                    new ShopItem(1265,48),
                    new ShopItem(1267,105),
                    new ShopItem(1275,32000),
                    new ShopItem(1351,350),
                    new ShopItem(1349,100),
                    new ShopItem(1359,9000),
                    new ShopItem(954,200),
                    new ShopItem(1778,30),
                    new ShopItem(313,2),
                    new ShopItem(1733,72),
                    new ShopItem(1734,8),
                    new ShopItem(303,50),
                    new ShopItem(305,100),
                    new ShopItem(301,150),
                    new ShopItem(307, 130),
                    new ShopItem(309, 115),
                    new ShopItem(311, 112),
                    new ShopItem(7510, 1250),
                    new ShopItem(1933, 25),
                    new ShopItem(5509, 1000),
                    new ShopItem(5510, 10000),
                    new ShopItem(5512, 50000),
                    new ShopItem(1785, 100),
                    new ShopItem(1775, 120),
                    new ShopItem(3157, 1000),
                    new ShopItem(3150,150),

                    new ShopItem(10010,150),
                    new ShopItem(11260,150),
                    new ShopItem(10012,150),
                    new ShopItem(10025,400),
                    new ShopItem(10150,150),
                    new ShopItem(10006,150),
                    new ShopItem(10008,150),
                    new ShopItem(10029,150),
                    new ShopItem(596,150),
                    new ShopItem(10031,150),
                    new ShopItem(12740,1500),
                    new ShopItem(12742,1500),
                    new ShopItem(12744,1500),

                    new ShopItem(11940,10),
                    new ShopItem(1759,15),
                    new ShopItem(1595,200),
                    new ShopItem(1592,200),
                    new ShopItem(1597,200)
            }
    ),
    DONATOR_STORE(
            9,
            Currency.DONATOR_TOKENS,
            new ShopItem[]{
                    new ShopItem(290,150), //sbox
                    new ShopItem(12922,200),//t fang
                    new ShopItem(12932,120),//magic fang
                    new ShopItem(12927,120),//serp visage
                    new ShopItem(11802,200),//ags
                    new ShopItem(26233,400),//Anc GS
                    new ShopItem(21003,300),//Elder Maul
                    new ShopItem(21295,250),//icape
                    new ShopItem(22109,35),//assembler
                    new ShopItem(12954,40),//ddef
                    new ShopItem(13576,400),//dwarham
                    new ShopItem(11785,250),//acbow
                    new ShopItem(7630,200),//evoid
                    new ShopItem(21034,200),//dex
                    new ShopItem(21079,200),//arcane
                    new ShopItem(21047,50),//torpscroll
                    new ShopItem(12863,150),//dcann
                    new ShopItem(13173,2750),//pset
                    new ShopItem(13175,1350),//hwset
                    new ShopItem(1053,500),//red
                    new ShopItem(1055,500),//blue
                    new ShopItem(1057,500),//green
                    new ShopItem(11847,750),//bhween
                    new ShopItem(1038,500), //red
                    new ShopItem(1040,500), //yellow
                    new ShopItem(1042,500), //blue
                    new ShopItem(1044,500), //green
                    new ShopItem(1046,500), //purple
                    new ShopItem(1048,500), //white
                    new ShopItem(11862,750),//bphat
                    new ShopItem(11863,1500),//rbphat
                    new ShopItem(1050,500), //santa
                    new ShopItem(13343,750), //bsanta
                    new ShopItem(13344,750), //invsanta
                    new ShopItem(607,70), //
                    new ShopItem(608,70), //
                    new ShopItem(6758,70), //
                    new ShopItem(6828,150), //petbox
                    new ShopItem(13225,350),//Tzrek-Jad
                    new ShopItem(13179,350),//vet'ion Jr
                    new ShopItem(13177,350),//Vene
                    new ShopItem(12648,350),//Pet Smoke devil
                    new ShopItem(4149,350),//abby demon
                    new ShopItem(30131,350),//lava dragon
                    new ShopItem(2528,120),//xp lamp
                    new ShopItem(10834, 400)//Dice bag
            }
    ),

    VOTE_STORE(
            10,
            Currency.VOTE_TICKETS,
            new ShopItem[]{
                    new ShopItem(4151, 10),
                    new ShopItem(12006, 35),
                    new ShopItem(11840, 10),
                    new ShopItem(290, 250),
                    new ShopItem(6199, 25),
                    new ShopItem(23951,3),
                    new ShopItem(19564,5),
                    new ShopItem(12791,10),
                    new ShopItem(6570, 10),
                    new ShopItem(21295,250),
                    new ShopItem(6758,25),
                    new ShopItem(608,25),
                    new ShopItem(607,25),
                    new ShopItem(21034,50),
                    new ShopItem(21079,50),
                    new ShopItem(786, 1),
                    new ShopItem(10834,200),
                    new ShopItem(13124,25),
                    new ShopItem(12637,25),
                    new ShopItem(12638,25),
                    new ShopItem(12639,25),
                    new ShopItem(20017,300),
                    new ShopItem(20005,300),
                    new ShopItem(20546,1),
                    new ShopItem(20545,2),
                    new ShopItem(20544,3),
                    new ShopItem(12804,75),
                    new ShopItem(13256,75),
                    new ShopItem(12771,50),
                    new ShopItem(12769,50),
                    new ShopItem(7671,100),
                    new ShopItem(7673,100),
                    new ShopItem(8839,15),
                    new ShopItem(8840,15),
                    new ShopItem(11663,15),
                    new ShopItem(11664,15),
                    new ShopItem(11665,15),
                    new ShopItem(8842,5),
                    new ShopItem(8850,5),
                    new ShopItem(12954,15),
                    new ShopItem(19722,20),
                    new ShopItem(7409,3),
                    new ShopItem(10499,10),
                    new ShopItem(4081,3),
                    new ShopItem(10588,10),
                    new ShopItem(12018,6),
                    new ShopItem(11941,15),
                    new ShopItem(22114,15),
                    new ShopItem(11200,5),
                    new ShopItem(11061,5)
            }
    ),

    AFK_STORE(
            11,
            Currency.AFK_DUST,
            new ShopItem[]{
                    new ShopItem(7409,100000),
                    new ShopItem(11920,1000000),
                    new ShopItem(6739,1000000),
                    new ShopItem(21028,1000000),
                    new ShopItem(12800,200000),
                    new ShopItem(22994,250000),
                    new ShopItem(13226,500000),

                    new ShopItem(10941,100000),
                    new ShopItem(10939,100000),
                    new ShopItem(10940,100000),
                    new ShopItem(10933,100000),

                    new ShopItem(13258,100000),
                    new ShopItem(13259,100000),
                    new ShopItem(13260,100000),
                    new ShopItem(13261,100000),

                    new ShopItem(25592,100000),
                    new ShopItem(25594,100000),
                    new ShopItem(25596,100000),
                    new ShopItem( 25598,100000),

                    new ShopItem(13646,100000),
                    new ShopItem(13642,100000),
                    new ShopItem(13640,100000),
                    new ShopItem(13644,100000),

                    new ShopItem(21343,100000),
                    new ShopItem(21345,200000),
                    new ShopItem(21392,300000),

            }
    ),

    IRONMAN_SHOP(
            12,
            Currency.COINS,
            new ShopItem[] {
                    new ShopItem(314,1),
                    new ShopItem(590,1),
                    new ShopItem(2347,1),
                    new ShopItem(946,1),
                    new ShopItem(1755,1),
                    new ShopItem(1735,1),
                    new ShopItem(1733,1),
                    new ShopItem(1734,3),
                    new ShopItem(1785,5),
                    new ShopItem(1592,10),
                    new ShopItem(1595,10),
                    new ShopItem(1597,10),
                    new ShopItem(11065,10),
                    new ShopItem(5523,175),
                    new ShopItem(1267,245),
                    new ShopItem(1269,2275),
                    new ShopItem(1271,5050),
                    new ShopItem(1275,32000),
                    new ShopItem(1349,98),
                    new ShopItem(1353,325),
                    new ShopItem(1355,900),
                    new ShopItem(1357,2260),
                    new ShopItem(1359,21000),

                    new ShopItem(303,1),
                    new ShopItem(309,1),
                    new ShopItem(301,1),
                    new ShopItem(311,1),

                    new ShopItem(1540,1000),
                    new ShopItem(952,1),
                    new ShopItem(5341,3),
                    new ShopItem(5343,3),
                    new ShopItem(5343,3),
                    new ShopItem(5325,3),
                    new ShopItem(5329,3),

                    new ShopItem(10006,1),
                    new ShopItem(10008,1),
                    new ShopItem(10012,0),
                    new ShopItem(11260,0),
                    new ShopItem(10010,12),
                    new ShopItem(954,10),
                    new ShopItem(227,1),
                    new ShopItem(233,2),
                    new ShopItem(2446,216),
                    new ShopItem(9419,1),
                    new ShopItem(10499,600),
                    new ShopItem(544,24),
                    new ShopItem(542,18),
                    new ShopItem(3105,7),
                    new ShopItem(8880,1200),
                    new ShopItem(8882,1),
                    new ShopItem(2417,48000),
                    new ShopItem(2415,48000),
                    new ShopItem(2416,48000),
                    new ShopItem(9672,4800),
                    new ShopItem(9674,7200),
                    new ShopItem(9676,6000)
            }
    )

    ;

    private ShopItem[] shopItems;
    private Currency currency;
    private int shopId;
    @Getter
    private List<Player> playersInShop;

    CustomShop(final int SHOP_ID, final Currency currency, ShopItem[] shopItems) {
        this.shopId = SHOP_ID;
        this.currency = currency;
        this.shopItems = shopItems;
        playersInShop = new ArrayList<>();
    }

    public static Item[] getItemsFromShop(Player player) {
        int shopId = player.getShopIdentifier();
        if (shopId < 0) {
            player.sendMessage("Something is wrong with this shop. Please contact a staff member.");
            return new Item[0];
        }
        CustomShop shop = Arrays.stream(CustomShop.values())
                .filter(s -> s.shopId == shopId)
                .findFirst()
                .orElse(null);

        if (shop != null) {
            return toItemArray(shop.shopItems);
        }
        return new Item[0];
    }

    public ShopItem[] getShopItems() {
        return shopItems;
    }

    private static Item[] toItemArray(ShopItem[] shopItems) {
        Item[] items = new Item[shopItems.length];
        for (int index = 0; index < shopItems.length; index++) {
            ShopItem shopItem = shopItems[index];
            if (shopItem != null) {
                items[index] = new Item(shopItem.getItemId(), shopItem.getQuantity());
            }
        }
        return items;
    }

    public Item[] getItems() {
        Item[] items = new Item[shopItems.length];
        for (int index = 0; index < shopItems.length; index++) {
            ShopItem shopItem = shopItems[index];
            if (shopItem != null) {
                items[index] = new Item(shopItem.getItemId(), shopItem.getQuantity());
            }
        }
        return items;
    }

    public static CustomShop get(int shopId) {
        return Arrays.stream(CustomShop.values())
                .filter(s -> s.shopId == shopId)
                .findFirst()
                .orElse(null);
    }

    public void addPlayerToShop(Player player) {
        if(!playersInShop.contains(player)) {
            playersInShop.add(player);
        }
    }

    public void removePlayerFromShop(Player player) {
        playersInShop.remove(player);
    }

    public Currency getCurrency() {
        return currency;
    }

    public ShopItem getShopItem(int itemId) {
        return Arrays.stream(shopItems)
                .filter(i -> i.getItemId() == itemId)
                .findFirst()
                .orElse(null);
    }

    public void refreshShop() {
        List<Player> playersToRemove = new ArrayList<>();
        for (Player player : playersInShop) {
            if (player != null) {
                if (!player.hasInterfaceOpen(Interface.CUSTOM_SHOP, InterfaceType.MAIN)) {
                    playersToRemove.add(player);
                    continue;
                }
                player.getPacketSender().sendItems(10005, getItems());
                player.setShopIdentifier(player.getShopIdentifier());
                player.getPacketSender().sendClientScript(917, "ii", -1, -1);
                player.getPacketSender().sendClientScript(10197);
                player.getPacketSender().sendAccessMask(836, 39, 0,
                        127, 1150);
            }
        }
        playersToRemove.stream().filter(Objects::nonNull).forEach(this::removePlayerFromShop);
    }

    public static void openCustomShopViaCmd(Player player) {
        player.getPacketSender().sendString(836, 14, "Melee Store");
        CustomShopInterface.open(player, CustomShop.get(1).getItems());
        CustomShopInterface.handleEnteringShop(player, CustomShop.MELEE_STORE);
        player.setShopIdentifier(1);
    }

    public static void openBMShop(Player player) {
        player.getPacketSender().sendString(836, 14, "Blood Money Reward Shop");
        CustomShopInterface.open(player, CustomShop.get(4).getItems());
        CustomShopInterface.handleEnteringShop(player, CustomShop.BLOOD_MONEY_STORE);
        player.setShopIdentifier(4);
    }

    public static void openAFKShop(Player player) {
        player.getPacketSender().sendString(836, 14, "AFK Store");
        CustomShopInterface.open(player, CustomShop.get(11).getItems());
        CustomShopInterface.handleEnteringShop(player, CustomShop.AFK_STORE);
        player.setShopIdentifier(11);
    }

    public static void openIronmanShop(Player player) {
        player.getPacketSender().sendString(836, 14, "Ironman Store");
        CustomShopInterface.open(player, CustomShop.get(12).getItems());
        CustomShopInterface.handleEnteringShop(player, CustomShop.IRONMAN_SHOP);
        player.setShopIdentifier(12);
    }

    static {
        NPCAction.register(3216, "talk-to", (player, npc) -> {
            if (player.getGameMode().isAnyIron()) {
                player.getPacketSender().sendString(836, 14, "Voting Store");
                CustomShopInterface.open(player, CustomShop.get(10).getItems());
                CustomShopInterface.handleEnteringShop(player, CustomShop.VOTE_STORE);
                player.setShopIdentifier(10);
            } else {
                player.getPacketSender().sendString(836, 14, "Melee Supply Shop");
                CustomShopInterface.open(player, CustomShop.get(1).getItems());
                CustomShopInterface.handleEnteringShop(player, CustomShop.MELEE_STORE);
                player.setShopIdentifier(1);
            }
        });
        NPCAction.register(3216, "trade", (player, npc) -> {
            if (player.getGameMode().isAnyIron()) {
                player.getPacketSender().sendString(836, 14, "Voting Reward Shop");
                CustomShopInterface.open(player, CustomShop.get(10).getItems());
                CustomShopInterface.handleEnteringShop(player, CustomShop.VOTE_STORE);
                player.setShopIdentifier(10);
            } else {
                player.getPacketSender().sendString(836, 14, "Melee Supply Shop");
                CustomShopInterface.open(player, CustomShop.get(1).getItems());
                CustomShopInterface.handleEnteringShop(player, CustomShop.MELEE_STORE);
                player.setShopIdentifier(1);
            }
        });
        NPCAction.register(7045, 1, (player, npc) -> {
            player.getPacketSender().sendString(836, 14, "Voting Reward Shop");
            CustomShopInterface.open(player, CustomShop.get(10).getItems());
            CustomShopInterface.handleEnteringShop(player, CustomShop.VOTE_STORE);
            player.setShopIdentifier(10);
        });
        NPCAction.register(7045, 3, (player, npc) -> {
            if (player.getGameMode().isAnyIron()) {
                player.sendMessage("Ironman game modes cannot use this shop.");
            }
            player.getPacketSender().sendString(836, 14, "Donator Store");
            CustomShopInterface.open(player, CustomShop.get(9).getItems());
            CustomShopInterface.handleEnteringShop(player, CustomShop.DONATOR_STORE);
            player.setShopIdentifier(9);
        });
    }
}
