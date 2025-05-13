package com.theendercore.experienced_equipment.data

import com.mojang.serialization.JsonOps
import com.theendercore.experienced_equipment.ExperiencedEquipment.ID
import com.theendercore.experienced_equipment.ExperiencedEquipment.id
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.world.item.Items
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.common.data.JsonCodecProvider
import java.util.function.BiConsumer


class EquipmentLevelGen(output: PackOutput, fh: ExistingFileHelper) : JsonCodecProvider<EquipmentLevel>(
    output, fh, ID,
    JsonOps.INSTANCE,
    PackType.SERVER_DATA,
    "$ID/equipment_level",
    EquipmentLevel.CODEC,
    mutableMapOf<ResourceLocation, EquipmentLevel>()
) {

    override fun gather(gen: BiConsumer<ResourceLocation?, EquipmentLevel?>) {
        super.gather(gen)
        gen.accept(id("test"), EquipmentLevel(Items.IRON_CHESTPLATE, 10))
        gen.accept(id("test2"), EquipmentLevel(Items.IRON_BOOTS, 10))
        gen.accept(id("test3"), EquipmentLevel(Items.IRON_HELMET, 10))
    }


}