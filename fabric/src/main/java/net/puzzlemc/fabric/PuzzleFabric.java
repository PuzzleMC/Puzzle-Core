package net.puzzlemc.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.puzzlemc.core.PuzzleCore;
import net.puzzlemc.splashscreen.PuzzleSplashScreen;

public class PuzzleFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PuzzleCore.initModules();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return Identifier.of("puzzle", "splash_screen");
            }
            @Override
            public void reload(ResourceManager manager) {
                PuzzleSplashScreen.ReloadListener.INSTANCE.reload(manager);
            }
        });
    }
}
