package mcjty.lib.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public interface GenericArmorMaterial extends ArmorMaterial {

    int getDurabilityForType(EquipmentSlot slot);

    int getDefenseForType(EquipmentSlot slot);

    @Override
    default int getDurabilityForSlot(EquipmentSlot pSlot) {
        return getDurabilityForType(pSlot);
    }

    @Override
    default int getDefenseForSlot(EquipmentSlot pSlot) {
        return getDefenseForType(pSlot);
    }
}
