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
    @Shadow @Final
    EquipmentSlot val$equipmentslot;
    @Shadow @Final
    Player val$p_39708_;

    @ModifyReturnValue(method = "mayPlace", at = @At("RETURN"))
    boolean preventSlotPlace(boolean original, ItemStack stack) {
        if (original) {
            if (this.val$equipmentslot.isArmor() && checkIfShouldCancel(this.val$p_39708_, stack.getItem())) return false;
        }
        return original;
    }
}
