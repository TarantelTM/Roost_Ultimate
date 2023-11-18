package net.tarantel.chickenroost.block.entity.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.tarantel.chickenroost.block.entity.roost_entity;
import net.tarantel.chickenroost.block.roost_block;

public class ChickenRendererRoost implements BlockEntityRenderer<roost_entity> {


    public ChickenRendererRoost(BlockEntityRendererFactory.Context context){

    }
    @Override
    public void render(roost_entity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack itemStack = entity.getRenderStack();
        matrices.push();
        //matrices.translate(0.4f, 0.5f, 0.5f);
        matrices.scale(1.0f, 1.0f, 1.0f);
        matrices.multiply(RotationAxis.NEGATIVE_X.rotation(0));



        switch (entity.getCachedState().get(roost_block.FACING)) {
            //DONE
            case NORTH -> {
                int l = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getCachedState(), entity.getPos().offset(Direction.NORTH));
                matrices.translate(0.5f, 0.5f, 0.6f);
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(0));
                //matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, l, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
                matrices.pop();
            }
            case EAST -> {
                int l = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getCachedState(), entity.getPos().offset(Direction.EAST));
                matrices.translate(0.4f, 0.5f, 0.5f);
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(0));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(+90));
                itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, l, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
                matrices.pop();
            }
            case SOUTH -> {
                int l = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getCachedState(), entity.getPos().offset(Direction.SOUTH));
                matrices.translate(0.5f, 0.5f, 0.4f);
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(0));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(-180));
                //matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
                itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, l, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
                matrices.pop();
            }
            //DONE
            case WEST ->
            {
                int l = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getCachedState(), entity.getPos().offset(Direction.WEST));
                matrices.translate(0.6f, 0.5f, 0.5f);
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(0));
                matrices.multiply(RotationAxis.NEGATIVE_X.rotation(0));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(-90));
                itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, l, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
                matrices.pop();
            }
        }



    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
