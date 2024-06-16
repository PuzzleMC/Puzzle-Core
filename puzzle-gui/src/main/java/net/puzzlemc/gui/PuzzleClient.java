package net.puzzlemc.gui;

import eu.midnightdust.core.MidnightLib;
import eu.midnightdust.lib.util.PlatformFunctions;
import net.minecraft.util.Identifier;
import net.puzzlemc.core.config.PuzzleConfig;
import net.puzzlemc.gui.compat.*;
import net.puzzlemc.gui.screen.widget.PuzzleWidget;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.puzzlemc.splashscreen.PuzzleSplashScreen;

public class PuzzleClient implements ClientModInitializer {

    public final static String id = "puzzle";
    public static final Text YES = Text.translatable("gui.yes").formatted(Formatting.GREEN);
    public static final Text NO = Text.translatable("gui.no").formatted(Formatting.RED);
    public static final Identifier PUZZLE_BUTTON = Identifier.of(id, "icon/button");

    @Override
    public void onInitializeClient() {
        MidnightLib.hiddenMods.add("puzzle");
        MinecraftClient client = MinecraftClient.getInstance();
        PuzzleApi.addToMiscOptions(new PuzzleWidget(Text.of("Puzzle")));
        PuzzleApi.addToMiscOptions(new PuzzleWidget(Text.translatable("puzzle.option.check_for_updates"), (button) -> button.setMessage(PuzzleConfig.checkUpdates ? YES : NO), (button) -> {
            PuzzleConfig.checkUpdates = !PuzzleConfig.checkUpdates;
            PuzzleConfig.write(id);
        }));
        PuzzleApi.addToMiscOptions(new PuzzleWidget(Text.translatable("puzzle.option.show_version_info"), (button) -> button.setMessage(PuzzleConfig.showPuzzleInfo ? YES : NO), (button) -> {
            PuzzleConfig.showPuzzleInfo = !PuzzleConfig.showPuzzleInfo;
            PuzzleConfig.write(id);
        }));
        PuzzleApi.addToMiscOptions(new PuzzleWidget(Text.translatable("puzzle.midnightconfig.title"), (button) -> button.setMessage(Text.of("OPEN")), (button) -> {
            client.setScreen(PuzzleConfig.getScreen(client.currentScreen, "puzzle"));
        }));
        PuzzleApi.addToResourceOptions(new PuzzleWidget(Text.of("Puzzle")));
        if (isActive("puzzle-splashscreen")) {
            PuzzleApi.addToResourceOptions(new PuzzleWidget(Text.translatable("puzzle.option.resourcepack_splash_screen"), (button) -> button.setMessage(PuzzleConfig.resourcepackSplashScreen ? YES : NO), (button) -> {
                PuzzleConfig.resourcepackSplashScreen = !PuzzleConfig.resourcepackSplashScreen;
                PuzzleConfig.write(id);
                PuzzleSplashScreen.resetColors();
                MinecraftClient.getInstance().getTextureManager().registerTexture(PuzzleSplashScreen.LOGO, new PuzzleSplashScreen.LogoTexture(PuzzleSplashScreen.LOGO));
            }));
        }
        if (isActive("puzzle-models")) {
            PuzzleApi.addToResourceOptions(new PuzzleWidget(Text.translatable("puzzle.option.unlimited_model_rotations"), (button) -> button.setMessage(PuzzleConfig.unlimitedRotations ? YES : NO), (button) -> {
                PuzzleConfig.unlimitedRotations = !PuzzleConfig.unlimitedRotations;
                PuzzleConfig.write(id);
            }));
            PuzzleApi.addToResourceOptions(new PuzzleWidget(Text.translatable("puzzle.option.bigger_custom_models"), (button) -> button.setMessage(PuzzleConfig.biggerModels ? YES : NO), (button) -> {
                PuzzleConfig.biggerModels = !PuzzleConfig.biggerModels;
                PuzzleConfig.write(id);
            }));
        }
        if (isActive("cullleaves")) CullLeavesCompat.init();
        if (isActive("colormatic")) ColormaticCompat.init();
        if (isActive("borderlessmining")) BorderlessMiningCompat.init();
        if (isActive("iris")) IrisCompat.init();
    }
    public static boolean lateInitDone = false;
    public static void lateInit() { // Some mods are initialized after Puzzle, so we can't access them in our ClientModInitializer
        if (isActive("lambdynlights")) LDLCompat.init();
        if (isActive("citresewn")) CITRCompat.init();
        if (isActive("lambdabettergrass")) LBGCompat.init();
        if (isActive("continuity")) ContinuityCompat.init();
        if (isActive("entity_texture_features")) ETFCompat.init();
        if (isActive("entity_model_features")) EMFCompat.init();
        lateInitDone = true;
    }
    public static boolean isActive(String modid) {
        return PlatformFunctions.isModLoaded(modid) && !PuzzleConfig.disabledIntegrations.contains(modid);
    }
}
