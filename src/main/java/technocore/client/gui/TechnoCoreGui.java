package technocore.client.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import technocore.TechnoCore;
import technocore.block.container.TechnoCoreContainer;
import technocore.block.tileentity.TechnoCoreTileEntity;
import technocore.client.gui.elements.Element;
import technocore.gui.slot.GuiSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class TechnoCoreGui extends GuiContainer {

	protected List<Element> elements = new ArrayList<Element>();
	protected TechnoCoreTileEntity tile;
	protected IInventory player;
	protected ResourceLocation texture = new ResourceLocation(TechnoCore.MODID, "textures/gui/normal.png");

	public TechnoCoreGui(TechnoCoreTileEntity tile, IInventory player) {
		super(tile.getContainer((InventoryPlayer)player));
		this.tile = tile;
		this.player = player;
	}

	public static Dimension displaySize() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        return new Dimension(res.getScaledWidth(), res.getScaledHeight());
    }

    public static Dimension displayRes() {
        Minecraft mc = Minecraft.getMinecraft();
        return new Dimension(mc.displayWidth, mc.displayHeight);
    }
    
    public Point getTopLeftCorner() {
    	return new Point((width - xSize) / 2, (height - ySize) / 2);
    }

    public static Point getMousePosition(int eventX, int eventY) {
        Dimension size = displaySize();
        Dimension res = displayRes();
        return new Point(eventX * size.width / res.width, size.height - eventY * size.height / res.height - 1);
    }

    public static Point getMousePosition() {
        return getMousePosition(Mouse.getX(), Mouse.getY());
    }
    
    protected void renderToolTip(List tooltip, int p_renderToolTip_2_, int p_renderToolTip_3_)
    {
        drawHoveringText(tooltip, p_renderToolTip_2_, p_renderToolTip_3_);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0, int arg1, int arg2) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.mc.getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		TechnoCoreContainer container = (TechnoCoreContainer)inventorySlots;
		for(GuiSlot s : container.getSlots())
			s.draw(this, arg1, arg2);

		for(Element e : elements)
			e.draw(this, arg1, arg2);
	}
	
	public void drawSizedModalRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
	{
		    int i;
		    if (paramInt1 < paramInt3) {
		        i = paramInt1;
		        paramInt1 = paramInt3;
		        paramInt3 = i;
		    }
		    if (paramInt2 < paramInt4) {
		        i = paramInt2;
		        paramInt2 = paramInt4;
		        paramInt4 = i;
		    }
	
		    float f1 = (paramInt5 >> 24 & 0xFF) / 255.0F;
		    float f2 = (paramInt5 >> 16 & 0xFF) / 255.0F;
		    float f3 = (paramInt5 >> 8 & 0xFF) / 255.0F;
		    float f4 = (paramInt5 & 0xFF) / 255.0F;
		    Tessellator localTessellator = Tessellator.getInstance();
		    GL11.glEnable(3042);
		    GL11.glDisable(3553);
		    GL11.glBlendFunc(770, 771);
		    GL11.glColor4f(f2, f3, f4, f1);
		    localTessellator.getWorldRenderer().startDrawingQuads();
		    localTessellator.getWorldRenderer().addVertex(paramInt1, paramInt4, this.zLevel);
		    localTessellator.getWorldRenderer().addVertex(paramInt3, paramInt4, this.zLevel);
		    localTessellator.getWorldRenderer().addVertex(paramInt3, paramInt2, this.zLevel);
		    localTessellator.getWorldRenderer().addVertex(paramInt1, paramInt2, this.zLevel);
		    localTessellator.draw();
		    GL11.glEnable(3553);
		    GL11.glDisable(3042);
	  }

	  public void drawSizedRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
	  {
		    int i;
		    if (paramInt1 < paramInt3) {
		        i = paramInt1;
		        paramInt1 = paramInt3;
		        paramInt3 = i;
		    }
		    if (paramInt2 < paramInt4) {
		        i = paramInt2;
		        paramInt2 = paramInt4;
		        paramInt4 = i;
		    }
	
		    float f1 = (paramInt5 >> 24 & 0xFF) / 255.0F;
		    float f2 = (paramInt5 >> 16 & 0xFF) / 255.0F;
		    float f3 = (paramInt5 >> 8 & 0xFF) / 255.0F;
		    float f4 = (paramInt5 & 0xFF) / 255.0F;
		    Tessellator localTessellator = Tessellator.getInstance();
		    GL11.glDisable(3553);
		    GL11.glColor4f(f2, f3, f4, f1);
		    localTessellator.getWorldRenderer().startDrawingQuads();
		    localTessellator.getWorldRenderer().addVertex(paramInt1, paramInt4, this.zLevel);
		    localTessellator.getWorldRenderer().addVertex(paramInt3, paramInt4, this.zLevel);
		    localTessellator.getWorldRenderer().addVertex(paramInt3, paramInt2, this.zLevel);
		    localTessellator.getWorldRenderer().addVertex(paramInt1, paramInt2, this.zLevel);
		    localTessellator.draw();
		    GL11.glEnable(3553);
	  }

	  public void drawSizedTexturedModalRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat1, float paramFloat2)
	  {
		    float f1 = 1.0F / paramFloat1;
		    float f2 = 1.0F / paramFloat2;
		    Tessellator localTessellator = Tessellator.getInstance();
		    localTessellator.getWorldRenderer().startDrawingQuads();
		    localTessellator.getWorldRenderer().addVertexWithUV(paramInt1 + 0, paramInt2 + paramInt6, this.zLevel, (paramInt3 + 0) * f1, (paramInt4 + paramInt6) * f2);
		    localTessellator.getWorldRenderer().addVertexWithUV(paramInt1 + paramInt5, paramInt2 + paramInt6, this.zLevel, (paramInt3 + paramInt5) * f1, (paramInt4 + paramInt6) * f2);
		    localTessellator.getWorldRenderer().addVertexWithUV(paramInt1 + paramInt5, paramInt2 + 0, this.zLevel, (paramInt3 + paramInt5) * f1, (paramInt4 + 0) * f2);
		    localTessellator.getWorldRenderer().addVertexWithUV(paramInt1 + 0, paramInt2 + 0, this.zLevel, (paramInt3 + 0) * f1, (paramInt4 + 0) * f2);
		    localTessellator.draw();
	  }
}