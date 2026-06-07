package net.tarantel.chickenroost.block.blocks;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;
import net.tarantel.chickenroost.block.blocks.crops.CropBlock;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.pipe.PipeBlock;
import net.tarantel.chickenroost.pipe.PipeTier;
import org.jetbrains.annotations.NotNull;

public class ModBlocks {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("chicken_roost");
   public static final DeferredBlock<Block> COLLECTOR = registerBlock(
      "collector", () -> new CollectorBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion())
   );
   public static final DeferredBlock<Block> FEEDER = registerBlock(
      "feeder", () -> new FeederBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion())
   );
   public static final DeferredBlock<ChickenStorageBlock> CHICKENSTORAGE = BLOCKS.register(
      "chickenstorage",
      () -> new ChickenStorageBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(70.0F, 120000.0F).noOcclusion())
   );
   public static final DeferredBlock<Block> BREEDER = registerBlock(
      "breeder", () -> new BreederBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion())
   );
   public static final DeferredBlock<PipeBlock> PIPE_TIER1 = registerBlock(
      "pipe_tier1",
      () -> new PipeBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion(), PipeTier.TIER1, ModItems.CHICKEN_STICK
      ) {
         public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
            super.appendHoverText(itemstack, context, list, flag);
            list.add(Component.translatable("pipe.chicken_roost.transfer.info.tier1"));
            list.add(Component.translatable("pipe.chicken_roost.transfer.pipe.tier1"));
            list.add(Component.translatable("pipe.chicken_roost.transfer.pipe.modeinfo"));
         }
      }
   );
   public static final DeferredBlock<PipeBlock> PIPE_TIER2 = registerBlock(
      "pipe_tier2",
      () -> new PipeBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion(), PipeTier.TIER2, ModItems.CHICKEN_STICK
      ) {
         public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
            super.appendHoverText(itemstack, context, list, flag);
            list.add(Component.translatable("pipe.chicken_roost.transfer.info.tier2"));
            list.add(Component.translatable("pipe.chicken_roost.transfer.pipe.tier2"));
            list.add(Component.translatable("pipe.chicken_roost.transfer.pipe.modeinfo"));
         }
      }
   );
   public static final DeferredBlock<PipeBlock> PIPE_TIER3 = registerBlock(
      "pipe_tier3",
      () -> new PipeBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion(), PipeTier.TIER3, ModItems.CHICKEN_STICK
      ) {
         public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
            super.appendHoverText(itemstack, context, list, flag);
            list.add(Component.translatable("pipe.chicken_roost.transfer.info.tier3"));
            list.add(Component.translatable("pipe.chicken_roost.transfer.pipe.tier3"));
            list.add(Component.translatable("pipe.chicken_roost.transfer.pipe.modeinfo"));
         }
      }
   );
   public static final DeferredBlock<PipeBlock> PIPE_TIER4 = registerBlock(
      "pipe_tier4",
      () -> new PipeBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion(), PipeTier.TIER4, ModItems.CHICKEN_STICK
      ) {
         public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
            super.appendHoverText(itemstack, context, list, flag);
            list.add(Component.translatable("pipe.chicken_roost.transfer.info.tier4"));
            list.add(Component.translatable("pipe.chicken_roost.transfer.pipe.tier4"));
            list.add(Component.translatable("pipe.chicken_roost.transfer.pipe.modeinfo"));
         }
      }
   );
   public static final DeferredBlock<Block> SOUL_EXTRACTOR = registerBlock(
      "soul_extractor", () -> new SoulExtractorBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion())
   );
   public static final DeferredBlock<Block> ROOST = registerBlock(
      "roost", () -> new RoostBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion())
   );
   public static final DeferredBlock<Block> TRAINER = BLOCKS.register(
      "trainer", () -> new TrainerBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS).strength(1.0F).noOcclusion())
   );
   public static final DeferredBlock<SlimeBlock> SLIMEBLOCK = registerBlock(
      "slime_block",
      () -> new SlimeBlock(Properties.of().mapColor(MapColor.GRASS).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion().lightLevel(p_152607_ -> 15)) {
         public void updateEntityAfterFallOn(@NotNull BlockGetter level, @NotNull Entity entity) {
            if (entity.isSuppressingBounce()) {
               super.updateEntityAfterFallOn(level, entity);
            } else {
               this.bounceUp(entity);
            }
         }

         private void bounceUp(Entity entity) {
            Vec3 vec3 = entity.getDeltaMovement();
            if (vec3.y < 0.0) {
               double d0 = entity instanceof LivingEntity ? 10.0 : 0.8;
               entity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
            }
         }
      }
   );
   public static final DeferredBlock<Block> SEED_CROP_1 = BLOCKS.register(
      "seed_crop_1", () -> new CropBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT).noOcclusion().noCollission(), 0)
   );
   public static final DeferredBlock<Block> SEED_CROP_2 = BLOCKS.register(
      "seed_crop_2", () -> new CropBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT).noOcclusion().noCollission(), 1)
   );
   public static final DeferredBlock<Block> SEED_CROP_3 = BLOCKS.register(
      "seed_crop_3", () -> new CropBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT).noOcclusion().noCollission(), 2)
   );
   public static final DeferredBlock<Block> SEED_CROP_4 = BLOCKS.register(
      "seed_crop_4", () -> new CropBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT).noOcclusion().noCollission(), 3)
   );
   public static final DeferredBlock<Block> SEED_CROP_5 = BLOCKS.register(
      "seed_crop_5", () -> new CropBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT).noOcclusion().noCollission(), 4)
   );
   public static final DeferredBlock<Block> SEED_CROP_6 = BLOCKS.register(
      "seed_crop_6", () -> new CropBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT).noOcclusion().noCollission(), 5)
   );
   public static final DeferredBlock<Block> SEED_CROP_7 = BLOCKS.register(
      "seed_crop_7", () -> new CropBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT).noOcclusion().noCollission(), 6)
   );
   public static final DeferredBlock<Block> SEED_CROP_8 = BLOCKS.register(
      "seed_crop_8", () -> new CropBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT).noOcclusion().noCollission(), 7)
   );
   public static final DeferredBlock<Block> SEED_CROP_9 = BLOCKS.register(
      "seed_crop_9", () -> new CropBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT).noOcclusion().noCollission(), 8)
   );

   private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
      DeferredBlock<T> toReturn = BLOCKS.register(name, block);
      registerBlockItem(name, toReturn);
      return toReturn;
   }

   private static <T extends Block> DeferredItem<Item> registerBlockItem(String name, DeferredBlock<T> block) {
      return ModItems.ITEMSS.register(name, () -> new BlockItem((Block)block.get(), new net.minecraft.world.item.Item.Properties()));
   }

   public static void register(IEventBus eventBus) {
      BLOCKS.register(eventBus);
   }
}
