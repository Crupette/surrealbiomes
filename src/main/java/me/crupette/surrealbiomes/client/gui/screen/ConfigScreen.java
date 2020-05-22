package me.crupette.surrealbiomes.client.gui.screen;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Iterator;

public class ConfigScreen extends Screen{
    private final Screen parent;
    private Text explanation;

    public ConfigScreen(Screen parent) {
        super(new TranslatableText("screen.surrealbiomes.config"));
        this.parent = parent;
    }

    protected void init() {
        this.explanation = new TranslatableText("text.surrealbiomes.config_explain");
        this.textRenderer.getClass();
        int var10003 = this.width / 2 - 100;
        int var10004 = this.height / 2;
        this.textRenderer.getClass();
        this.addButton(new ButtonWidget(var10003, Math.min(var10004 + 9, this.height - 30), 200, 20, new TranslatableText("Ok"), (buttonWidget) -> {
            this.client.openScreen(this.parent);
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        TextRenderer var10002 = this.textRenderer;
        Text var10003 = this.title;
        int var10004 = this.width / 2;
        int var10005 = this.height / 2;
        this.textRenderer.getClass();
        this.drawCenteredText(matrices, var10002, var10003, var10004, var10005 - 9 * 2, 11184810);
        int i = this.height / 2;

        this.drawCenteredText(matrices, this.textRenderer, this.explanation, this.width / 2, i, 0xFFFFFF);
        this.textRenderer.getClass();

        super.render(matrices, mouseX, mouseY, delta);
    }
}
