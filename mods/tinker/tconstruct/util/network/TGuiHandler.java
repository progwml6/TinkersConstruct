package mods.tinker.tconstruct.util.network;

import mods.tinker.tconstruct.TConstruct;
import mods.tinker.tconstruct.blocks.logic.FrypanLogic;
import mods.tinker.tconstruct.blocks.logic.PartCrafterLogic;
import mods.tinker.tconstruct.blocks.logic.PatternChestLogic;
import mods.tinker.tconstruct.blocks.logic.PatternShaperLogic;
import mods.tinker.tconstruct.blocks.logic.SmelteryLogic;
import mods.tinker.tconstruct.blocks.logic.ToolStationLogic;
import mods.tinker.tconstruct.client.TProxyClient;
import mods.tinker.tconstruct.client.gui.ArmorExtendedGui;
import mods.tinker.tconstruct.client.gui.FrypanGui;
import mods.tinker.tconstruct.client.gui.GuiManual;
import mods.tinker.tconstruct.client.gui.PartCrafterGui;
import mods.tinker.tconstruct.client.gui.PatternChestGui;
import mods.tinker.tconstruct.client.gui.PatternShaperGui;
import mods.tinker.tconstruct.client.gui.SmelteryGui;
import mods.tinker.tconstruct.client.gui.ToolStationGui;
import mods.tinker.tconstruct.inventory.ArmorExtendedContainer;
import mods.tinker.tconstruct.library.InventoryLogic;
import mods.tinker.tconstruct.util.player.TPlayerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class TGuiHandler implements IGuiHandler
{
    public static int stationID = 0;
    public static int partID = 1;
    public static int pchestID = 2;
    public static int pshaperID = 3;
    public static int frypanID = 4;

    public static int smeltery = 7;
    public static int armor = 101;
    public static int manualGui = -1;

    @Override
    public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID < 0)
            return null;

        else if (ID <= 100)
        {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null && tile instanceof InventoryLogic)
            {
                return ((InventoryLogic) tile).getGuiContainer(player.inventory, world, x, y, z);
            }
        }
        else
        {
            if (ID == armor)
            {
                TPlayerStats stats = TConstruct.playerTracker.getPlayerStats(player.username);
                System.out.println("Server Armor Gui: "+stats.armor);
                return new ArmorExtendedContainer(player.inventory, stats.armor);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == stationID)
            return new ToolStationGui(player.inventory, (ToolStationLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
        if (ID == partID)
            return new PartCrafterGui(player.inventory, (PartCrafterLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
        if (ID == pchestID)
            return new PatternChestGui(player.inventory, (PatternChestLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
        if (ID == frypanID)
            return new FrypanGui(player.inventory, (FrypanLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
        if (ID == smeltery)
            return new SmelteryGui(player.inventory, (SmelteryLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
        if (ID == pshaperID)
            return new PatternShaperGui(player.inventory, (PatternShaperLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
        if (ID == manualGui)
        {
            ItemStack stack = player.getCurrentEquippedItem();
            return new GuiManual(stack, TProxyClient.getManualFromStack(stack));
        }
        if (ID == armor)
        {
            System.out.println("Client Armor Gui: "+TProxyClient.armorExtended);
            //TPlayerStats stats = TConstruct.playerTracker.getPlayerStats(player.username);
            TProxyClient.armorExtended.init(Minecraft.getMinecraft().thePlayer);
            return new ArmorExtendedGui(player.inventory, TProxyClient.armorExtended);
        }
        return null;
    }

}