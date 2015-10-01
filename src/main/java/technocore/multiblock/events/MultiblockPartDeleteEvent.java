package technocore.multiblock.events;

import technocore.multiblock.IMultiblockPart;
import net.minecraft.world.World;

public class MultiblockPartDeleteEvent extends MultiblockPartEvent {

	public MultiblockPartDeleteEvent(World world, IMultiblockPart multiBlockTile) {
		super(world, multiBlockTile);
	}

}
