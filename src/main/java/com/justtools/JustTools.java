package com.justtools;

import com.justtools.client.JustToolsClient;
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
        ModMenuTypes.MENUS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(JustToolsClient::onRegisterScreens);
        }

        LOGGER.info("JustTools initialized!");
    }
}
