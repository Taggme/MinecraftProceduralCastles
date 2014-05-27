package castle.gen.generator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GenCastleRoomLibrary extends CastleChunk {

	public GenCastleRoomLibrary(World world_, Random rand_, int XCoord_,
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

		switch(direct)
		{
		case CastleGen.RoomNorth:
		case CastleGen.RoomSouth:
			for(int x = 1; x < CastleGen.RoomWidth - 1; x++)
				for(int y = 0; y < CastleGen.RoomHeight - 1; y++)
					world.setBlock(XCoord + x, YCoord + y, ZCoord + 1, Blocks.bookshelf);
			for(int x = 1; x < CastleGen.RoomWidth - 1; x++)
				for(int y = 0; y < CastleGen.RoomHeight - 1; y++)
					world.setBlock(XCoord + x, YCoord + y, ZCoord + CastleGen.RoomWidth - 2, Blocks.bookshelf);
			break;
		case CastleGen.RoomEast:
		case CastleGen.RoomWest:
			for(int x = 1; x < CastleGen.RoomWidth - 1; x++)
				for(int y = 0; y < CastleGen.RoomHeight - 1; y++)
					world.setBlock(XCoord + 1, YCoord + y, ZCoord + x, Blocks.bookshelf);
			for(int x = 1; x < CastleGen.RoomWidth - 1; x++)
				for(int y = 0; y < CastleGen.RoomHeight - 1; y++)
					world.setBlock(XCoord + CastleGen.RoomWidth - 2, YCoord + y, ZCoord + x, Blocks.bookshelf);
			break;
		}
		
    	CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, (direct + 2) % 4);		
    	if(CastleGen.GenCastleCanGen(world, rand, XCoord, YCoord, ZCoord, direct, spawn + 1, floor))
		{
    		CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, direct);
        	CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, direct);		
			CastleGen.chunks[floor].addLast(new GenCastleRoomSplitter(world, rand, XCoord, YCoord + CastleGen.RoomHeight + 1, ZCoord, direct, spawn + 1, floor + 1));
		}
	}
    public void Window(){
    	return;
    }
}
