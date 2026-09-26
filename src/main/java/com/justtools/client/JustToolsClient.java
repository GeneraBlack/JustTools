package com.justtools.client;

import com.justtools.JustTools;
import com.justtools.init.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = JustTools.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class JustToolsClient {
    @SubscribeEvent
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.HARDENING_MENU.get(), HardeningScreen::new);
    }
}
