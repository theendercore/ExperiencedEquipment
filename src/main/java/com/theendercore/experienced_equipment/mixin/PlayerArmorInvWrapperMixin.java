package com.theendercore.experienced_equipment.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.PlayerArmorInvWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static com.theendercore.experienced_equipment.ExperiencedEquipment.checkIfShouldCancel;


@Mixin(value = PlayerArmorInvWrapper.class, remap = false)
abstract class PlayerArmorInvWrapperMixin {

    @Shadow(remap = false)
    public abstract Inventory getInventoryPlayer();

    @ModifyExpressionValue(method = "insertItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;canEquip(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/entity/Entity;)Z", remap = false), remap = false)
    boolean modifyItemInsertion(boolean original, int slot, ItemStack item, @Local EquipmentSlot eqSlot) {
        if (original) {
            if (eqSlot.isArmor() && checkIfShouldCancel(getInventoryPlayer().player, item.getItem())) return false;
        }
        return original;
    }
}
