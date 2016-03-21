package essenceMod.handlers.compatibility.nei;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GuiNEI extends GuiContainer
{
	public GuiNEI(Container p_i1072_1_)
	{
		super(p_i1072_1_);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		drawTexturedModalRect(0, 0, 0, 0, xSize, ySize);
	}
}