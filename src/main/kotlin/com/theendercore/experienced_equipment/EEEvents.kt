package com.theendercore.experienced_equipment

import com.theendercore.experienced_equipment.ExperiencedEquipment.canUseCheck
import com.theendercore.experienced_equipment.ExperiencedEquipment.tryDrop
import com.theendercore.experienced_equipment.data.EquipmentLevel
import com.theendercore.experienced_equipment.data.EquipmentLevel.Companion.EQUIPMENT_LEVEL_REGISTRY_KEY
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.commands.Commands.literal
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.entity.player.PlayerXpEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.registries.DataPackRegistryEvent
import kotlin.jvm.optionals.getOrNull

//Server
@SubscribeEvent(priority = EventPriority.HIGHEST)
fun onPlayerInteract(event: PlayerInteractEvent) {
    if (event is PlayerInteractEvent.LeftClickBlock || event is PlayerInteractEvent.LeftClickEmpty) return
    if (canUseCheck(event.entity, event.itemStack.item)) event.isCanceled = true
}

@SubscribeEvent(priority = EventPriority.HIGHEST)
fun onChangeEquipment(event: LivingEquipmentChangeEvent) {
    val player = event.entity
    if (player !is Player) return
    if (player.isCreative) return
    if (!event.slot.isArmor) return

    val item = event.to
    if (canUseCheck(player, item.item)) player.tryDrop(item)
}

@SubscribeEvent(priority = EventPriority.HIGHEST)
fun onXPChange(event: PlayerXpEvent) {
    val player = event.entity
    player.armorSlots.forEach {
        if (canUseCheck(player, it.item)) player.tryDrop(it)
    }
}

fun onCreateDynReg(event: DataPackRegistryEvent.NewRegistry) {
    event.dataPackRegistry(EQUIPMENT_LEVEL_REGISTRY_KEY, EquipmentLevel.CODEC, EquipmentLevel.CODEC)
}

fun commandEvent(event: RegisterCommandsEvent) {
    val root = literal("dump_level_values").requires { it.player?.hasPermissions(2) ?: true }.executes {
        val world = it.source.level
        val player = it.source.player ?: return@executes 0
        val reg = world.registryAccess().registry(EQUIPMENT_LEVEL_REGISTRY_KEY).getOrNull()?.holders()
        if (reg == null) {
            player.sendSystemMessage(Component.literal("Failed to get Registry!"))
            return@executes -1
        } else reg.forEach { holder ->
            val (item, levels) = holder.get()
            player.sendSystemMessage(Component.literal("${holder.key().location()} - [ $item : $levels ]"), false)
        }

        1
    }.build()
    event.dispatcher.root.addChild(root)
}

// Client
fun onTooltip(event: ItemTooltipEvent) {
    val levels = Minecraft.getInstance().level?.registryAccess()
        ?.registry(EQUIPMENT_LEVEL_REGISTRY_KEY)?.getOrNull()
        ?.find { it.item == event.itemStack.item }
        ?.levels ?: return
    val canRender = event.entity == null || event.entity!!.experienceLevel < levels
    if (canRender) event.toolTip.add(
        1, Component.literal("You need to be level $levels to equip this item!").withStyle(ChatFormatting.RED)
    )
}
