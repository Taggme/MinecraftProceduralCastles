package castle.gen.generator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GenCastleRoomBridge extends CastleChunk{

	public GenCastleRoomBridge(World world_, Random rand_, int XCoord_,
			int YCoord_, int ZCoord_, int direct_, int spawn_, int floor_) {
		super(world_, rand_, XCoord_, YCoord_, ZCoord_, direct_, spawn_, floor_);
	}	
	public void Generate(){
		int XCoordo = XCoord;
		int ZCoordo = ZCoord;
    	int Xcoord2 = XCoord;
    	int Zcoord2 = ZCoord;
    	switch(direct)
		{
		case CastleGen.RoomNorth:
			Xcoord2 -= CastleGen.RoomWidth / 2;
			XCoord -= CastleGen.RoomWidth;
			break;
		case CastleGen.RoomEast:
			Zcoord2 -= CastleGen.RoomWidth / 2;
			ZCoord -= CastleGen.RoomWidth;
			break;
		case CastleGen.RoomSouth:
			Xcoord2 += CastleGen.RoomWidth / 2;
			XCoord += CastleGen.RoomWidth;
			break;
		case CastleGen.RoomWest:
			Zcoord2 += CastleGen.RoomWidth / 2;
			ZCoord += CastleGen.RoomWidth;
			break;
		}
		if(CastleGen.GenCastleCanGenNeedAir(world, rand, XCoord, YCoord, ZCoord, 0) &&
				CastleGen.GenCastleCanGen(world, rand, XCoord, YCoord, ZCoord, direct, spawn + 1, floor))
		{
			CastleGen.GenCastleDoor(world, rand, XCoordo, YCoord, ZCoordo, direct);
			
			switch(direct)
			{
			case CastleGen.RoomNorth:
			case CastleGen.RoomSouth:
		    	for(int airx = 0; airx < CastleGen.RoomWidth; airx++) {
		        	for(int airz = 2; airz < CastleGen.RoomWidth - 2; airz++) {
				    	for(int airy = 0; airy < 2; airy++) {
				    		if(airy == 0 || airz == 2 || airz == CastleGen.RoomWidth - 3)
				    			world.setBlock(XCoord + airx, YCoord - 1 + airy, ZCoord + airz, Blocks.stonebrick);
				    	}
		        	}
		    	}
				break;
			case CastleGen.RoomWest:
			case CastleGen.RoomEast:
		    	for(int airx = 2; airx < CastleGen.RoomWidth - 2; airx++) {
		        	for(int airz = 0; airz < CastleGen.RoomWidth; airz++) {
				    	for(int airy = 0; airy < 2; airy++) {
				    		if(airy == 0 || airx == 2 || airx == CastleGen.RoomWidth - 3)
				    			world.setBlock(XCoord + airx, YCoord - 1 + airy, ZCoord + airz, Blocks.stonebrick);
				    	}
		        	}
		    	}
				break;
			}

			CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, direct);
			CastleGen.chunks[floor].addLast(new GenCastleRoomSplitter(world, rand, XCoord, YCoord, ZCoord, direct, spawn + 1, floor));
		}
	}
    public void Window(){
    	return;
    }

}
