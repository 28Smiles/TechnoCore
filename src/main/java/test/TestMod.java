package test;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = "test", dependencies = "required-after:technocore")
public class TestMod {

	@net.minecraftforge.fml.common.Mod.EventHandler
	public void preInit(FMLInitializationEvent event)
	{
		GameRegistry.registerTileEntity(TestTile.class, "testtile");
		TestBlock test;
		test = (TestBlock) GameRegistry.registerBlock(new TestBlock(), "test");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(test), 0, new ModelResourceLocation("tutorial:tutorial_block", "inventory"));
	}

}
