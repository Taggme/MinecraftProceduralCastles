package castle.gen.generator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class GenCastleRoomTreasure extends CastleChunk {

	public GenCastleRoomTreasure(World world_, Random rand_, int XCoord_,
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

    	//GenCastleRoom(world, rand, XCoord, YCoord, ZCoord);
    	CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, (direct + 2) % 4);
    	//CastleGen.GenCastleWindows(world, rand, XCoord, YCoord, ZCoord);

		if(spawn > 5)
		{
	    	int XCoord2 = XCoord;
	    	int ZCoord2 = ZCoord;
			switch(direct)
			{
			case CastleGen.RoomNorth:
				XCoord2 +=  1;
				ZCoord2 += CastleGen.RoomWidth / 2;
				break;
			case CastleGen.RoomEast:
				ZCoord2 += 1;
				XCoord2 += CastleGen.RoomWidth / 2;
				break;
			case CastleGen.RoomSouth:
				XCoord2 += CastleGen.RoomWidth - 2;
				ZCoord2 += CastleGen.RoomWidth / 2;
				break;
			case CastleGen.RoomWest:
				ZCoord2 += CastleGen.RoomWidth - 2;
				XCoord2 += CastleGen.RoomWidth / 2;
				break;
			}
			TileEntityChest chest = new TileEntityChest();
			world.setBlock(XCoord2, YCoord, ZCoord2, Blocks.chest);
			world.setTileEntity(XCoord2, YCoord, ZCoord2, chest);
			if(rand.nextInt() % 8 == 0)
				chest.setInventorySlotContents(0, new ItemStack(Items.apple, rand.nextInt(10) + 8));
			if(rand.nextInt() % 12 == 0)
				chest.setInventorySlotContents(1, new ItemStack(Items.record_stal, 1));
			if(rand.nextInt() % 6 == 0)
				chest.setInventorySlotContents(3, new ItemStack(Items.bed, 1));
			if(rand.nextInt() % 7 == 0)
				chest.setInventorySlotContents(5, new ItemStack(Items.iron_sword, 1));
			if(rand.nextInt() % 8 == 0)
				chest.setInventorySlotContents(7, new ItemStack(Blocks.bookshelf, rand.nextInt(8) + 4));
			if(rand.nextInt() % 18 == 0)
				chest.setInventorySlotContents(12, new ItemStack(Items.diamond, rand.nextInt(3) + 1));
			if(rand.nextInt() % 24 == 0)
				chest.setInventorySlotContents(13, new ItemStack(Items.emerald, rand.nextInt(2) + 1));
			if(rand.nextInt() % 12 == 0)
				chest.setInventorySlotContents(14, new ItemStack(Items.gold_ingot, rand.nextInt(4) + 6));
			if(rand.nextInt() % 8 == 0)
				chest.setInventorySlotContents(15, new ItemStack(Items.bed, 1));
			if(rand.nextInt() % 12 == 0)
				chest.setInventorySlotContents(16, new ItemStack(Blocks.glass, 12));
			if(rand.nextInt() % 3 == 0)
				chest.setInventorySlotContents(17, new ItemStack(Items.cooked_beef, rand.nextInt(6) + 4));
			if(rand.nextInt() % 14 == 0)	
				chest.setInventorySlotContents(19, new ItemStack(Items.map, 1));
			if(rand.nextInt() % 2 == 0)
				chest.setInventorySlotContents(20, new ItemStack(Items.coal, rand.nextInt(20) + 12));
			else
				chest.setInventorySlotContents(22, new ItemStack(Items.iron_ingot, rand.nextInt(10) + 4));
		}
		else{
			for(int tries = 0; tries < 70; tries++)
			{
		    	int dirout = rand.nextInt() % 4;
				if(CastleGen.GenCastleCanGen(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor))
				{
		    		CastleGen.GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, dirout);
		    		int rooms = rand.nextInt() % 100;
		    		if(rooms < 20){
						CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomConnector(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
		    		}
		    		else if(rooms < 25){
		    			if(CastleGen.GenCastleCanGenNeedAir(world, rand, XCoord, YCoord, ZCoord, dirout)){
			    			CastleGen.GenCastleGarden(world, rand, XCoord, YCoord, ZCoord, dirout);
			    			CastleGen.chunks[floor].addLast(new GenCastleRoomGarden(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
		    			}
		    			else{
							CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
			    			CastleGen.chunks[floor].addLast(new GenCastleRoomConnector(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
		    			}
		    		}
		    		else if(rooms < 30){
						CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomSpawner(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
		    		}
		    		else if(rooms < 60){
						CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomSplitter(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
		    		}
		    		else if(rooms < 80){
						CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomLadder(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
		    		}
		    		else{
						CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dirout);
		    			CastleGen.chunks[floor].addLast(new GenCastleRoomTreasure(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor));
		    		}
				}
			}
		}
	}
    public void Window(){
    	CastleGen.GenCastleWindows(world, rand, XCoord, YCoord, ZCoord);
    }
}
