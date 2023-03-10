package mod.syconn.sycore.api.screens.componet;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.syconn.sycore.api.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.gui.widget.Slider;

public class ColorSlider extends Slider {

    private final String value;
    private final int id;

    public ColorSlider(int id, int x, int y, String prefix, double min, double max, double current, OnPress press)
    {
        super(x, y, new TranslatableComponent(prefix), min, max, current, press, null);
        this.value = prefix;
        this.id = id;
        updateSlider();
    }

    @Override
    protected void renderBg(PoseStack mStack, Minecraft par1Minecraft, int par2, int par3) {
        switch (id){
            case 0 -> GuiHelper.fillRect(this.x, this.y, width, height, getValueInt(), 0, 0, 255);
            case 1 -> GuiHelper.fillRect(this.x, this.y, width, height, 0, getValueInt(), 0, 255);
            case 2 -> GuiHelper.fillRect(this.x, this.y, width, height, 0, 0, getValueInt(), 255);
        }

        super.renderBg(mStack, par1Minecraft, par2, par3);
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.onRelease(mouseX, mouseY);
        //onPress.onPress(this);
    }

    public ColorSlider hide(){
        visible = false;
        return this;
    }

    @Override
    public void updateSlider() {
        super.updateSlider();
        onPress.onPress(this);
        setMessage(new TextComponent(getValueInt() + " " + value));
    }

    public int getId() {
        return id;
    }
}
