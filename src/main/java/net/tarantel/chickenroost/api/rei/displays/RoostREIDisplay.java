//package net.tarantel.chickenroost.api.rei.displays;
//
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import me.shedaniel.rei.api.common.category.CategoryIdentifier;
//import me.shedaniel.rei.api.common.display.Display;
//import me.shedaniel.rei.api.common.display.DisplaySerializer;
//import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
//import me.shedaniel.rei.api.common.entry.EntryIngredient;
//import me.shedaniel.rei.api.common.util.EntryIngredients;
//import net.minecraft.network.codec.ByteBufCodecs;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.resources.Identifier;
//import net.minecraft.world.item.crafting.RecipeHolder;
//import net.tarantel.chickenroost.ChickenRoostMod;
//import net.tarantel.chickenroost.recipes.RoostRecipe;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//public class RoostREIDisplay extends BasicDisplay {
//
//    public static final DisplaySerializer<RoostREIDisplay> SERIALIZER =
//            DisplaySerializer.of(
//                    // ---------- MapCodec ----------
//                    RecordCodecBuilder.mapCodec(instance -> instance.group(
//                            EntryIngredient.codec().listOf()
//                                    .fieldOf("inputs")
//                                    .forGetter(RoostREIDisplay::getInputEntries),
//
//                            EntryIngredient.codec().listOf()
//                                    .fieldOf("outputs")
//                                    .forGetter(RoostREIDisplay::getOutputEntries),
//
//                            Identifier.CODEC.optionalFieldOf("location")
//                                    .forGetter(RoostREIDisplay::getDisplayLocation)
//                    ).apply(instance, RoostREIDisplay::new)),
//
//                    // ---------- StreamCodec ----------
//                    StreamCodec.composite(
//                            EntryIngredient.streamCodec()
//                                    .apply(ByteBufCodecs.list()),
//                            RoostREIDisplay::getInputEntries,
//
//                            EntryIngredient.streamCodec()
//                                    .apply(ByteBufCodecs.list()),
//                            RoostREIDisplay::getOutputEntries,
//
//                            ByteBufCodecs.optional(Identifier.STREAM_CODEC),
//                            RoostREIDisplay::getDisplayLocation,
//
//                            RoostREIDisplay::new
//                    )
//            );
//
//    public static final CategoryIdentifier<RoostREIDisplay> ID = CategoryIdentifier.of(ChickenRoostMod.MODID, "roost_output");
//
//
//    public RoostREIDisplay(RecipeHolder<RoostRecipe> recipe){
//        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryIngredients.of(recipe.value().getResultItem(null)))));
//    }
//
//    public RoostREIDisplay(List<EntryIngredient> input, List<EntryIngredient> output, Optional<Identifier> location) {
//        super(input, output, location);
//    }
//
//    private static List<EntryIngredient> getInputList(RoostRecipe recipe) {
//        if(recipe == null) return Collections.emptyList();
//        List<EntryIngredient> list = new ArrayList<>();
//        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
//        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(1)));
//        return list;
//    }
//
//    @Override
//    public CategoryIdentifier<?> getCategoryIdentifier() {
//
//        return ID;
//    }
//
//    @Override
//    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
//        return SERIALIZER;
//    }
//}