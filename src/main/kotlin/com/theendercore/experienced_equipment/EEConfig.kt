package com.theendercore.experienced_equipment

import com.theendercore.experienced_equipment.ExperiencedEquipment.ID
import com.theendercore.experienced_equipment.ExperiencedEquipment.id
import me.fzzyhmstrs.fzzy_config.config.Config
import net.minecraft.world.item.Items.*

class EEConfig : Config(id(ID)) {
    var equipmentMap = mutableMapOf(
        // lvl 5
        CHAINMAIL_HELMET to 5,
        CHAINMAIL_CHESTPLATE to 5,
        CHAINMAIL_LEGGINGS to 5,
        CHAINMAIL_BOOTS to 5,
        GOLDEN_HELMET to 5,
        GOLDEN_CHESTPLATE to 5,
        GOLDEN_LEGGINGS to 5,
        GOLDEN_BOOTS to 5,
        // lvl 15
        IRON_HELMET to 15,
        IRON_CHESTPLATE to 15,
        IRON_LEGGINGS to 15,
        IRON_BOOTS to 15,
        // lvl 30
        DIAMOND_HELMET to 30,
        DIAMOND_CHESTPLATE to 30,
        DIAMOND_LEGGINGS to 30,
        DIAMOND_BOOTS to 30,
        // lvl 45
        NETHERITE_HELMET to 45,
        NETHERITE_CHESTPLATE to 45,
        NETHERITE_LEGGINGS to 45,
        NETHERITE_BOOTS to 45,
    )
}