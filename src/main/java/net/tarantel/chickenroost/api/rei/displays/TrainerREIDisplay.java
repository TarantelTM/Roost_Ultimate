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
//import net.tarantel.chickenroost.recipes.TrainerRecipe;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//public class TrainerREIDisplay extends BasicDisplay {
//
//
//    public static final DisplaySerializer<TrainerREIDisplay> SERIALIZER =
//            DisplaySerializer.of(
//                    // ---------- MapCodec ----------
//                    RecordCodecBuilder.mapCodec(instance -> instance.group(
//                            EntryIngredient.codec().listOf()
//                                    .fieldOf("inputs")
//                                    .forGetter(TrainerREIDisplay::getInputEntries),
//
//                            EntryIngredient.codec().listOf()
//                                    .fieldOf("outputs")
//                                    .forGetter(TrainerREIDisplay::getOutputEntries),
//
//                            Identifier.CODEC.optionalFieldOf("location")
//                                    .forGetter(TrainerREIDisplay::getDisplayLocation)
//                    ).apply(instance, TrainerREIDisplay::new)),
//
//                    // ---------- StreamCodec ----------
//                    StreamCodec.composite(
//                            EntryIngredient.streamCodec()
//                                    .apply(ByteBufCodecs.list()),
//                            TrainerREIDisplay::getInputEntries,
//
//                            EntryIngredient.streamCodec()
//                                    .apply(ByteBufCodecs.list()),
//                            TrainerREIDisplay::getOutputEntries,
//
//                            ByteBufCodecs.optional(Identifier.STREAM_CODEC),
//                            TrainerREIDisplay::getDisplayLocation,
//
//                            TrainerREIDisplay::new
//                    )
//            );
//
//    public TrainerREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
//        super(inputs, outputs);
//    }
//
//    public static final CategoryIdentifier<TrainerREIDisplay> ID = CategoryIdentifier.of(ChickenRoostMod.MODID, "trainer_output");
//
//    public TrainerREIDisplay(RecipeHolder<TrainerRecipe> recipe){
//        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryIngredients.of(recipe.value().getResultItem(null)))));
//    }
//
//    public TrainerREIDisplay(List<EntryIngredient> input, List<EntryIngredient> output, Optional<Identifier> location) {
//        super(input, output, location);
//    }
//
//    private static List<EntryIngredient> getInputList(TrainerRecipe recipe) {
//        if(recipe == null) return Collections.emptyList();
//        List<EntryIngredient> list = new ArrayList<>();
//        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
//        return list;
//    }
//
//    @Override
//    public CategoryIdentifier<?> getCategoryIdentifier() {
//        return ID;
//    }
//
//    @Override
//    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
//        return SERIALIZER;
//    }
//}