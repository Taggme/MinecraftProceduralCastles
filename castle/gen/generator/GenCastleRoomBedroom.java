package castle.gen.generator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GenCastleRoomBedroom extends CastleChunk{
	public GenCastleRoomBedroom(World world_, Random rand_, int XCoord_,
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

    	int XCoord2 = XCoord;
    	int ZCoord2 = ZCoord;
    	int XCoord3 = XCoord;
    	int ZCoord3 = ZCoord;
		switch(direct)
		{
		case CastleGen.RoomNorth:
			XCoord2 +=  3;
			ZCoord2 += CastleGen.RoomWidth / 2 + 1;
			XCoord3 +=  2;
			ZCoord3 += CastleGen.RoomWidth / 2 + 1;
			break;
		case CastleGen.RoomEast:
			ZCoord2 += 3;
			XCoord2 += CastleGen.RoomWidth / 2 + 1;
			ZCoord3 += 2;
			XCoord3 += CastleGen.RoomWidth / 2 + 1;
			break;
		case CastleGen.RoomSouth:
			XCoord2 += CastleGen.RoomWidth - 4;
			ZCoord2 += CastleGen.RoomWidth / 2 + 1;
			XCoord3 += CastleGen.RoomWidth - 3;
			ZCoord3 += CastleGen.RoomWidth / 2 + 1;
			break;
		case CastleGen.RoomWest:
			ZCoord2 += CastleGen.RoomWidth - 4;
			XCoord2 += CastleGen.RoomWidth / 2 + 1;
			ZCoord3 += CastleGen.RoomWidth - 3;
			XCoord3 += CastleGen.RoomWidth / 2 + 1;
			break;
		}
    	//CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, -1);
    	CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, (direct + 2) % 4);
    	//CastleGen.GenCastleWindows(world, rand, XCoord, YCoord, ZCoord);

    	for(int airx = 2; airx < CastleGen.RoomWidth - 2; airx++) {
        	for(int airz = 2; airz < CastleGen.RoomWidth - 2; airz++) {
    			world.setBlock(XCoord + airx, YCoord - 1, ZCoord + airz, Blocks.wool, 14, 0);
        	}
    	}
    	
		world.setBlock(XCoord2, YCoord, ZCoord2, Blocks.bed, (direct + 1 % 4), 0);
		world.setBlock(XCoord3, YCoord, ZCoord3, Blocks.bed, (direct + 1 % 4) + 8, 0);
	}
    public void Window(){
    	CastleGen.GenCastleStainWindowsR(world, rand, XCoord, YCoord, ZCoord);
    }
}
