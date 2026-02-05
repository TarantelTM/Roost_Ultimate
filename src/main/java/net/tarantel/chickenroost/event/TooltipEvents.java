package net.tarantel.chickenroost.event;

import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.client.ClientRoostCache;
import net.tarantel.chickenroost.client.tooltip.StackLineTooltip;

@Mod.EventBusSubscriber(
        modid = ChickenRoostMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public class TooltipEvents {

    @SubscribeEvent
    public static void onGatherTooltip(RenderTooltipEvent.GatherComponents e) {
        ItemStack stack = e.getItemStack();
        if (stack.isEmpty()) return;

        var recipes = ClientRoostCache.getRecipes(stack);
        if (recipes.isEmpty()) return;

        var elements = e.getTooltipElements();

        // 🔎 Index NACH XP-Zeile finden
        int insertAt = elements.size();
        for (int i = 0; i < elements.size(); i++) {
            var el = elements.get(i);
            if (el.left().isPresent()) {
                String text = el.left().get().getString();
                if (text.equals("\u0000ROOST_OUTPUT_MARKER")) {
                    insertAt = i + 1;
                    elements.remove(i); // Marker entfernen
                    break;
                }
            }
        }

        /*if (insertAt == -1) {
            insertAt = elements.size();
        }*/

        // Leerzeile für Abstand
        //elements.add(insertAt++, Either.left(Component.empty()));

        // 🔥 Output-Item einfügen
        ItemStack out = recipes.get(0).getResultItem(null).copy();
        out.setCount(1);

        elements.add(insertAt, Either.right(new StackLineTooltip(out)));

        // Optional: Abstand danach
        //elements.add(insertAt, Either.left(Component.empty()));
    }

}