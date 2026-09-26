package com.justtools;

import com.justtools.init.*;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
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
        ModArmorMaterials.ARMOR_MATERIALS.register(modEventBus);

        LOGGER.info("JustTools initialized!");
    }
}
