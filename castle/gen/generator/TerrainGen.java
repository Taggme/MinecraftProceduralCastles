package castle.gen.generator;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class TerrainGen implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
		case -1:
		    generateNether(world, random, chunkX * 16, chunkZ * 16);
		    break;
		case 0:
		    generateSurface(world, random, chunkX * 16, chunkZ * 16);
		    break;
		case 1:
		    generateEnd(world, random, chunkX * 16, chunkZ * 16);
		    break;
		}
	}
	private void generateEnd(World world, Random random, int i, int j) {
		
	}

	private void generateSurface(World world, Random rand, int chunkX, int chunkZ) {     

		//Generate Castles
		if(rand.nextInt() % 50 == 0)
		{
			CastleGen.GenCastleEnterence(world, rand, chunkX, chunkZ);
		}
	}

	private void generateNether(World world, Random random, int i, int j) {}
}
