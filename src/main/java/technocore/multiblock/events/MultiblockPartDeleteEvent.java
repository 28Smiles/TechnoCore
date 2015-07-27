package technocore.multiblock.events;

import technocore.multiblock.IMultiblock;
import net.minecraft.world.World;

public class MultiblockPartDeleteEvent extends MultiblockPartEvent {

	public MultiblockPartDeleteEvent(World world, IMultiblock multiBlockTile) {
		super(world, multiBlockTile);
	}

}
