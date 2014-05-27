package castle.gen.generator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GenCastleRoomSplitter extends CastleChunk{

	public GenCastleRoomSplitter(World world_, Random rand_, int XCoord_,
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
	   	CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, (direct + 2) % 4);

		for(int tries = 0; tries < 70; tries++)
		{
	    	int dirout = rand.nextInt() % 4;
			if(CastleGen.GenCastleCanGen(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor))
			{
	    		CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, dirout);
	    		int rooms = rand.nextInt() % 100;
	    		if(rooms < 10){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomConnector(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 15){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomChurch(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 20){
	    			if(CastleGen.GenCastleCanGenNeedAir(world, rand, XCoord, YCoord, ZCoord, dirout)){
		    			CastleGen.GenCastleGarden(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomGarden(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    			}
	    			else{
						CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomConnector(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    			}
	    		}
	    		else if(rooms < 25){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomSpawner(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 30){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomBedroom(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 60){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomSplitter(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 90){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			if(floor < CastleGen.CastleHeight)
	    			{
		    			CastleGen.chunks[floor + 1].addLast(new GenCastleRoomLadder(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
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
			else 
			{
				int Xcoord2 = XCoord;
				int Zcoord2 = ZCoord;
				int Xcoord3 = XCoord;
				int Zcoord3 = ZCoord;
		    	switch(dirout)
				{
				case CastleGen.RoomNorth:
					Xcoord2 -= CastleGen.RoomWidth;
					Xcoord3 -= CastleGen.RoomWidth * 2;
					break;
				case CastleGen.RoomEast:
					Zcoord2 -= CastleGen.RoomWidth ;
					Zcoord3 -= CastleGen.RoomWidth * 2;
					break;
				case CastleGen.RoomSouth:
					Xcoord2 += CastleGen.RoomWidth;
					Xcoord3 += CastleGen.RoomWidth * 2;
					break;
				case CastleGen.RoomWest:
					Zcoord2 += CastleGen.RoomWidth;
					Zcoord3 += CastleGen.RoomWidth * 2;
					break;
				}
				if(CastleGen.GenCastleCanGen(world, rand, Xcoord3, YCoord, Zcoord3, dirout, spawn + 1, 0) &&
						world.getBlock(Xcoord2, YCoord - 2, Zcoord2) != Blocks.stonebrick && floor > 0)
				{
					if(rand.nextInt(3) == 0)
					{
						CastleGen.chunks[floor].addLast(new GenCastleRoomBridge(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
					}
				}
			}
		}
	}
    public void Window(){
    	return;
    }
}
