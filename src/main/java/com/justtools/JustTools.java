package com.justtools;

import com.justtools.init.*;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(JustTools.MODID)
public class JustTools {
    public static final String MODID = "justtools";
    public static final Logger LOGGER = LogUtils.getLogger();

    public JustTools(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModDataComponents.DATA_COMPONENTS.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            // Use double-lambda to prevent JVM from loading client classes on server
            modEventBus.addListener(net.neoforged.neoforge.client.event.RegisterMenuScreensEvent.class,
                    event -> com.justtools.client.JustToolsClient.onRegisterScreens(event));
        }

        LOGGER.info("JustTools initialized!");
    }
}
