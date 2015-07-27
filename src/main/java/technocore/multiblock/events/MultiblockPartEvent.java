package technocore.multiblock.events;

import technocore.multiblock.IMultiblock;
import net.minecraft.world.World;

public class MultiblockPartEvent extends net.minecraftforge.fml.common.eventhandler.Event{
	
	public final IMultiblock multiBlockTile;
	public final World world;
	
	public MultiblockPartEvent(World world, IMultiblock multiBlockTile) {
		super();
		this.world = world;
		this.multiBlockTile = multiBlockTile;
	}

}
