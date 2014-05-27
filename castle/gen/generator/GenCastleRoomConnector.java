package castle.gen.generator;

import java.util.Random;

import net.minecraft.world.World;

public class GenCastleRoomConnector extends CastleChunk {

	private boolean rebuild;
	public GenCastleRoomConnector(World world_, Random rand_, int XCoord_,
			int YCoord_, int ZCoord_, int direct_, int spawn_, int floor_) {
		super(world_, rand_, XCoord_, YCoord_, ZCoord_, direct_, spawn_, floor_);
		rebuild = false;
	}
	public void Generate(){
		int XCoordo = XCoord;
		int ZCoordo = ZCoord;
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

		for(int tries = 0; tries < 60; tries++)
		{
	    	int dirout = rand.nextInt() % 4;
			if(CastleGen.GenCastleCanGen(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor))
			{
				CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, dirout);
	    		int rooms = rand.nextInt() % 100;

	    		if(rooms < 10){
	    			if(CastleGen.GenCastleCanGenNeedAir(world, rand, XCoord, YCoord, ZCoord, dirout)){
		    			CastleGen.GenCastleGarden(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomGarden(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    			}
	    			else
	    			{
						CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomLadder(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    			}
	    		}
	    		else if(rooms < 20){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomSpawner(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 25){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomBedroom(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 30){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomLibrary(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 70){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomSplitter(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 75){
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
	    			CastleGen.chunks[floor].addLast(new GenCastleRoomChurch(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
	    		}
	    		else if(rooms < 80){
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
	    		return;
			}
		}
	}

    public void Window(){
    	if(rebuild == false)
    		CastleGen.GenCastleWindows(world, rand, XCoord, YCoord, ZCoord);
    }
}
