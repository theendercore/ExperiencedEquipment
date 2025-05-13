package com.theendercore.experienced_equipment.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static com.theendercore.experienced_equipment.ExperiencedEquipment.checkIfShouldCancel;


@Mixin(targets = "net.minecraft.world.inventory.InventoryMenu$1")
public abstract class InventoryArmorSlotMixin {
    @Shadow @Final Player val$pOwner;
    @Shadow @Final EquipmentSlot val$equipmentslot;

    @ModifyReturnValue(method = "mayPlace", at = @At("RETURN"))
    boolean preventSlotPlace(boolean original, ItemStack stack) {
        if (original) {
            if (val$equipmentslot.isArmor() && checkIfShouldCancel(val$pOwner, stack.getItem())) return false;
        }
        return original;
    }
}
