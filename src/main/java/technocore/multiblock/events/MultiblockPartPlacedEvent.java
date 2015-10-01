package technocore.multiblock.events;

import technocore.multiblock.IMultiblockPart;
import net.minecraft.world.World;

public class MultiblockPartPlacedEvent extends MultiblockPartEvent {

	public MultiblockPartPlacedEvent(World world, IMultiblockPart multiBlockTile) {
		super(world, multiBlockTile);
	}

}
