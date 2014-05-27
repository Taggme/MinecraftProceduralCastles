package castle.gen;

import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import castle.gen.lib.ProxyCommon;
import castle.gen.lib.References;
import castle.gen.generator.CastleGen;
import castle.gen.generator.TerrainGen;

@Mod(modid = References.MODID, version = References.VERSION)
public class CastleGenMain
{    
	@SidedProxy(clientSide = References.Client, serverSide = References.Common)
	
	public static ProxyCommon proxy;
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
    	proxy.registerRenderInformation();
    }
    
    public CastleGenMain() {
    	CastleGen.GenCastleInit();
    	//LangReg
    	GameRegistry.registerWorldGenerator(new TerrainGen(), 5);
    	
    }
}
