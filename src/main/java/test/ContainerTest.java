package test;

import net.minecraft.entity.player.InventoryPlayer;
import technocore.block.container.TechnoCoreContainer;
import technocore.block.tileentity.TechnoCoreTileEntity;

public class ContainerTest extends TechnoCoreContainer {

	public ContainerTest(TechnoCoreTileEntity technoTile, InventoryPlayer inventory) {
		super(technoTile, inventory);
	}

}
