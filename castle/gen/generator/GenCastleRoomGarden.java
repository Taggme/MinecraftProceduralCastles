package castle.gen.generator;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class GenCastleRoomGarden  extends CastleChunk {
	public static final WorldGenTrees trees = new WorldGenTrees(false);
	public static final WorldGenBigTree treesbig = new WorldGenBigTree(false);
	
	public GenCastleRoomGarden(World world_, Random rand_, int XCoord_,
			int YCoord_, int ZCoord_, int direct_, int spawn_, int floor_) {
		super(world_, rand_, XCoord_, YCoord_, ZCoord_, direct_, spawn_, floor_);
	}	
	public void Generate(){
    	switch(direct)
		{
		case CastleGen.RoomNorth:
			XCoord -= CastleGen.RoomWidth;
			break;
		case CastleGen.RoomEast:
			ZCoord -= CastleGen.RoomWidth;
			break;
		case CastleGen.RoomSouth:
			XCoord += CastleGen.RoomWidth;
			break;
		case CastleGen.RoomWest:
			ZCoord += CastleGen.RoomWidth;
			break;
		}

		for(int tries = 0; tries < 60; tries++)
		{
	    	int dirout = rand.nextInt() % 4;
			if(CastleGen.GenCastleCanGen(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor))
			{
				CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, dirout);
				CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    		int rooms = rand.nextInt() % 100;

	    		if(rooms < 70){
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomSplitter(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 85){
	    			if(floor < CastleGen.CastleHeight)
	    			{
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomLadder(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    			}
	    			else
	    			{
	    				CastleGen.chunks[floor].addLast(new GenCastleRoomTreasure(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    			}
	    		}
	    		else{
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomTreasure(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		return;
			}
		}
	}

    public void Window(){    	
    	if(rand.nextInt(4) == 0){
    		treesbig.generate(world, rand, XCoord + 3 + rand.nextInt(CastleGen.RoomWidth - 6), YCoord, ZCoord + 3 + rand.nextInt(CastleGen.RoomWidth - 6));
    	}
    	else{
    		trees.generate(world, rand, XCoord + 3 + rand.nextInt(CastleGen.RoomWidth - 6), YCoord, ZCoord + 3 + rand.nextInt(CastleGen.RoomWidth - 6));
    	}
    	CastleGen.GenCastleRails(world, rand, XCoord, YCoord, ZCoord);
    }

}
