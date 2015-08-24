package test;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLInitializationEvent event)
	{
		FluidRegistry.registerFluid(TestMod.TestFluid.instance);
        GameRegistry.registerBlock(LoveBlock.instance, LoveBlock.name);
	}
}
