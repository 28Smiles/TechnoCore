package technocore.multiblock.events;

import technocore.multiblock.IMultiblock;
import net.minecraft.world.World;

public class MultiblockPartPlacedEvent extends MultiblockPartEvent {

	public MultiblockPartPlacedEvent(World world, IMultiblock multiBlockTile) {
		super(world, multiBlockTile);
	}

}
