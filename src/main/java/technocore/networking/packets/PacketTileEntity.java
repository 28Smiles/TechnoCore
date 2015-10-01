package technocore.networking.packets;

import java.util.Random;

import technocore.block.tileentity.TechnoCoreTileEntity;
import technocore.networking.IPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PacketTileEntity extends PacketTechno {

	public PacketTileEntity(TechnoCoreTileEntity tile) {
		addCoords(tile.getPos());
	}

	public PacketTileEntity(BlockPos pos) {
		addCoords(pos);
	}

	public PacketTileEntity() {}

	@Override
	public void handlePacket(EntityPlayer paramEntityPlayer, boolean isServer) {
		if(isServer)
			return;
		((IPacketHandler)Minecraft.getMinecraft().theWorld.getTileEntity(getBlockCoords())).handlePacket(this);
	}

	@Override
	public String getCanonicalName() {
		return "tile";
	}
}
