package com.theendercore.experienced_equipment

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.runWhenOn


@Mod(ExperiencedEquipment.ID)
object ExperiencedEquipment {
    const val ID = "experienced_equipment"
    var CONFIG = ConfigApi.registerAndLoadConfig(::EEConfig)
    val LOGGER: Logger = LogManager.getLogger(ID)
    fun id(path: String) = ResourceLocation.fromNamespaceAndPath(ID, path)


    init {
        FORGE_BUS.addListener(::onPlayerInteract)
        FORGE_BUS.addListener(::onChangeEquipment)
        FORGE_BUS.addListener(::onXPChange)

        runWhenOn(Dist.CLIENT) { FORGE_BUS.addListener(::onTooltip) }
    }

    fun Player.tryDrop(item: ItemStack) {
        val clone = item.copy()
        item.count = 0
        if (!this.addItem(clone)) this.drop(clone, false)
    }

    @JvmStatic
    fun shouldCancelCheck(player: Player, item: Item): Boolean {
        if (player.isCreative) return false
        val levels = CONFIG.equipmentMap[item] ?: return false
        return player.experienceLevel < levels
    }
}



