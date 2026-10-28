package io.ruin.model.inter.enchanting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.enchanting.armor.EnchantArmorInfo;
import io.ruin.model.inter.enchanting.jewellery.EnchantJewelleryInfo;
import io.ruin.model.inter.enchanting.weapons.EnchantWeaponsInfo;
import io.ruin.model.inter.utils.Option;

import static io.ruin.model.inter.enchanting.armor.EnchantArmorInfo.*;
import static io.ruin.model.inter.enchanting.jewellery.EnchantJewelleryInfo.*;
import static io.ruin.model.inter.enchanting.weapons.EnchantWeaponsInfo.*;

public class EnchantButton {

    public static void enchantButton(Player p) {
        //Armor
        if (selectedCrystalHelm) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Crystal Helm?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradecrystalHelm(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedCrystalBody) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Crystal Body?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradecrystalBody(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedCrystalLegs) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Crystal Legs?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradecrystalLegs(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedAncestralHat) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Ancestral Hat?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeAncestralHat(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedAncestralTop) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Ancestral Robe Top?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeAncestralTop(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedAncestralBottom) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Ancestral Robe Bottom?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeAncestralBottom(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedArmaHelm) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Armadyl Helmet?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeArmaHelm(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedArmaTop) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Armadyl Chestplate?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeArmaTop(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedArmaBottom) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Armadyl Chainskirt?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeArmaBottom(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedBandosTop) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Bandos Chestplate?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeBcp(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedBandosBottom) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Bandos Tassets?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeTassets(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedDfs) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Dragonfire Shield?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeDfs(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedEliteVoidTop) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Elite Void Top?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeEliteVoidTop(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedEliteVoidBottom) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Elite Void Robe?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeEliteVoidBottom(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedSerpentineHelm) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Serpentine Helm?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeSerpentine(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedNeitiznotFaceguard) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Neitiznot Faceguard?",
                            new Option("Yes, I'm sure!", () -> EnchantArmorInfo.upgradeNeitiznot(p)),
                            new Option("Nevermind.")
                    )
            );
        }

        //Weapons
        if (selectedAbyssalBludgeon) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Abyssal Bludgeon?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeAbyssalBludgeon(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedAbyssalDagger) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Abyssal Dagger?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeAbyssalDagger(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedAbyssalWhip) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Abyssal Whip?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeAbyssalWhip(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedBofa) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Bow of Faerdhinen?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeBofa(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedAcb) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Armadyl Crossbow?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeAcb(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedBladeofSaeldor) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Blade of Saeldor?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeSaeldor(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedDragonClaws) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Dragon Claws?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeClaws(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedGhraziRapier) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Ghrazi Rapier?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeRapier(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedKodaiWand) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Kodai Wand?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeKodai(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedSanguinesti) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Sanguinesti Staff?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeSanguinesti(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedScythe) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Scythe of Vitur?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeScythe(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedSOL) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Staff of Light?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeSOL(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedToxicBlowpipe) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Toxic Blowpipe?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeBlowpipe(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedTrident) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Trident of the Seas?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeTrident(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedDHCB) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Dragon Hunter Crossbow?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeDhcb(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedDHLance) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Dragon Hunter Lance?",
                            new Option("Yes, I'm sure!", () -> EnchantWeaponsInfo.upgradeLance(p)),
                            new Option("Nevermind.")
                    )
            );
        }

        //Jewellery
        if (selectedFury) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Amulet of Fury?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeFury(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedTorture) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Amulet of Torture?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeTorture(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedDamned) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Amulet of the Damned?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeDamned(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedArchers) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Archers Ring?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeArchers(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedBerserker) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Berserker Ring?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeBerserker(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedBrimstone) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Brimstone Ring?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeBrimstone(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedGranite) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Granite Ring?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeGranite(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedAnguish) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Necklace of Anguish?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeAnguish(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedRecoil) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Ring of Recoil?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeRecoil(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedSuffering) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Ring of Suffering?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeSuffering(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedGods) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Ring of the Gods?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeGods(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedSeers) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Seers Ring?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeSeers(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedTormented) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Tormented Bracelet?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeTormented(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedTreasonous) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Treasonous Ring?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeTreasonous(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedBloodFury) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Amulet of Blood Fury?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeBloodFury(p)),
                            new Option("Nevermind.")
                    )
            );
        }
        if (selectedOccult) {
            p.dialogue(
                    new OptionsDialogue("Enchant your Occult Necklace?",
                            new Option("Yes, I'm sure!", () -> EnchantJewelleryInfo.upgradeOccult(p)),
                            new Option("Nevermind.")
                    )
            );
        }
    }
}