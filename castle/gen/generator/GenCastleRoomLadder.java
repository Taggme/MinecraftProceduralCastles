package castle.gen.generator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GenCastleRoomLadder extends CastleChunk  {

	public GenCastleRoomLadder(World world_, Random rand_, int XCoord_,
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

		int XCoord2 = XCoord;
		int ZCoord2 = ZCoord;
		int meta = 1;
		switch(direct)
		{
		case CastleGen.RoomNorth:
			meta = 5;
			XCoord2 +=  1;
			ZCoord2 += CastleGen.RoomWidth / 2;
			break;
		case CastleGen.RoomEast:
			meta = 3;
			ZCoord2 += 1;
			XCoord2 += CastleGen.RoomWidth / 2;
			break;
		case CastleGen.RoomSouth:
			meta = 4;
			XCoord2 += CastleGen.RoomWidth - 2;
			ZCoord2 += CastleGen.RoomWidth / 2;
			break;
		case  CastleGen.RoomWest:
			meta = 2;
			ZCoord2 += CastleGen.RoomWidth - 2;
			XCoord2 += CastleGen.RoomWidth / 2;
			break;
		}

		if(CastleGen.GenCastleCanGen(world, rand, XCoord, YCoord + CastleGen.RoomHeight + 1, ZCoord, -1, spawn + 1, floor + 1))
		{
			CastleGen.GenCastleRoom(world, rand, XCoord, YCoord + CastleGen.RoomHeight + 1, ZCoord, -1);
			
			for(int splits = 0; splits < 6; splits++)
			{
				int dir_split = rand.nextInt(4);
				if(direct == dir_split)
					continue;
				
				if(CastleGen.GenCastleCanGen(world, rand, XCoord, YCoord + CastleGen.RoomHeight + 1, ZCoord, dir_split, spawn + 1, floor + 1))
				{
					CastleGen.GenCastleDoor(world, rand, XCoord, YCoord + CastleGen.RoomHeight + 1, ZCoord, dir_split);
					CastleGen.GenCastleRoom(world, rand, XCoord, YCoord + CastleGen.RoomHeight + 1, ZCoord, dir_split);
					CastleGen.chunks[floor + 1].addLast(new GenCastleRoomSplitter(world, rand, XCoord, YCoord + CastleGen.RoomHeight + 1, ZCoord, dir_split, spawn + 1, floor + 1));
					break;
				}
			}
			for(int laddery = 0; laddery < CastleGen.RoomHeight + 2; laddery++){
				world.setBlock(XCoord2, YCoord + laddery, ZCoord2, Blocks.ladder, meta, 2);
			}
		}			
	}

    public void Window(){
    	CastleGen.GenCastleWindows(world, rand, XCoord, YCoord + CastleGen.RoomHeight + 1, ZCoord);
    }
}
