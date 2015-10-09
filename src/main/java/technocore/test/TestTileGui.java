package technocore.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import technocore.block.TechnoBlock;
import technocore.block.container.TechnoCoreContainer;
import technocore.block.tileentity.TechnoCoreTileEntity;
import technocore.client.gui.TechnoCoreGui;
import technocore.client.gui.elements.ScaledElement;
import technocore.client.gui.elements.ScaledElement.ScaleType;
import technocore.client.gui.elements.Widget;
import technocore.gui.slot.GuiSlot;
import technocore.networking.packets.PacketTechno;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TestTileGui {

	public static void init() {
		TestBlock tb = new TestBlock();
		GameRegistry.registerBlock(tb, tb.getUnlocalizedName());
		GameRegistry.registerTileEntity(TileGuiTest.class, "tile.guitest");
	}

	public static class TestBlock extends TechnoBlock implements ITileEntityProvider {

		public TestBlock() {
			super(Material.iron);
			setUnlocalizedName("test.block.gui");
		}

		@Override
		public TileEntity createNewTileEntity(World worldIn, int meta) {
			return new TileGuiTest();
		}

	}

	public static class TileGuiTest extends TechnoCoreTileEntity {

		@Override
		public void handleGuiPacket(PacketTechno packet) {
			
		}

		@Override
		public void handleDescriptionPacket(PacketTechno packet) {
			
		}

		@Override
		public Gui getGui(IInventory player) {
			List l = new ArrayList<String>();
			l.add("huhu");
			List li = new ArrayList<String>();
			li.add("money");
			List lis = new ArrayList<String>();
			lis.add("luluul");
			lis.add("Ich kauf den Laden hier!");
			return new TechnoCoreGui(this, player).addElement(new ScaledElement(40, 20).setScaleType(ScaleType.VerticalRight).scale(10).setTexture("technocore", "textures/gui/elements/arrow_right.png"))
					.addWidget(new Widget(new Point(176, 0), l, new ResourceLocation("technocore", "textures/gui/widget/normal.png")))
					.addWidget(new Widget(new Point(176, 24), li, new ResourceLocation("technocore", "textures/gui/widget/normal.png")))
					.addWidget(new Widget(new Point(176, 48), lis, new ResourceLocation("technocore", "textures/gui/widget/normal.png"))
						.addElement(new ScaledElement(20, 10).setScaleType(ScaleType.VerticalRight).scale(10).setTexture("technocore", "textures/gui/elements/arrow_right.png"))
						.addElement(new ScaledElement(40, 120).setScaleType(ScaleType.VerticalRight).scale(20).setTexture("technocore", "textures/gui/elements/arrow_right.png")));
		}

		@Override
		public Container getContainer(InventoryPlayer player) {
			return new TechnoCoreContainer(this, player).addSlot(new GuiSlot(20, 20, 1, 0, player)).addSlot(new GuiSlot(20, 40, 2, 0, player)).addSlot(new GuiSlot(20, 60, 3, 0, player));
		}
		
	}
}
