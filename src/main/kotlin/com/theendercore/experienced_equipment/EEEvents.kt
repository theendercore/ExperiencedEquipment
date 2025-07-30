package com.theendercore.experienced_equipment

import com.theendercore.experienced_equipment.ExperiencedEquipment.CONFIG
import com.theendercore.experienced_equipment.ExperiencedEquipment.shouldCancelCheck
import com.theendercore.experienced_equipment.ExperiencedEquipment.tryDrop
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.entity.player.PlayerXpEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent

//Server
@SubscribeEvent(priority = EventPriority.HIGHEST)
fun onPlayerInteract(event: PlayerInteractEvent) {
    if (event is PlayerInteractEvent.LeftClickBlock || event is PlayerInteractEvent.LeftClickEmpty) return
    if (shouldCancelCheck(event.entity, event.itemStack.item)) event.isCanceled = true
}

@SubscribeEvent(priority = EventPriority.HIGHEST)
fun onChangeEquipment(event: LivingEquipmentChangeEvent) {
    val player = event.entity
    if (player !is Player) return
    if (!event.slot.isArmor) return

    val item = event.to
    if (shouldCancelCheck(player, item.item)) player.tryDrop(item)
}

@SubscribeEvent(priority = EventPriority.HIGHEST)
fun onXPChange(event: PlayerXpEvent) {
    val player = event.entity
    player.armorSlots.forEach {
        if (shouldCancelCheck(player, it.item)) player.tryDrop(it)
    }
}

// Client
fun onTooltip(event: ItemTooltipEvent) {
    val levels = CONFIG.equipmentMap[event.itemStack.item] ?: return
    val canRender = event.entity == null || event.entity!!.experienceLevel < levels
    if (canRender) event.toolTip.add(
        1, Component.literal("You need to be level $levels to equip this item!").withStyle(ChatFormatting.RED)
    )
}
