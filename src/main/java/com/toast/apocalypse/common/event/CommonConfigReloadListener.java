package com.toast.apocalypse.common.event;

import com.toast.apocalypse.common.core.Apocalypse;
import com.toast.apocalypse.common.core.difficulty.WorldDifficultyManager;
import com.toast.apocalypse.common.core.config.ApocalypseCommonConfig;
import com.toast.apocalypse.common.misc.DestroyerExplosionContext;
import com.toast.apocalypse.common.util.RainDamageTickHelper;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Apocalypse.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfigReloadListener {

    @SubscribeEvent
    public static void onLoad(ModConfig.Loading event) {
        if (event.getConfig().getType() == ModConfig.Type.COMMON) {
            updateInfo();
        }
    }

    @SubscribeEvent
    public static void onReload(ModConfig.Reloading event) {
        if (event.getConfig().getType() == ModConfig.Type.COMMON) {
            updateInfo();
        }
    }

    /**
     * Updates static references when needed to
     * avoid accessing the config constantly
     * in the server tick loop and whatnot.
     */
    public static void updateInfo() {
        WorldDifficultyManager.MULTIPLAYER_DIFFICULTY_SCALING = ApocalypseCommonConfig.COMMON.multiplayerDifficultyScaling();
        WorldDifficultyManager.DIFFICULTY_MULTIPLIER = ApocalypseCommonConfig.COMMON.getDifficultyRateMultiplier();
        WorldDifficultyManager.SLEEP_PENALTY = ApocalypseCommonConfig.COMMON.getSleepPenalty();

        List<RegistryKey<World>> list = new ArrayList<>();
        ApocalypseCommonConfig.COMMON.getDifficultyPenaltyDimensions().forEach((s -> {
            list.add(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(s)));
        }));
        WorldDifficultyManager.DIMENSION_PENALTY_LIST = list;

        WorldDifficultyManager.DIMENSION_PENALTY = ApocalypseCommonConfig.COMMON.getDimensionPenalty();

        RainDamageTickHelper.RAIN_TICK_RATE = ApocalypseCommonConfig.COMMON.getRainTickRate();
        RainDamageTickHelper.RAIN_DAMAGE = ApocalypseCommonConfig.COMMON.getRainDamage();

        // Clear before refreshing entries
        DestroyerExplosionContext.DESTROYER_PROOF_BLOCKS.clear();

        for (String blockName : ApocalypseCommonConfig.COMMON.getDestroyerProofBlocks()) {
            if (ResourceLocation.isValidResourceLocation(blockName)) {
                ResourceLocation blockLocation = new ResourceLocation(blockName);

                if (ForgeRegistries.BLOCKS.containsKey(blockLocation)) {
                    DestroyerExplosionContext.DESTROYER_PROOF_BLOCKS.add(ForgeRegistries.BLOCKS.getValue(blockLocation));
                }
            }
            else {
                Apocalypse.LOGGER.warn("Invalid block registry name found in the destroyer proof block list. Check your Apocalypse common config! Problematic ResourceLocation: " + "\"" + blockName + "\"");
            }
        }
    }
}
