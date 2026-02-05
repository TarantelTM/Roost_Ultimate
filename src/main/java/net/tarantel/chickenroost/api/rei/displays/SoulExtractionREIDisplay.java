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
//import net.tarantel.chickenroost.recipes.SoulExtractorRecipe;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//public class SoulExtractionREIDisplay extends BasicDisplay {
//
//    public static final DisplaySerializer<SoulExtractionREIDisplay> SERIALIZER =
//            DisplaySerializer.of(
//                    // ---------- MapCodec ----------
//                    RecordCodecBuilder.mapCodec(instance -> instance.group(
//                            EntryIngredient.codec().listOf()
//                                    .fieldOf("inputs")
//                                    .forGetter(SoulExtractionREIDisplay::getInputEntries),
//
//                            EntryIngredient.codec().listOf()
//                                    .fieldOf("outputs")
//                                    .forGetter(SoulExtractionREIDisplay::getOutputEntries),
//
//                            Identifier.CODEC.optionalFieldOf("location")
//                                    .forGetter(SoulExtractionREIDisplay::getDisplayLocation)
//                    ).apply(instance, SoulExtractionREIDisplay::new)),
//
//                    // ---------- StreamCodec ----------
//                    StreamCodec.composite(
//                            EntryIngredient.streamCodec()
//                                    .apply(ByteBufCodecs.list()),
//                            SoulExtractionREIDisplay::getInputEntries,
//
//                            EntryIngredient.streamCodec()
//                                    .apply(ByteBufCodecs.list()),
//                            SoulExtractionREIDisplay::getOutputEntries,
//
//                            ByteBufCodecs.optional(Identifier.STREAM_CODEC),
//                            SoulExtractionREIDisplay::getDisplayLocation,
//
//                            SoulExtractionREIDisplay::new
//                    )
//            );
//
//    public SoulExtractionREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
//        super(inputs, outputs);
//    }
//
//    public static final CategoryIdentifier<SoulExtractionREIDisplay> ID = CategoryIdentifier.of(ChickenRoostMod.MODID, "soul_extraction");
//
//
//    public SoulExtractionREIDisplay(RecipeHolder<SoulExtractorRecipe> recipe){
//        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryIngredients.of(recipe.value().getResultItem(null)))));
//    }
//
//    public SoulExtractionREIDisplay(List<EntryIngredient> input, List<EntryIngredient> output, Optional<Identifier> location) {
//        super(input, output, location);
//    }
//
//    private static List<EntryIngredient> getInputList(SoulExtractorRecipe recipe) {
//        if(recipe == null) return Collections.emptyList();
//        List<EntryIngredient> list = new ArrayList<>();
//        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
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