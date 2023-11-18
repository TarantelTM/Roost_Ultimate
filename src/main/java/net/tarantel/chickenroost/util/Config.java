package net.tarantel.chickenroost.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("ALL")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static final ForgeConfigSpec.Builder mycfg = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec SPEC;

    static {
        initmycfg(mycfg);
        SPEC = mycfg.build();

    }

    public static ForgeConfigSpec.ConfigValue<Integer> roost_speed_tick;
    public static ForgeConfigSpec.ConfigValue<Integer> breed_speed_tick;
    public static ForgeConfigSpec.ConfigValue<Integer> training_speed_tick;
    public static ForgeConfigSpec.ConfigValue<Float> roostxp;
    public static ForgeConfigSpec.ConfigValue<Boolean> handheldcraft;

    public static ForgeConfigSpec.ConfigValue<Integer> extractor_speedtimer;
    public static ForgeConfigSpec.ConfigValue<Integer> soulbreed_speedtimer;
    public static ForgeConfigSpec.ConfigValue<Integer> trainingxp_perfood;
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_1;
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_2;
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_3;
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_4;
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_5;
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_6;
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_7;
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_8;
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_9;

    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_1;

    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_2;

    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_3;

    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_4;

    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_5;

    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_6;

    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_7;

    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_8;
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_9;

    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_1;
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_2;
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_3;
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_4;
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_5;
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_6;
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_7;
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_8;
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_9;


    public static void initmycfg(ForgeConfigSpec.Builder mycfg) {
        mycfg.push("Roost Ultimate Config");
        mycfg.comment("Enable/Disable Handheld Crafting via Guidebook");
        handheldcraft = mycfg.define("Guidebook Crafting Grid", true);
        mycfg.comment("Production Speed in Seconds");
        roost_speed_tick = mycfg.define("Roost Speed", 20);
        breed_speed_tick = mycfg.define("Breeder Speed", 30);
        training_speed_tick = mycfg.define("Trainer Speed", 25);
        extractor_speedtimer = mycfg.define("Soul Extractor Speed", 10);
        soulbreed_speedtimer = mycfg.define("Soul Breeder Speed", 40);
        mycfg.comment("             ");
        mycfg.comment("             ");
        mycfg.comment("Seeds Tier 1-9 and Vanilla is the XP Amount for the tiered Seeds.");
        trainingxp_perfood = mycfg.define("Vanilla Seeds", 20);
        food_xp_tier_1 = mycfg.define("Seeds Tier 1", 100);
        food_xp_tier_2 = mycfg.define("Seeds Tier 2", 125);
        food_xp_tier_3 = mycfg.define("Seeds Tier 3", 150);
        food_xp_tier_4 = mycfg.define("Seeds Tier 4", 300);
        food_xp_tier_5 = mycfg.define("Seeds Tier 5", 350);
        food_xp_tier_6 = mycfg.define("Seeds Tier 6", 500);
        food_xp_tier_7 = mycfg.define("Seeds Tier 7", 700);
        food_xp_tier_8 = mycfg.define("Seeds Tier 8", 1000);
        food_xp_tier_9 = mycfg.define("Seeds Tier 9", 2500);
        mycfg.comment("             ");
        mycfg.comment("             ");
        mycfg.comment("XP Tier 1-9 - is the needed XP Amount for a levelup for each Tier of Chicken.");
        xp_tier_1 = mycfg.define("XP Tier 1", 500);
        xp_tier_2 = mycfg.define("XP Tier 2", 2500);
        xp_tier_3 = mycfg.define("XP Tier 3", 12500);
        xp_tier_4 = mycfg.define("XP Tier 4", 62500);
        xp_tier_5 = mycfg.define("XP Tier 5", 125000);
        xp_tier_6 = mycfg.define("XP Tier 6", 250000);
        xp_tier_7 = mycfg.define("XP Tier 7", 325000);
        xp_tier_8 = mycfg.define("XP Tier 8", 400000);
        xp_tier_9 = mycfg.define("XP Tier 9", 500000);
        mycfg.comment("             ");
        mycfg.comment("             ");
        mycfg.comment("Tier 1-9 - sets the maxlevel for each Tier of Chicken.");
        maxlevel_tier_1 = mycfg.define("Max Level Tier 1", 60);
        maxlevel_tier_2 = mycfg.define("Max Level Tier 2", 60);
        maxlevel_tier_3 = mycfg.define("Max Level Tier 3", 60);
        maxlevel_tier_4 = mycfg.define("Max Level Tier 4", 60);
        maxlevel_tier_5 = mycfg.define("Max Level Tier 5", 60);
        maxlevel_tier_6 = mycfg.define("Max Level Tier 6", 60);
        maxlevel_tier_7 = mycfg.define("Max Level Tier 7", 60);
        maxlevel_tier_8 = mycfg.define("Max Level Tier 8", 60);
        maxlevel_tier_9 = mycfg.define("Max Level Tier 9", 60);
        mycfg.pop();
    }



