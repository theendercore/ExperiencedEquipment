package com.theendercore.experienced_equipment.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.theendercore.experienced_equipment.ExperiencedEquipment.canUseCheck;
import static net.minecraft.world.entity.EquipmentSlot.Type.ARMOR;
import static net.minecraft.world.entity.EquipmentSlot.Type.HAND;


@Debug(export = true)
@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin {
    @ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EquipmentSlot;getType()Lnet/minecraft/world/entity/EquipmentSlot$Type;"))
    EquipmentSlot.Type fixQuickMoveForBlockedItems(EquipmentSlot.Type original, Player player, @Local(ordinal = 1) ItemStack stack) {
        if (original == ARMOR) {
            if (canUseCheck(player, stack.getItem())) return HAND;
        }
        return original;
    }
}
