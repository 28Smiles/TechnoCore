package test;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "test", dependencies = "required-after:technocore")
public class TestMod {

	@SidedProxy(serverSide = "test.CommonProxy", clientSide = "test.ClientProxy")
    public static CommonProxy proxy;
	
	@net.minecraftforge.fml.common.Mod.EventHandler
	public void preInit(FMLInitializationEvent event)
	{
		B3DLoader.instance.addDomain("test");
		GameRegistry.registerTileEntity(TestTile.class, "testtile");
		TestBlock test;
		test = (TestBlock) GameRegistry.registerBlock(new TestBlock(), "test");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(test), 0, new ModelResourceLocation("test:test", "inventory"));
		
		proxy.preInit(event);
	}

	public static final class TestFluid extends Fluid
	{
	    public static final String name = "love";
	    public static final TestFluid instance = new TestFluid();
	    
	    private TestFluid()
	    {
	        super(name, new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/water_flow"));
	    }
	
	    @Override
	    public int getColor()
	    {
	        return 0xFF00FF00;
	    }
	}
}
