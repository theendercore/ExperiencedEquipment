package com.theendercore.experienced_equipment.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.theendercore.experienced_equipment.ExperiencedEquipment
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries

data class EquipmentLevel(val item: Item, val levels: Int) {
    companion object {
        val CODEC: Codec<EquipmentLevel> = RecordCodecBuilder.create<EquipmentLevel> { inst ->
            inst.group(
                ForgeRegistries.ITEMS.codec.fieldOf("item").forGetter { it.item },
                Codec.INT.fieldOf("levels").forGetter { it.levels }
            ).apply(inst, ::EquipmentLevel)
        }
        val EQUIPMENT_LEVEL_REGISTRY_KEY: ResourceKey<Registry<EquipmentLevel>> =
            ResourceKey.createRegistryKey(ExperiencedEquipment.id("equipment_level"))
    }

}