package net.puzzlemc.gui.mixin;

import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.lib.util.PlatformFunctions;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.puzzlemc.core.config.PuzzleConfig;
import net.puzzlemc.gui.PuzzleGui;
import net.puzzlemc.gui.screen.PuzzleOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(OptionsScreen.class)
public abstract class MixinOptionsScreen extends Screen {
    @Shadow @Final private ThreePartsLayoutWidget layout;
    @Unique TextIconButtonWidget puzzle$button = TextIconButtonWidget.builder(Text.translatable("puzzle.screen.title"), (buttonWidget) ->
            (Objects.requireNonNull(this.client)).setScreen(new PuzzleOptionsScreen(this)), true)
            .dimension(20, 20).texture(PuzzleGui.PUZZLE_BUTTON, 20, 20).build();

    private MixinOptionsScreen(Text title) {super(title);}

    @Inject(at = @At("HEAD"), method = "init")
    public void puzzle$onInit(CallbackInfo ci) {
        if (PuzzleConfig.enablePuzzleButton) {
            this.puzzle$setButtonPos();
            this.addDrawableChild(puzzle$button);
        }
    }

    @Inject(at = @At("TAIL"), method = "initTabNavigation")
    public void puzzle$onResize(CallbackInfo ci) {
        if (PuzzleConfig.enablePuzzleButton) this.puzzle$setButtonPos();
    }

    @Unique
    public void puzzle$setButtonPos() {
        int i = 0;
        if (PlatformFunctions.isModLoaded("lod")) i = i + 358;
        if (MidnightLibConfig.config_screen_list.equals(MidnightLibConfig.ConfigButton.FALSE)) i = i - 25;
        puzzle$button.setPosition(this.width / 2 - 178 + i, layout.getY() + layout.getFooterHeight() - 4);
    }
}
