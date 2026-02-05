package net.tarantel.chickenroost.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class CodecFix {
    private CodecFix() {}

    public static final Codec<ItemStack> ITEM_STACK_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(

                    // item id (Holder<Item>)
                    Item.CODEC
                            .fieldOf("item")
                            .forGetter(ItemStack::getItemHolder),

                    // count
                    ExtraCodecs.POSITIVE_INT
                            .optionalFieldOf("count", 1)
                            .forGetter(ItemStack::getCount),

                    // components
                    DataComponentPatch.CODEC
                            .optionalFieldOf("components", DataComponentPatch.EMPTY)
                            .forGetter(stack -> stack.getComponentsPatch())

            ).apply(instance, ItemStack::new));
}
