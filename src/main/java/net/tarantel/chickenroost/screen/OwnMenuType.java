package net.tarantel.chickenroost.screen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.*;

public class OwnMenuType<T extends AbstractContainerMenu> implements FeatureElement, net.neoforged.neoforge.common.extensions.IMenuTypeExtension<T> {
    public static final net.minecraft.world.inventory.MenuType<CraftingMenu> CRAFTING = register("crafting", CraftingMenu::new);

    private final FeatureFlagSet requiredFeatures;
    private final net.minecraft.world.inventory.MenuType.MenuSupplier<T> constructor;

    private static <T extends AbstractContainerMenu> net.minecraft.world.inventory.MenuType<T> register(String p_39989_, net.minecraft.world.inventory.MenuType.MenuSupplier<T> p_39990_) {
        return Registry.register(BuiltInRegistries.MENU, p_39989_, new net.minecraft.world.inventory.MenuType<>(p_39990_, FeatureFlags.VANILLA_SET));
    }

    private static <T extends AbstractContainerMenu> net.minecraft.world.inventory.MenuType<T> register(String p_267295_, net.minecraft.world.inventory.MenuType.MenuSupplier<T> p_266945_, FeatureFlag... p_267055_) {
        return Registry.register(BuiltInRegistries.MENU, p_267295_, new net.minecraft.world.inventory.MenuType<>(p_266945_, FeatureFlags.REGISTRY.subset(p_267055_)));
    }

    public OwnMenuType(net.minecraft.world.inventory.MenuType.MenuSupplier<T> p_267054_, FeatureFlagSet p_266909_) {
        this.constructor = p_267054_;
        this.requiredFeatures = p_266909_;
    }

    public T create(int p_39986_, Inventory p_39987_) {
        return this.constructor.create(p_39986_, p_39987_);
    }

    @Override
    public T create(int windowId, Inventory playerInv, net.minecraft.network.FriendlyByteBuf extraData) {
        if (this.constructor instanceof net.neoforged.neoforge.network.IContainerFactory) {
            return ((net.neoforged.neoforge.network.IContainerFactory<T>) this.constructor).create(windowId, playerInv, extraData);
        }
        return create(windowId, playerInv);
    }

    @Override
    public FeatureFlagSet requiredFeatures() {
        return this.requiredFeatures;
    }

    public interface MenuSupplier<T extends AbstractContainerMenu> {
        T create(int p_39995_, Inventory p_39996_);
    }
}