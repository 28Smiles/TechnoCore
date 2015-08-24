package test;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class LoveBlock extends BlockFluidClassic {

	public static final LoveBlock instance = new LoveBlock(TestMod.TestFluid.instance);
	public static final String name = "blocklove";
	
	public LoveBlock(Fluid fluid) {
		super(fluid, Material.water);
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.tabBlock);
	}
}
