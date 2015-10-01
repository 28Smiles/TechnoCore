package technocore.networking.packets;

import technocore.multiblock.IMultiblockPart;
import technocore.networking.IPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class PacketMultiblock extends PacketTechno {

	public PacketMultiblock(BlockPos partPos) {
		addCoords(partPos);
	}

	@Override
	public void handlePacket(EntityPlayer paramEntityPlayer, boolean isServer) {
		if(isServer)
			return;
		((IMultiblockPart)Minecraft.getMinecraft().theWorld.getTileEntity(getBlockCoords())).getMultiblockTile().handlePacket(this);
	}

	@Override
	public String getCanonicalName() {
		return "multi";
	}

}
