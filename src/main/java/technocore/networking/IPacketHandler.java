package technocore.networking;

import technocore.networking.packets.PacketTechno;

public interface IPacketHandler {

	public abstract void handlePacket(PacketTechno packet);
}