package technocore.multiblock.events;

import technocore.multiblock.IMultiblockPart;
import net.minecraft.world.World;

public class MultiblockPartEvent extends net.minecraftforge.fml.common.eventhandler.Event{
	
	public final IMultiblockPart multiBlockTile;
	public final World world;
	
	public MultiblockPartEvent(World world, IMultiblockPart multiBlockTile) {
		super();
		this.world = world;
		this.multiBlockTile = multiBlockTile;
	}

}
