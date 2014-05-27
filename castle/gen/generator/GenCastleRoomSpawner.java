package castle.gen.generator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class GenCastleRoomSpawner extends CastleChunk{

	public GenCastleRoomSpawner(World world_, Random rand_, int XCoord_,
			int YCoord_, int ZCoord_, int direct_, int spawn_, int floor_) {
		super(world_, rand_, XCoord_, YCoord_, ZCoord_, direct_, spawn_, floor_);
	}
	public void Generate(){    	switch(direct)
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

		TileEntityMobSpawner mobs = new TileEntityMobSpawner();
		int spw = rand.nextInt(3);
		if(spw == 0)
		{
			mobs.func_145881_a().setEntityName("Spider");
		}
		else if(spw == 1)
		{
			mobs.func_145881_a().setEntityName("Blaze");
		}
		else if(spw == 2)
		{
			mobs.func_145881_a().setEntityName("Skeleton");
		}
		world.setBlock(XCoord + CastleGen.RoomWidth / 2, YCoord, ZCoord + CastleGen.RoomWidth / 2, Blocks.mob_spawner);
		world.setTileEntity(XCoord + CastleGen.RoomWidth / 2, YCoord, ZCoord + CastleGen.RoomWidth / 2, mobs);
	
	   	CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, (direct + 2) % 4);

		for(int tries = 0; tries < 70; tries++)
		{
	    	int dirout = rand.nextInt() % 4;
			if(CastleGen.GenCastleCanGen(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor))
			{
	    		CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, dirout);
	    		int rooms = rand.nextInt() % 100;
	    		if(rooms < 5){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomLibrary(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		if(rooms < 20){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomConnector(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 30){
	    			if(CastleGen.GenCastleCanGenNeedAir(world, rand, XCoord, YCoord, ZCoord, dirout)){
		    			CastleGen.GenCastleGarden(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomGarden(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    			}
	    			else{
						CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomConnector(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    			}
	    		}
	    		else if(rooms < 60){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomSplitter(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 80){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
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
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomTreasure(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
			}
		}
		
	}

    public void Window(){
    	CastleGen.GenCastleWindows(world, rand, XCoord, YCoord, ZCoord);
    }

}
