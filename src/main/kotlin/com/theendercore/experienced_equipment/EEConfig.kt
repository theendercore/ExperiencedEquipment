package com.theendercore.experienced_equipment

import com.theendercore.experienced_equipment.ExperiencedEquipment.ID
import com.theendercore.experienced_equipment.ExperiencedEquipment.id
import me.fzzyhmstrs.fzzy_config.annotations.NonSync
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.util.AllowableIdentifiers
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber.WidgetType
import net.minecraft.ChatFormatting
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items.*
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.IForgeRegistry
import java.awt.Color

class EEConfig : Config(id(ID)) {

    var equipmentMap = ValidatedMap.Builder<ResourceLocation, Int>()
        .keyHandler(ofForge(ForgeRegistries.ITEMS))
        .valueHandler(ValidatedInt(16, 1024, 1, WidgetType.TEXTBOX_WITH_BUTTONS))
        .defaults(DEFAULTS.mapKeys { it.key.builtInRegistryHolder().key().location() })
        .build()

    @NonSync
    var renderTooltip = true

    @NonSync
    var tooltipColor = ValidatedColor(Color(ChatFormatting.RED.color!!), false)

    fun getLevels(item: Item): Int? = equipmentMap[item.builtInRegistryHolder().key().location()]

    companion object {
        fun <T : Any> ofForge(registry: IForgeRegistry<T>): ValidatedIdentifier = ValidatedIdentifier(
            registry.defaultKey ?: ResourceLocation.fromNamespaceAndPath("empty", "empty"),
            AllowableIdentifiers({ id -> registry.containsKey(id) }, { registry.keys.toList() }, false)
        )

        var DEFAULTS = mapOf(
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
}