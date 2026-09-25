package com.justtools.init;

import com.justtools.JustTools;
import com.justtools.block.HardeningStationBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(JustTools.MODID);

    public static final DeferredBlock<HardeningStationBlock> HARDENING_STATION = BLOCKS.register(
            "hardening_station",
            () -> new HardeningStationBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(3.5F, 6.0F)
                    .sound(SoundType.NETHERITE_BLOCK)
                    .requiresCorrectToolForDrops())
    );
}
