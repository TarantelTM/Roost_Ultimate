//package net.tarantel.chickenroost.api.rei.displays;
//
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import me.shedaniel.rei.api.common.category.CategoryIdentifier;
//import me.shedaniel.rei.api.common.display.Display;
//import me.shedaniel.rei.api.common.display.DisplaySerializer;
//import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
//import me.shedaniel.rei.api.common.entry.EntryIngredient;
//import me.shedaniel.rei.api.common.util.EntryIngredients;
//import me.shedaniel.rei.plugin.common.displays.cooking.DefaultSmokingDisplay;
//import net.minecraft.network.codec.ByteBufCodecs;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.resources.Identifier;
//import net.minecraft.world.item.crafting.RecipeHolder;
//import net.tarantel.chickenroost.ChickenRoostMod;
//import net.tarantel.chickenroost.recipes.ThrowEggRecipe;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//public class EggREIDisplay extends BasicDisplay {
//
//
//    public static final DisplaySerializer<EggREIDisplay> SERIALIZER =
//            DisplaySerializer.of(
//                    // -------- MapCodec (Datapack / Save) --------
//                    RecordCodecBuilder.mapCodec(instance -> instance.group(
//                            EntryIngredient.codec().listOf()
//                                    .fieldOf("inputs")
//                                    .forGetter(EggREIDisplay::getInputEntries),
//
//                            EntryIngredient.codec().listOf()
//                                    .fieldOf("outputs")
//                                    .forGetter(EggREIDisplay::getOutputEntries),
//
//                            Identifier.CODEC.optionalFieldOf("location")
//                                    .forGetter(EggREIDisplay::getDisplayLocation)
//                    ).apply(instance, EggREIDisplay::new)),
//
//                    // -------- StreamCodec (Network) --------
//                    StreamCodec.composite(
//                            EntryIngredient.streamCodec()
//                                    .apply(ByteBufCodecs.list()),
//                            EggREIDisplay::getInputEntries,
//
//                            EntryIngredient.streamCodec()
//                                    .apply(ByteBufCodecs.list()),
//                            EggREIDisplay::getOutputEntries,
//
//                            ByteBufCodecs.optional(Identifier.STREAM_CODEC),
//                            EggREIDisplay::getDisplayLocation,
//
//                            EggREIDisplay::new
//                    )
//            );
//
//    public EggREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
//        super(inputs, outputs);
//    }
//
//    public static final CategoryIdentifier<EggREIDisplay> ID = CategoryIdentifier.of(ChickenRoostMod.MODID, "throwegg");
//
//
//    public EggREIDisplay(RecipeHolder<ThrowEggRecipe> recipe){
//        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryIngredients.of(recipe.value().getResultItem(null)))));
//    }
//
//    public EggREIDisplay(List<EntryIngredient> input, List<EntryIngredient> output, Optional<Identifier> location) {
//        super(input, output, location);
//    }
//
//    private static List<EntryIngredient> getInputList(ThrowEggRecipe recipe) {
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