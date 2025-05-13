package com.theendercore.experienced_equipment.data

import com.mojang.serialization.JsonOps
import com.theendercore.experienced_equipment.ExperiencedEquipment.ID
import com.theendercore.experienced_equipment.ExperiencedEquipment.id
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items.*
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.common.data.JsonCodecProvider
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.BiConsumer
import kotlin.jvm.optionals.getOrNull


class EquipmentLevelGen(output: PackOutput, fh: ExistingFileHelper) : JsonCodecProvider<EquipmentLevel>(
    output, fh, ID,
    JsonOps.INSTANCE,
    PackType.SERVER_DATA,
    "$ID/equipment_level",
    EquipmentLevel.CODEC,
    mutableMapOf<ResourceLocation, EquipmentLevel>()
) {

    override fun gather(gen: BiConsumer<ResourceLocation, EquipmentLevel>) {
        super.gather(gen)
        gen.makeAll(
            5,
            CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS,
            GOLDEN_HELMET, GOLDEN_CHESTPLATE, GOLDEN_LEGGINGS, GOLDEN_BOOTS,
        )
        gen.makeAll(15, IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS)
        gen.makeAll(30, DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS)
        gen.makeAll(45, NETHERITE_HELMET, NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS, NETHERITE_BOOTS)
    }


    fun BiConsumer<ResourceLocation, EquipmentLevel>.makeAll(levels: Int, vararg items: Item) =
        items.forEach { this.make(it, levels) }

    fun BiConsumer<ResourceLocation, EquipmentLevel>.make(item: Item, levels: Int) {
        this.accept(
            id(ForgeRegistries.ITEMS.getResourceKey(item).getOrNull()?.location()?.path ?: error("Item not Found!")),
            EquipmentLevel(item, levels)
        )
    }

}