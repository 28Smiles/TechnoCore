package technocore;

import org.apache.logging.log4j.Logger;

import technocore.client.gui.GuiHandler;
import technocore.event.EventHandler;
import technocore.mechanic.fluid.pipe.TileFluidPipe;
import technocore.multiblock.MultiBlockRegistry;
import technocore.multiblock.events.MultiBlockEventHandler;
import technocore.networking.PacketHandler;
import technocore.networking.packets.PacketTileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = TechnoCore.MODID, name = TechnoCore.MODNAME, version = TechnoCore.VERSION)
public class TechnoCore {

	@Mod.Instance(value = TechnoCore.MODID)
	public static TechnoCore instance;

	public static final String MODID = "technocore";
	public static final String MODNAME = "Techno Core";
	public static final String VERSION = "v0.1";

	public static Logger log;

	@net.minecraftforge.fml.common.Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		log = event.getModLog();
		PacketHandler.instance.initialize();
		FMLCommonHandler.instance().bus().register(EventHandler.instance);
		FMLCommonHandler.instance().bus().register(MultiBlockEventHandler.instance);
		FMLCommonHandler.instance().bus().register(MultiBlockRegistry.INSTANCE);
		MinecraftForge.EVENT_BUS.register(MultiBlockRegistry.INSTANCE);
		FMLCommonHandler.instance().bus().register(TileFluidPipe.Swapper.INSTANCE);
		PacketHandler.instance.registerPacket(PacketTileEntity.class);
	}
	
	@net.minecraftforge.fml.common.Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		PacketHandler.instance.postInit();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, GuiHandler.instance);
	}
}
