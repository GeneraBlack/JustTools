package com.justtools.init;

import com.justtools.JustTools;
import com.justtools.menu.HardeningMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, JustTools.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<HardeningMenu>> HARDENING_MENU = MENUS.register(
            "hardening_station",
            () -> new MenuType<>(HardeningMenu::new, FeatureFlagSet.of())
    );
}
