package technocore.mechanic.fluid.pipe;

import org.lwjgl.opengl.GL11;

import technocore.TechnoCore;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;

public class TileEntitiyPipeRenderer extends TileEntitySpecialRenderer {

	private static final ResourceLocation TEXTURE = new ResourceLocation(TechnoCore.MODID, "textures/blocks/pipe.png");

	/**
	 * render the tile entity - called every frame while the tileentity is in view of the player
	 * @param tileEntity the associated tile entity
	 * @param relativeX the x distance from the player's eye to the tileentity
	 * @param relativeY the y distance from the player's eye to the tileentity
	 * @param relativeZ the z distance from the player's eye to the tileentity
	 * @param partialTicks the fraction of a tick that this frame is being rendered at - used to interpolate frames between
	 *                     ticks, to make animations smoother.  For example - if the frame rate is steady at 80 frames per second,
	 *                     this method will be called four times per tick, with partialTicks spaced 0.25 apart, (eg) 0, 0.25, 0.5, 0.75
	 * @param blockDamageProgress the progress of the block being damaged (0 - 10), if relevant.  -1 if not relevant.
	 */
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress)
	{
		if (!(tileEntity instanceof TileFluidPipe)) return; // should never happen
		TileFluidPipe tileEntityFluidPipe = (TileFluidPipe) tileEntity;

		int pipeHeight = tileEntityFluidPipe.getPipeSize();
		final float blockCenter = 0.5F;

		final EnumFacing[] connections = tileEntityFluidPipe.getConnectedSides();
		try {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

			Tessellator tesselator = Tessellator.getInstance();
			WorldRenderer worldRenderer = tesselator.getWorldRenderer();

			GlStateManager.translate(relativeX, relativeY, relativeZ);

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthMask(true);

			GlStateManager.color(1, 1, 1);

			this.bindTexture(TEXTURE);
			worldRenderer.startDrawing(GL11.GL_QUADS);

			for(EnumFacing face : EnumFacing.values())
			{
				boolean found = false;
				for(EnumFacing connection : connections)
					if(connection.equals(face))
						found = true;

				if(found)
				{
					
				}
				else
				{
					if(face.equals(EnumFacing.EAST))
					{
						worldRenderer.addVertexWithUV(1, 0, 0, 1, 1);
						worldRenderer.addVertexWithUV(1, 1, 0, 1, 0);
						worldRenderer.addVertexWithUV(1, 0, 0, 0.5F, 0);
						worldRenderer.addVertexWithUV(1, 0, 0, 0.5F, 1);
					}
				}
			}

			tesselator.draw();
		} finally {
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}

}
