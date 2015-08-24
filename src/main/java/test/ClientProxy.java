package test;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

	private static ModelResourceLocation fluidLocation = new ModelResourceLocation("test" + ":" + LoveBlock.name, "fluid");
	
	@Override
	public void preInit(FMLInitializationEvent event) {
		super.preInit(event);
		Item fluid = Item.getItemFromBlock(LoveBlock.instance);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(fluid, 0, new ModelResourceLocation("test:fluid", "inventory"));
		/*
		ModelBakery.addVariantName(fluid);
        
        ModelLoader.setCustomMeshDefinition(fluid, new ItemMeshDefinition()
        {
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                return fluidLocation;
            }
        });
        ModelLoader.setCustomStateMapper(LoveBlock.instance, new StateMapperBase()
        {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return fluidLocation;
            }
        });*/
	}
}