/*
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        ChickenRoostMod.addNetworkMessage(SavedDataSyncMessage.class, SavedDataSyncMessage::buffer, SavedDataSyncMessage::new,
                SavedDataSyncMessage::handler);
        ChickenRoostMod.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new,
                PlayerVariablesSyncMessage::handler);
    }

    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent event) {
        event.register(PlayerVariables.class);
    }

    @Mod.EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide())
                ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
                        .syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (!event.getEntity().level().isClientSide())
                ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
                        .syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (!event.getEntity().level().isClientSide())
                ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
                        .syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            event.getOriginal().revive();
            PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new PlayerVariables()));
            PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new PlayerVariables()));
            clone.regcooldown = original.regcooldown;
            clone.mana = original.mana;
            clone.insertorplacechicken = original.insertorplacechicken;
            if (!event.isWasDeath()) {
            }
        }

        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                SavedData mapdata = MapVariables.get(event.getEntity().level());
                SavedData worlddata = WorldVariables.get(event.getEntity().level());
                if (mapdata != null)
                    ChickenRoostMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()),
                            new SavedDataSyncMessage(0, mapdata));
                if (worlddata != null)
                    ChickenRoostMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()),
                            new SavedDataSyncMessage(1, worlddata));
            }
        }

        @SubscribeEvent
        public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                SavedData worlddata = WorldVariables.get(event.getEntity().level());
                if (worlddata != null)
                    ChickenRoostMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()),
                            new SavedDataSyncMessage(1, worlddata));
            }
        }
    }

    public static class WorldVariables extends SavedData {
        public static final String DATA_NAME = "chicken_roost_worldvars";

        public static WorldVariables load(CompoundTag tag) {
            WorldVariables data = new WorldVariables();
            data.read(tag);
            return data;
        }

        public void read(CompoundTag nbt) {
        }

        @Override
        public CompoundTag save(CompoundTag nbt) {
            return nbt;
        }

        public void syncData(LevelAccessor world) {
            this.setDirty();
            if (world instanceof Level level && !level.isClientSide())
                ChickenRoostMod.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(level::dimension), new SavedDataSyncMessage(1, this));
        }

        static WorldVariables clientSide = new WorldVariables();

        public static WorldVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel level) {
                return level.getDataStorage().computeIfAbsent(e -> WorldVariables.load(e), WorldVariables::new, DATA_NAME);
            } else {
                return clientSide;
            }
        }
    }

    public static class MapVariables extends SavedData {
        public static final String DATA_NAME = "chicken_roost_mapvars";
        public ItemStack testttt = ItemStack.EMPTY;
        public String asecondstring = "\"\"";

        public static MapVariables load(CompoundTag tag) {
            MapVariables data = new MapVariables();
            data.read(tag);
            return data;
        }

        public void read(CompoundTag nbt) {
            testttt = ItemStack.of(nbt.getCompound("testttt"));
            asecondstring = nbt.getString("asecondstring");
        }

        @Override
        public CompoundTag save(CompoundTag nbt) {
            nbt.put("testttt", testttt.save(new CompoundTag()));
            nbt.putString("asecondstring", asecondstring);
            return nbt;
        }

        public void syncData(LevelAccessor world) {
            this.setDirty();
            if (world instanceof Level && !world.isClientSide())
                ChickenRoostMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new SavedDataSyncMessage(0, this));
        }

        static MapVariables clientSide = new MapVariables();

        public static MapVariables get(LevelAccessor world) {
            if (world instanceof ServerLevelAccessor serverLevelAcc) {
                return serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(e -> MapVariables.load(e),
                        MapVariables::new, DATA_NAME);
            } else {
                return clientSide;
            }
        }
    }

    public static class SavedDataSyncMessage {
        public int type;
        public SavedData data;

        public SavedDataSyncMessage(FriendlyByteBuf buffer) {
            this.type = buffer.readInt();
            this.data = this.type == 0 ? new MapVariables() : new WorldVariables();
            if (this.data instanceof MapVariables _mapvars)
                _mapvars.read(buffer.readNbt());
            else if (this.data instanceof WorldVariables _worldvars)
                _worldvars.read(buffer.readNbt());
        }

        public SavedDataSyncMessage(int type, SavedData data) {
            this.type = type;
            this.data = data;
        }

        public static void buffer(SavedDataSyncMessage message, FriendlyByteBuf buffer) {
            buffer.writeInt(message.type);
            buffer.writeNbt(message.data.save(new CompoundTag()));
        }

        public static void handler(SavedDataSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    if (message.type == 0)
                        MapVariables.clientSide = (MapVariables) message.data;
                    else
                        WorldVariables.clientSide = (WorldVariables) message.data;
                }
            });
            context.setPacketHandled(true);
        }
    }

    public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
    });

    @Mod.EventBusSubscriber
    private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
                event.addCapability(new ResourceLocation("chicken_roost", "player_variables"), new PlayerVariablesProvider());
        }

        private final PlayerVariables playerVariables = new PlayerVariables();
        private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
        }

        @Override
        public Tag serializeNBT() {
            return playerVariables.writeNBT();
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            playerVariables.readNBT(nbt);
        }
    }

    public static class PlayerVariables {
        public double regcooldown = 0;
        public double mana = 0;
        public boolean insertorplacechicken = false;

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer)
                ChickenRoostMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
        }

        public Tag writeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putDouble("regcooldown", regcooldown);
            nbt.putDouble("mana", mana);
            nbt.putBoolean("insertorplacechicken", insertorplacechicken);
            return nbt;
        }

        public void readNBT(Tag Tag) {
            CompoundTag nbt = (CompoundTag) Tag;
            regcooldown = nbt.getDouble("regcooldown");
            mana = nbt.getDouble("mana");
            insertorplacechicken = nbt.getBoolean("insertorplacechicken");
        }
    }

    public static class PlayerVariablesSyncMessage {
        public PlayerVariables data;

        public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
            this.data = new PlayerVariables();
            this.data.readNBT(buffer.readNbt());
        }

        public PlayerVariablesSyncMessage(PlayerVariables data) {
            this.data = data;
        }

        public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
            buffer.writeNbt((CompoundTag) message.data.writeNBT());
        }

        public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null)
                            .orElse(new PlayerVariables()));
                    variables.regcooldown = message.data.regcooldown;
                    variables.mana = message.data.mana;
                    variables.insertorplacechicken = message.data.insertorplacechicken;
                }
            });
            context.setPacketHandled(true);
        }
    }*/
}
