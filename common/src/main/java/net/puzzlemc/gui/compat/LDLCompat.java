package net.puzzlemc.gui.compat;

import dev.lambdaurora.lambdynlights.DynamicLightsConfig;
import dev.lambdaurora.lambdynlights.LambDynLights;
import net.minecraft.text.Text;
import net.puzzlemc.gui.PuzzleApi;
import net.puzzlemc.gui.screen.widget.PuzzleWidget;

import static net.puzzlemc.gui.PuzzleGui.NO;
import static net.puzzlemc.gui.PuzzleGui.YES;

public class LDLCompat {
    public static void init() {
        DynamicLightsConfig ldlConfig = LambDynLights.get().config;

        PuzzleApi.addToGraphicsOptions(new PuzzleWidget(Text.of("LambDynamicLights")));
        PuzzleApi.addToGraphicsOptions(new PuzzleWidget(Text.translatable("lambdynlights.option.mode"), (button) -> button.setMessage(ldlConfig.getDynamicLightsMode().getTranslatedText()), (button) -> ldlConfig.setDynamicLightsMode(ldlConfig.getDynamicLightsMode().next())));
        PuzzleApi.addToGraphicsOptions(new PuzzleWidget(Text.empty().append("DynLights: ").append(Text.translatable("lambdynlights.option.light_sources.entities")), (button) -> button.setMessage(ldlConfig.getEntitiesLightSource().get() ? YES : NO), (button) -> ldlConfig.getEntitiesLightSource().set(!ldlConfig.getEntitiesLightSource().get())));
        PuzzleApi.addToGraphicsOptions(new PuzzleWidget(Text.empty().append("DynLights: ").append(Text.translatable("entity.minecraft.creeper")), (button) -> button.setMessage(ldlConfig.getCreeperLightingMode().getTranslatedText()), (button) -> ldlConfig.setCreeperLightingMode(ldlConfig.getCreeperLightingMode().next())));
        PuzzleApi.addToGraphicsOptions(new PuzzleWidget(Text.empty().append("DynLights: ").append(Text.translatable("block.minecraft.tnt")), (button) -> button.setMessage(ldlConfig.getTntLightingMode().getTranslatedText()), (button) -> ldlConfig.setTntLightingMode(ldlConfig.getTntLightingMode().next())));
        PuzzleApi.addToGraphicsOptions(new PuzzleWidget(Text.empty().append("DynLights: ").append(Text.translatable("lambdynlights.option.light_sources.water_sensitive_check")), (button) -> button.setMessage(ldlConfig.getWaterSensitiveCheck().get() ? YES : NO), (button) -> ldlConfig.getWaterSensitiveCheck().set(!ldlConfig.getWaterSensitiveCheck().get())));
    }
}
