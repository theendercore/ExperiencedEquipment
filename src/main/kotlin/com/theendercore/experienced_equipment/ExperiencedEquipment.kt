package com.theendercore.experienced_equipment

import com.theendercore.experienced_equipment.data.EquipmentLevel.Companion.EQUIPMENT_LEVEL_REGISTRY_KEY
import com.theendercore.experienced_equipment.data.EquipmentLevelGen
import net.minecraft.data.DataProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runWhenOn
import kotlin.jvm.optionals.getOrNull


@Mod(ExperiencedEquipment.ID)
object ExperiencedEquipment {
    const val ID = "experienced_equipment"
    val LOGGER: Logger = LogManager.getLogger(ID)
    fun id(path: String) = ResourceLocation.fromNamespaceAndPath(ID, path)


    init {
        FORGE_BUS.addListener(::onPlayerInteract)
        FORGE_BUS.addListener(::onChangeEquipment)
        FORGE_BUS.addListener(::onXPChange)
        FORGE_BUS.addListener(::commandEvent)

        MOD_BUS.addListener(::onCreateDynReg)
        MOD_BUS.addListener(::dataGen)

        runWhenOn(Dist.CLIENT) { FORGE_BUS.addListener(::onTooltip) }
    }

    fun Player.tryDrop(item: ItemStack) {
        val clone = item.copy()
        item.count = 0
        if (!this.addItem(clone)) this.drop(clone, false)
    }

    @JvmStatic
    fun checkIfShouldCancel(player: Player, item: Item): Boolean {
        if (player.isCreative) return false
        val levels = player.level().registryAccess()
            .registry(EQUIPMENT_LEVEL_REGISTRY_KEY).getOrNull()
            ?.find { it.item == item }
            ?.levels ?: return false
        return player.experienceLevel < levels
    }

    fun dataGen(event: GatherDataEvent) {
        val generator = event.generator
        generator.addProvider(true, DataProvider.Factory { EquipmentLevelGen(it, event.existingFileHelper) })
    }
}



