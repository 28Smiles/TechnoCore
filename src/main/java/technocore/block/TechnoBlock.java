package technocore.block;

import java.awt.Dimension;
import java.awt.Point;

import technocore.block.tileentity.TechnoCoreTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

//TODO: a lot
public class TechnoBlock extends Block {

	protected TechnoBlock(Material material) {
		super(material);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos bp, IBlockState bs, EntityPlayer player, EnumFacing facing, float f1, float f2, float f3) {
		if(world.getTileEntity(bp) != null)
			return ((TechnoCoreTileEntity)world.getTileEntity(bp)).openGui(world, bp, bs, player, facing, f1, f2, f3);
		return false;
	}
}
