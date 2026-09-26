package com.justtools.client;

import com.justtools.JustTools;
import com.justtools.init.ModItems;
import com.justtools.init.ModMenuTypes;
import com.justtools.item.ModBowItem;
import com.justtools.item.ModCrossbowItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = JustTools.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class JustToolsClient {
    @SubscribeEvent
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.HARDENING_MENU.get(), HardeningScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            registerBowProperties(ModItems.HARDENED_BOW.get());
            registerBowProperties(ModItems.COPPER_BOW.get());
            registerBowProperties(ModItems.HARDENED_COPPER_BOW.get());
            registerBowProperties(ModItems.DEEPSLATE_BOW.get());
            registerBowProperties(ModItems.HARDENED_DEEPSLATE_BOW.get());
            registerBowProperties(ModItems.IRON_BOW.get());
            registerBowProperties(ModItems.HARDENED_IRON_BOW.get());
            registerBowProperties(ModItems.DIAMOND_BOW.get());
            registerBowProperties(ModItems.HARDENED_DIAMOND_BOW.get());
            registerBowProperties(ModItems.NETHERITE_BOW.get());
            registerBowProperties(ModItems.HARDENED_NETHERITE_BOW.get());

            registerCrossbowProperties(ModItems.HARDENED_CROSSBOW.get());
            registerCrossbowProperties(ModItems.COPPER_CROSSBOW.get());
            registerCrossbowProperties(ModItems.HARDENED_COPPER_CROSSBOW.get());
            registerCrossbowProperties(ModItems.DEEPSLATE_CROSSBOW.get());
            registerCrossbowProperties(ModItems.HARDENED_DEEPSLATE_CROSSBOW.get());
            registerCrossbowProperties(ModItems.IRON_CROSSBOW.get());
            registerCrossbowProperties(ModItems.HARDENED_IRON_CROSSBOW.get());
            registerCrossbowProperties(ModItems.DIAMOND_CROSSBOW.get());
            registerCrossbowProperties(ModItems.HARDENED_DIAMOND_CROSSBOW.get());
            registerCrossbowProperties(ModItems.NETHERITE_CROSSBOW.get());
            registerCrossbowProperties(ModItems.HARDENED_NETHERITE_CROSSBOW.get());
        });
    }

    private static void registerBowProperties(Item bow) {
        ItemProperties.register(bow, ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else if (entity.getUseItem() != stack) {
                return 0.0F;
            } else {
                float drawTime = bow instanceof ModBowItem modBow ? modBow.getDrawTime(stack) : 20.0F;
                return (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / drawTime;
            }
        });
        ItemProperties.register(bow, ResourceLocation.withDefaultNamespace("pulling"), (stack, level, entity, seed) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }

    private static void registerCrossbowProperties(Item crossbow) {
        ItemProperties.register(crossbow, ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else if (CrossbowItem.isCharged(stack)) {
                return 0.0F;
            } else {
                int duration = crossbow instanceof ModCrossbowItem modCrossbow ? modCrossbow.getCustomChargeDuration(stack, entity) : CrossbowItem.getChargeDuration(stack, entity);
                return (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / (float) duration;
            }
        });
        ItemProperties.register(crossbow, ResourceLocation.withDefaultNamespace("pulling"), (stack, level, entity, seed) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
        });
        ItemProperties.register(crossbow, ResourceLocation.withDefaultNamespace("charged"), (stack, level, entity, seed) -> {
            return CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
        });
        ItemProperties.register(crossbow, ResourceLocation.withDefaultNamespace("firework"), (stack, level, entity, seed) -> {
            ChargedProjectiles chargedprojectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
            return chargedprojectiles != null && chargedprojectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }
}
