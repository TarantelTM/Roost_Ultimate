package net.tarantel.chickenroost.util;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tarantel.chickenroost.item.base.AnimatedChicken;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

@Mod.EventBusSubscriber
public class DumpChickenItemsCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("dumpChickenItems")
                        .requires(source -> source.hasPermission(2))
                        .executes(ctx -> dump(ctx.getSource()))
        );
    }

    private static int dump(CommandSourceStack source) {
        Path output = source.getServer()
                .getServerDirectory()
                .toPath()
                .resolve("chicken_items.txt");

        int count = 0;

        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            for (var item : BuiltInRegistries.ITEM) {

                // 🔴 FILTER BY CLASS
                if (!(item instanceof AnimatedChicken)) continue;

                ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                if (id == null) continue;

                String translationKey = item.getDescriptionId();

                // Resolve translation using server language (usually en_us)
                String translatedName = Component.translatable(translationKey)
                        .getString();

                writer.write(
                        id + " | " + translationKey + " | " + translatedName
                );
                writer.newLine();
                count++;
            }
        } catch (IOException e) {
            source.sendFailure(Component.literal("Failed to write chicken_items.txt"));
            e.printStackTrace();
            return 0;
        }

        int finalCount = count;
        source.sendSuccess(
                () -> Component.literal("Dumped " + finalCount + " chicken items to chicken_items.txt"),
                true
        );

        return count;
    }
}