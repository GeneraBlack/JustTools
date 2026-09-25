package com.justtools.client;

import com.justtools.init.ModMenuTypes;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class JustToolsClient {
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.HARDENING_MENU.get(), HardeningScreen::new);
    }
}
