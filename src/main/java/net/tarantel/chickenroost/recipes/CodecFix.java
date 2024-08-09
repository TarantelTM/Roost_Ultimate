package net.tarantel.chickenroost.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;


import java.util.Optional;

public final class CodecFix {
    private CodecFix() {}

    public static final Codec<ItemStack> ITEM_STACK_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ItemStack.ITEM_NON_AIR_CODEC.fieldOf("item").forGetter(ItemStack::getItemHolder),
                        ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(ItemStack::getCount),
                        DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).
                                forGetter(ItemStack::getComponentsPatch)).
                apply(instance, ItemStack::new);
    });
}