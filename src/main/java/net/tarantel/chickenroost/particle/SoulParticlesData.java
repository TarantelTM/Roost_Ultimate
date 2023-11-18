//package net.tarantel.chickenroost.particle;
//import java.util.Locale;
//
//import com.mojang.brigadier.StringReader;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//
//import net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration;
//import net.minecraft.core.particles.ParticleOptions;
//import net.minecraft.core.particles.ParticleType;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//public class SoulParticlesData implements ParticleOptions, ICustomParticleDataWithSprite<SoulParticlesData> {
//
//    public static final Codec<SoulParticlesData> CODEC = RecordCodecBuilder.create(i ->
//            i.group(
//                            Codec.FLOAT.fieldOf("drag").forGetter(p -> p.drag),
//                            Codec.FLOAT.fieldOf("speed").forGetter(p -> p.speed))
//                    .apply(i, SoulParticlesData::new));
//
//    public static final ParticleOptions.Deserializer<SoulParticlesData> DESERIALIZER =
//            new ParticleOptions.Deserializer<SoulParticlesData>() {
//                public SoulParticlesData fromCommand(ParticleType<SoulParticlesData> particleTypeIn, StringReader reader)
//                        throws CommandSyntaxException {
//                    reader.expect(' ');
//                    float drag = reader.readFloat();
//                    reader.expect(' ');
//                    float speed = reader.readFloat();
//                    return new SoulParticlesData(drag, speed);
//                }
//
//                public SoulParticlesData fromNetwork(ParticleType<SoulParticlesData> particleTypeIn, FriendlyByteBuf buffer) {
//                    return new SoulParticlesData(buffer.readFloat(), buffer.readFloat());
//                }
//            };
//
//    float drag;
//    float speed;
//
//    public SoulParticlesData(float drag, float speed) {
//        this.drag = drag;
//        this.speed = speed;
//    }
//
//    public SoulParticlesData() {
//        this(0, 0);
//    }
//
//    @Override
//    public ParticleType<?> getType() {
//        return AllParticleTypes.AIR.get();
//    }
//
//    @Override
//    public void writeToNetwork(FriendlyByteBuf buffer) {
//        buffer.writeFloat(drag);
//        buffer.writeFloat(speed);
//    }
//
//    @Override
//    public String writeToString() {
//        return String.format(Locale.ROOT, "%s %f %f", AllParticleTypes.AIR.parameter(), drag, speed);
//    }
//
//    @Override
//    public Deserializer<SoulParticlesData> getDeserializer() {
//        return DESERIALIZER;
//    }
//
//    @Override
//    public Codec<SoulParticlesData> getCodec(ParticleType<SoulParticlesData> type) {
//        return CODEC;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public SpriteParticleRegistration<SoulParticlesData> getMetaFactory() {
//        return SoulParticles.Factory::new;
//    }
//
//}