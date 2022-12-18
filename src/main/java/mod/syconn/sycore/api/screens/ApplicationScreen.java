package mod.syconn.sycore.api.screens;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.syconn.sycore.api.blockEntity.ApplicationBE;
import mod.syconn.sycore.api.containers.ApplicationContainer;
import mod.syconn.sycore.api.util.applications.BasicApplication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;

public class ApplicationScreen extends BasicContainerScreen<ApplicationContainer> {

    private final BasicApplication<ApplicationContainer> application;
    private final Minecraft mc = Minecraft.getInstance();

    public ApplicationScreen(ApplicationContainer container, Inventory pPlayerInventory, Component pTitle) {
        super(container, pPlayerInventory, pTitle);
        this.application = ((ApplicationBE) container.getBlockEntity()).getApplication();
    }

    @Override
    protected void init() {
        super.init();
        application.init(this, width, height);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        application.tick();
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        application.render(pPoseStack, pMouseX, pMouseY, pPartialTick, width, height);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        application.renderBG(pPoseStack, pPartialTick, pMouseY, pMouseX, width, height);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        application.renderLabels(pPoseStack, title.getString(), titleLabelX, titleLabelY);
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        //super.resize(pMinecraft, pWidth, pHeight);
        application.resize(this, minecraft, pWidth, pHeight);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        application.keyPressed(pKeyCode, pScanCode, pModifiers);
        InputConstants.Key mouseKey = InputConstants.getKey(pKeyCode, pScanCode);
        if (pKeyCode == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        } else if (pKeyCode == 258) {
            boolean flag = !hasShiftDown();
            if (!this.changeFocus(flag)) {
                this.changeFocus(flag);
            }

            return false;
        } else {
            boolean handled = this.checkHotbarKeyPressed(pKeyCode, pScanCode);// Forge MC-146650: Needs to return true when the key is handled
            if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
                if (this.minecraft.options.keyPickItem.isActiveAndMatches(mouseKey)) {
                    this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, 0, ClickType.CLONE);
                    handled = true;
                } else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
                    this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
                    handled = true;
                }
            } else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
                handled = true; // Forge MC-146650: Emulate MC bug, so we don't drop from hotbar when pressing drop without hovering over a item.
            }

            return handled;
        }
    }

    public <T extends GuiEventListener & Widget & NarratableEntry> void createWidget(T widget){
        addRenderableWidget(widget);
    }
}
