package net.tarantel.chickenroost.util;

import com.google.common.collect.Iterables;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;

public final class RoostItemContainerContents {
    public static final RoostItemContainerContents EMPTY = new RoostItemContainerContents(NonNullList.create());
    public static final Codec<RoostItemContainerContents> CODEC = Slot.CODEC
            .sizeLimitedListOf(2147483)
            .xmap(RoostItemContainerContents::fromSlots, RoostItemContainerContents::asSlots);
    public static final StreamCodec<RegistryFriendlyByteBuf, RoostItemContainerContents> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC
            .apply(ByteBufCodecs.list(2147483))
            .map(RoostItemContainerContents::new, p_331691_ -> p_331691_.items);
    private final NonNullList<ItemStack> items;
    private final int hashCode;

    private RoostItemContainerContents(NonNullList<ItemStack> items) {
        if (items.size() > 2147483) {
            throw new IllegalArgumentException("Got " + items.size() + " items, but maximum is 256");
        } else {
            this.items = items;
            this.hashCode = ItemStack.hashStackList(items);
        }
    }

    private RoostItemContainerContents(int size) {
        this(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    private RoostItemContainerContents(List<ItemStack> items) {
        this(items.size());

        for (int i = 0; i < items.size(); i++) {
            this.items.set(i, items.get(i));
        }
    }

    private static RoostItemContainerContents fromSlots(List<Slot> slots) {
        OptionalInt optionalint = slots.stream().mapToInt(Slot::index).max();
        if (optionalint.isEmpty()) {
            return EMPTY;
        } else {
            RoostItemContainerContents itemcontainercontents = new RoostItemContainerContents(optionalint.getAsInt() + 1);

            for (Slot itemcontainercontents$slot : slots) {
                itemcontainercontents.items.set(itemcontainercontents$slot.index(), itemcontainercontents$slot.item());
            }

            return itemcontainercontents;
        }
    }

    public static RoostItemContainerContents fromItems(List<ItemStack> items) {
        int i = findLastNonEmptySlot(items);
        if (i == -1) {
            return EMPTY;
        } else {
            RoostItemContainerContents itemcontainercontents = new RoostItemContainerContents(i + 1);

            for (int j = 0; j <= i; j++) {
                itemcontainercontents.items.set(j, items.get(j).copy());
            }

            return itemcontainercontents;
        }
    }

    private static int findLastNonEmptySlot(List<ItemStack> items) {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (!items.get(i).isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    private List<Slot> asSlots() {
        List<Slot> list = new ArrayList<>();

        for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemstack = this.items.get(i);
            if (!itemstack.isEmpty()) {
                list.add(new Slot(i, itemstack));
            }
        }

        return list;
    }

    public void copyInto(NonNullList<ItemStack> list) {
        for (int i = 0; i < list.size(); i++) {
            ItemStack itemstack = i < this.items.size() ? this.items.get(i) : ItemStack.EMPTY;
            list.set(i, itemstack.copy());
        }
    }

    public ItemStack copyOne() {
        return this.items.isEmpty() ? ItemStack.EMPTY : this.items.get(0).copy();
    }

    public Stream<ItemStack> stream() {
        return this.items.stream().map(ItemStack::copy);
    }

    public Stream<ItemStack> nonEmptyStream() {
        return this.items.stream().filter(p_331322_ -> !p_331322_.isEmpty()).map(ItemStack::copy);
    }

    public Iterable<ItemStack> nonEmptyItems() {
        return Iterables.filter(this.items, p_331420_ -> !p_331420_.isEmpty());
    }

    public Iterable<ItemStack> nonEmptyItemsCopy() {
        return Iterables.transform(this.nonEmptyItems(), ItemStack::copy);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof RoostItemContainerContents itemcontainercontents && ItemStack.listMatches(this.items, itemcontainercontents.items);
        }
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }


    public int getSlots() {
        return this.items.size();
    }


    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);
        return this.items.get(slot).copy();
    }


    private void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= getSlots()) {
            throw new UnsupportedOperationException("Slot " + slot + " not in valid range - [0," + getSlots() + ")");
        }
    }

    static record Slot(int index, ItemStack item) {
        public static final Codec<Slot> CODEC = RecordCodecBuilder.create(
                p_331695_ -> p_331695_.group(
                                Codec.intRange(0, 2147483).fieldOf("slot").forGetter(Slot::index),
                                ItemStack.CODEC.fieldOf("item").forGetter(Slot::item)
                        )
                        .apply(p_331695_, Slot::new)
        );
    }
}
