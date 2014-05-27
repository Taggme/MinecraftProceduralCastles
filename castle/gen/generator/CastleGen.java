package castle.gen.generator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class CastleGen {	
	
	public class Ladder_Cord
	{
	    public int XCoord;
	    public int YCoord;
	    public int ZCoord;
	    public int Dir;
	    public int Spawn;
	    public int Floor;
	}
	
	public static final int RoomWidth = 8;
	public static final int RoomHeight = 5;
	public static final int RoomNorth = 0;
	public static final int RoomEast = 1;
	public static final int RoomSouth = 2;
	public static final int RoomWest = 3;
	public static final int SpawnOut = 40;
	public static final int CastleDepth = 7;
	public static final int CastleHeight = 7;
	private static final Ladder_Cord Ladder_Cord = null;
	public static int CastleMinX;
	public static int CastleMinZ;
	public static int CastleMaxX;
	public static int CastleMaxZ;
	public static Deque<CastleChunk> chunks[] = new ArrayDeque[CastleHeight ];
	public static Deque<CastleChunk> finalize = new ArrayDeque();

	public static void GenCastleInit(){
    	for(int floors = 0; floors < CastleHeight; floors++)
    	{
    		chunks[floors] = new ArrayDeque();
    	}
	}
	public static void GenCastleEnterence(World world, Random rand,int chunkX, int chunkZ)
	{
    	for(int floors = 0; floors < CastleHeight; floors++)
    	{
    		chunks[floors].clear();
    	}
		finalize.clear();
		
    	int XCoord = chunkX + (rand.nextInt() % 2) * 8;
    	int YCoord = 185;
    	int ZCoord = chunkZ + (rand.nextInt() % 2) * 8;
        while (world.isAirBlock(XCoord, YCoord - 1, ZCoord) && YCoord > 2)
        {
            --YCoord;
        }
        Block ground = world.getBlock(XCoord, YCoord - 1, ZCoord);
        if(ground == Blocks.dirt || ground == Blocks.grass)
        {
        	int dir = rand.nextInt(4);
        	int XCoord2 = XCoord;
        	int ZCoord2 = ZCoord;
        	switch(dir)
    		{
    		case RoomNorth:
    			CastleMaxX = XCoord - RoomWidth;
    			CastleMinX = XCoord - RoomWidth * (CastleDepth + 1);
    			CastleMinZ = ZCoord - RoomWidth * CastleDepth / 2;
    			CastleMaxZ = ZCoord + RoomWidth * CastleDepth / 2;
    			break;
    		case RoomEast:
    			CastleMaxZ = ZCoord - RoomWidth;
    			CastleMinZ = ZCoord - RoomWidth * (CastleDepth + 1);
    			CastleMinX = XCoord - RoomWidth * CastleDepth / 2;
    			CastleMaxX = XCoord + RoomWidth * CastleDepth / 2;
    			break;
    		case RoomSouth:
    			CastleMaxX = XCoord + RoomWidth * (CastleDepth + 1);
    			CastleMinX = XCoord + RoomWidth;
    			CastleMinZ = ZCoord - RoomWidth * CastleDepth / 2;
    			CastleMaxZ = ZCoord + RoomWidth * CastleDepth / 2;
    			break;
    		case RoomWest:
    			CastleMaxZ = ZCoord + RoomWidth * (CastleDepth + 1);
    			CastleMinZ = ZCoord + RoomWidth;
    			CastleMinX = XCoord - RoomWidth * CastleDepth / 2;
    			CastleMaxX = XCoord + RoomWidth * CastleDepth / 2;
    			break;
    		}
        	GenCastleFloor(world, rand, XCoord2, YCoord, ZCoord2);
        	CastleGen.GenCastleRoom(world, rand, XCoord, YCoord, ZCoord, dir);
        	chunks[0].addLast(new GenCastleRoomSplitter(world, rand, XCoord, YCoord, ZCoord, dir, 0, 0));
        	for(int floors = 0; floors < CastleHeight; floors++)
        	{
	        	while(!chunks[floors].isEmpty())
	        	{
	        		chunks[floors].getFirst().Generate();
	        		finalize.addLast(chunks[floors].getFirst());
	        		chunks[floors].removeFirst();
	        	}
        	}
        	while(!finalize.isEmpty())
        	{
        		finalize.getFirst().Window();
        		finalize.removeFirst();
        	}
        }
	}
	public static boolean GenCastleCanGen(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direct, int spawn, int floor)
	{
		if(spawn >= SpawnOut || floor >= CastleHeight)
		{
			return false;
		}
    	switch(direct)
		{
		case RoomNorth:
			XCoord -= RoomWidth;
			if(XCoord < CastleMinX)
				return false;
			break;
		case RoomEast:
			ZCoord -= RoomWidth;
			if(ZCoord < CastleMinZ)
				return false;
			break;
		case RoomSouth:
			XCoord += RoomWidth;
			if(XCoord > CastleMaxX)
				return false;
			break;
		case RoomWest:
			ZCoord += RoomWidth;
			if(ZCoord > CastleMaxZ)
				return false;
			break;
		}
    	if(world.getBlock(XCoord + RoomWidth / 2, YCoord - 1, ZCoord) == Blocks.stonebrick)
    	{
    		return false;
    	}
    	if(world.getBlock(XCoord + RoomWidth / 2, YCoord - 1, ZCoord + RoomWidth / 2) == Blocks.stonebrick)
    	{
    		return false;
    	}
    	if(world.getBlock(XCoord, YCoord - 1, ZCoord + RoomWidth / 2) == Blocks.stonebrick)
    	{
    		return false;
    	}
    	if(world.getBlock(XCoord, YCoord - RoomHeight - 1, ZCoord) != Blocks.stonebrick && floor != 0)
    	{
    		return false;
    	}
    	
    	return true;
	}
	public static boolean GenCastleCanGenNeedAir(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direct)
	{
    	switch(direct)
		{
		case RoomNorth:
			XCoord -= RoomWidth;
			if(XCoord < CastleMinX)
				return false;
			break;
		case RoomEast:
			ZCoord -= RoomWidth;
			if(ZCoord < CastleMinZ)
				return false;
			break;
		case RoomSouth:
			XCoord += RoomWidth;
			if(XCoord > CastleMaxX)
				return false;
			break;
		case RoomWest:
			ZCoord += RoomWidth;
			if(ZCoord > CastleMaxZ)
				return false;
			break;
		}
    	if(world.getBlock(XCoord + RoomWidth / 2, YCoord - 1, ZCoord) == Blocks.stonebrick)
    	{
    		return false;
    	}
    	if(world.getBlock(XCoord + RoomWidth / 2, YCoord - 1, ZCoord + RoomWidth / 2) == Blocks.stonebrick)
    	{
    		return false;
    	}
    	if(world.getBlock(XCoord, YCoord - 1, ZCoord + RoomWidth / 2) == Blocks.stonebrick)
    	{
    		return false;
    	}
    	if(world.getBlock(XCoord, YCoord - RoomHeight - 1, ZCoord) == Blocks.stonebrick)
    	{
    		return false;
    	}
    	return true;
	}
	/*
	public static void GenCastleRoomConnector(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direct, int spawn, int floor)
	{
    	switch(direct)
		{
		case RoomNorth:
			XCoord -= RoomWidth;
			break;
		case RoomEast:
			ZCoord -= RoomWidth;
			break;
		case RoomSouth:
			XCoord += RoomWidth;
			break;
		case RoomWest:
			ZCoord += RoomWidth;
			break;
		}

    	GenCastleRoom(world, rand, XCoord, YCoord, ZCoord);
		GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, (direct + 2) % 4);
		GenCastleWindows(world, rand, XCoord, YCoord, ZCoord);

		for(int tries = 0; tries < 60; tries++)
		{
	    	int dirout = rand.nextInt() % 4;
			if(GenCastleCanGen(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor))
			{
	    		GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, dirout);
	    		int rooms = rand.nextInt() % 100;

	    		if(rooms < 70){
	    			GenCastleRoomSplitter(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor);
	    		}
	    		else if(rooms < 80){
	    			GenCastleRoomLadder(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor);
	    		}
	    		else{
	    			GenCastleRoomTreasure(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor);
	    		}
	    		return;
			}
		}
	}
	*/
	/*
	public static void GenCastleRoomSplitter(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direct, int spawn, int floor)
	{
    	switch(direct)
		{
		case RoomNorth:
			XCoord -= RoomWidth;
			break;
		case RoomEast:
			ZCoord -= RoomWidth;
			break;
		case RoomSouth:
			XCoord += RoomWidth;
			break;
		case RoomWest:
			ZCoord += RoomWidth;
			break;
		}

    	GenCastleRoom(world, rand, XCoord, YCoord, ZCoord);
		GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, (direct + 2) % 4);
		//GenCastleWindows(world, rand, XCoord, YCoord, ZCoord);

		for(int tries = 0; tries < 70; tries++)
		{
	    	int dirout = rand.nextInt() % 4;
			if(GenCastleCanGen(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor))
			{
	    		int rooms = rand.nextInt() % 100;
	    		if(rooms < 20){
	    			GenCastleRoomConnector(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor);
	    		}
	    		else if(rooms < 70){
	    			GenCastleRoomSplitter(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor);
	    		}
	    		else if(rooms < 80){
	    			GenCastleRoomLadder(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor);
	    		}
	    		else{
	    			GenCastleRoomTreasure(world, rand, XCoord, YCoord, ZCoord, dirout, spawn + 1, floor);
	    		}
	    		GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, dirout);
			}
		}
	}
	*/
	/*
	public static void GenCastleRoomTreasure(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direct, int spawn, int floor)
	{
    	switch(direct)
		{
		case RoomNorth:
			XCoord -= RoomWidth;
			break;
		case RoomEast:
			ZCoord -= RoomWidth;
			break;
		case RoomSouth:
			XCoord += RoomWidth;
			break;
		case RoomWest:
			ZCoord += RoomWidth;
			break;
		}

    	GenCastleRoom(world, rand, XCoord, YCoord, ZCoord);
		GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, (direct + 2) % 4);
		GenCastleWindows(world, rand, XCoord, YCoord, ZCoord);

		switch(direct)
		{
		case RoomNorth:
			XCoord +=  1;
			ZCoord += RoomWidth / 2;
			break;
		case RoomEast:
			ZCoord += 1;
			XCoord += RoomWidth / 2;
			break;
		case RoomSouth:
			XCoord += RoomWidth - 2;
			ZCoord += RoomWidth / 2;
			break;
		case RoomWest:
			ZCoord += RoomWidth - 2;
			XCoord += RoomWidth / 2;
			break;
		}
		
		TileEntityChest chest = new TileEntityChest();
		world.setBlock(XCoord, YCoord, ZCoord, Blocks.chest);
		world.setTileEntity(XCoord, YCoord, ZCoord, chest);
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
	*/
	/*
	public static void GenCastleRoomLadder(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direct, int spawn, int floor)
	{
    	switch(direct)
		{
		case RoomNorth:
			XCoord -= RoomWidth;
			break;
		case RoomEast:
			ZCoord -= RoomWidth;
			break;
		case RoomSouth:
			XCoord += RoomWidth;
			break;
		case RoomWest:
			ZCoord += RoomWidth;
			break;
		}

    	GenCastleRoom(world, rand, XCoord, YCoord, ZCoord);
		GenCastleDoor(world, rand, XCoord, YCoord, ZCoord, (direct + 2) % 4);

		int XCoord2 = XCoord;
		int ZCoord2 = ZCoord;
		int meta = 1;
		switch(direct)
		{
		case RoomNorth:
			meta = 5;
			XCoord +=  1;
			ZCoord += RoomWidth / 2;
			break;
		case RoomEast:
			meta = 3;
			ZCoord += 1;
			XCoord += RoomWidth / 2;
			break;
		case RoomSouth:
			meta = 4;
			XCoord += RoomWidth - 2;
			ZCoord += RoomWidth / 2;
			break;
		case RoomWest:
			meta = 2;
			ZCoord += RoomWidth - 2;
			XCoord += RoomWidth / 2;
			break;
		}

		if(GenCastleCanGen(world, rand, XCoord2, YCoord + RoomHeight + 1, ZCoord2, -1, spawn + 1, floor + 1))
		{
	    	GenCastleRoom(world, rand, XCoord2, YCoord + RoomHeight + 1, ZCoord2);
			GenCastleDoor(world, rand, XCoord2, YCoord + RoomHeight + 1, ZCoord2, (direct + 2) % 4);
			GenCastleWindows(world, rand, XCoord2, YCoord + RoomHeight + 1, ZCoord2);

			for(int laddery = 0; laddery < RoomHeight + 2; laddery++){
				world.setBlock(XCoord, YCoord + laddery, ZCoord, Blocks.ladder, meta, 0);
			}
			
			GenCastleRoomSplitter(world, rand, XCoord2, YCoord + RoomHeight + 1, ZCoord2, (direct + 2) % 4, spawn + 1, floor + 1);
		}
	}
	*/
	
	public static void GenCastleFloor(World world, Random rand, int XCoord, int YCoord, int ZCoord)
	{
    	for(int airx_ = 0; airx_ < RoomWidth; airx_++) {
        	for(int airy_ = 0; airy_ <= RoomHeight; airy_++) {
	        	for(int airz_ = 0; airz_ < RoomWidth; airz_++) {
	        		if(airy_ == 0) {
	        			world.setBlock(XCoord + airx_, YCoord - 1 + airy_, ZCoord + airz_, Blocks.stonebrick);
	        		}
	        		else {
	        			world.setBlockToAir(XCoord + airx_, YCoord - 1 + airy_, ZCoord + airz_);
	        		}
	        	}
        	}
    	}
	}
	public static void GenCastleRoom(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direct)
	{	   	
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
    	for(int airx = 0; airx < RoomWidth; airx++) {
        	for(int airy = 0; airy <= RoomHeight; airy++) {
	        	for(int airz = 0; airz < RoomWidth; airz++) {
	        		if(airy == 0 || airy == RoomHeight || airx == 0 || airx == RoomWidth - 1 || airz == 0 || airz == RoomWidth - 1) {
	        			world.setBlock(XCoord + airx, YCoord - 1 + airy, ZCoord + airz, Blocks.stonebrick);
	        		}
	        		else {
	        			world.setBlockToAir(XCoord + airx, YCoord - 1 + airy, ZCoord + airz);
	        		}
	        	}
        	}
    	}
	}
	public static void GenCastleGarden(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direct)
	{	   	
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
    	for(int airx = 0; airx < RoomWidth; airx++) {
        	for(int airy = 0; airy < RoomHeight; airy++) {
	        	for(int airz = 0; airz < RoomWidth; airz++) {
	        		if(airy == 0){
	        			if(airx == 0 || airx == RoomWidth - 1 || airz == 0 || airz == RoomWidth - 1) {
	        				world.setBlock(XCoord + airx, YCoord - 1 + airy, ZCoord + airz, Blocks.stonebrick);
	        			}
        				else{
        					world.setBlock(XCoord + airx, YCoord - 1 + airy, ZCoord + airz, Blocks.grass);
        				}
	        		}
	        		else {
	        			world.setBlockToAir(XCoord + airx, YCoord - 1 + airy, ZCoord + airz);
	        		}
	        	}
        	}
    	}
	}
	public static void GenCastleDoor(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direction)
	{
		switch(direction)
		{
		case RoomNorth:
			for(int doory = 0; doory <= 2; doory++){
				world.setBlockToAir(XCoord, YCoord + doory, ZCoord + RoomWidth / 2 - 1);
				world.setBlockToAir(XCoord, YCoord + doory, ZCoord + RoomWidth / 2);
			}
			break;
		case RoomEast:
			for(int doory = 0; doory <= 2; doory++){
				world.setBlockToAir(XCoord + RoomWidth / 2 - 1, YCoord + doory, ZCoord);
				world.setBlockToAir(XCoord + RoomWidth / 2, YCoord + doory, ZCoord);
			}
			break;
		case RoomSouth:
			for(int doory = 0; doory <= 2; doory++){
				world.setBlockToAir(XCoord + RoomWidth - 1, YCoord + doory, ZCoord + RoomWidth / 2 - 1);
				world.setBlockToAir(XCoord + RoomWidth - 1, YCoord + doory, ZCoord + RoomWidth / 2);
			}
			break;
		case RoomWest:
			for(int doory = 0; doory <= 2; doory++){
				world.setBlockToAir(XCoord + RoomWidth / 2 - 1, YCoord + doory, ZCoord + RoomWidth - 1);
				world.setBlockToAir(XCoord + RoomWidth / 2, YCoord + doory, ZCoord + RoomWidth - 1);
			}
			break;
		}
	}
	public static void GenCastleNoWall(World world, Random rand, int XCoord, int YCoord, int ZCoord, int direction)
	{
		switch(direction)
		{
		case RoomNorth:
			for(int wallw = 1; wallw < RoomWidth - 1; wallw++)
			{
				for(int doory = 0; doory < RoomHeight - 1; doory++){
					world.setBlockToAir(XCoord, YCoord + doory, ZCoord + wallw);
				}
			}
			break;
		case RoomEast:
			for(int wallw = 1; wallw < RoomWidth - 1; wallw++)
			{
				for(int doory = 0; doory < RoomHeight - 1; doory++){
					world.setBlockToAir(XCoord + wallw, YCoord + doory, ZCoord);
				}
			}
			break;
		case RoomSouth:
			for(int wallw = 1; wallw < RoomWidth - 1; wallw++)
			{
				for(int doory = 0; doory < RoomHeight - 1; doory++){
					world.setBlockToAir(XCoord + RoomWidth - 1, YCoord + doory, ZCoord + wallw);
				}
			}
			break;
		case RoomWest:
			for(int wallw = 1; wallw < RoomWidth - 1; wallw++)
			{
				for(int doory = 0; doory < RoomHeight - 1; doory++){
					world.setBlockToAir(XCoord + wallw, YCoord + doory, ZCoord + RoomWidth - 1);
				}
			}
			break;
		}
	}
	public static void GenCastleWindows(World world, Random rand, int XCoord, int YCoord, int ZCoord){
    	if(world.getBlock(XCoord + RoomWidth, YCoord, ZCoord) == Blocks.air){  
    		for(int airx = 0; airx < RoomWidth; airx++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airx >= 2 && airx <= 5 && world.getBlock(XCoord + RoomWidth - 1, YCoord + airy, ZCoord  + airx) == Blocks.stonebrick) 
	        			world.setBlock(XCoord + RoomWidth - 1, YCoord + airy, ZCoord  + airx, Blocks.stained_glass);
	        	}
    		}  	
    	}
    	if(world.getBlock(XCoord - 1, YCoord, ZCoord) == Blocks.air){	
    		for(int airx = 0; airx < RoomWidth; airx++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airx >= 2 && airx <= 5 && world.getBlock(XCoord, YCoord + airy, ZCoord + airx) == Blocks.stonebrick) 
	        			world.setBlock(XCoord, YCoord + airy, ZCoord + airx, Blocks.stained_glass);
	        	}
    		}
    	}
    	if(world.getBlock(XCoord, YCoord, ZCoord + RoomWidth) == Blocks.air){
    		for(int airz = 0; airz < RoomWidth; airz++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airz >= 2 && airz <= 5 && world.getBlock(XCoord + airz, YCoord + airy, ZCoord + RoomWidth - 1) == Blocks.stonebrick)
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord + RoomWidth - 1, Blocks.stained_glass);
	        	}
    		}
    	}
    	if(world.getBlock(XCoord, YCoord, ZCoord - 1) == Blocks.air){	
    		for(int airz = 0; airz < RoomWidth; airz++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airz >= 2 && airz <= 5 && world.getBlock(XCoord + airz, YCoord + airy, ZCoord) == Blocks.stonebrick)
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord, Blocks.stained_glass);
	        	}
    		}
    	}
	}
	public static void GenCastleStainWindows(World world, Random rand, int XCoord, int YCoord, int ZCoord){
    	if(world.getBlock(XCoord + RoomWidth, YCoord, ZCoord) == Blocks.air){  
    		for(int airx = 0; airx < RoomWidth; airx++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airx >= 2 && airx <= 5 && world.getBlock(XCoord + RoomWidth - 1, YCoord + airy, ZCoord  + airx) == Blocks.stonebrick) 
	        			world.setBlock(XCoord + RoomWidth - 1, YCoord + airy, ZCoord  + airx, Blocks.stained_glass, 14, 0);
	        	}
    		}  	
    	}
    	if(world.getBlock(XCoord - 1, YCoord, ZCoord) == Blocks.air){	
    		for(int airx = 0; airx < RoomWidth; airx++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airx >= 2 && airx <= 5 && world.getBlock(XCoord, YCoord + airy, ZCoord + airx) == Blocks.stonebrick) 
	        			world.setBlock(XCoord, YCoord + airy, ZCoord + airx, Blocks.stained_glass, 14, 0);
	        	}
    		}
    	}
    	if(world.getBlock(XCoord, YCoord, ZCoord + RoomWidth) == Blocks.air){
    		for(int airz = 0; airz < RoomWidth; airz++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airz >= 2 && airz <= 5 && world.getBlock(XCoord + airz, YCoord + airy, ZCoord + RoomWidth - 1) == Blocks.stonebrick)
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord + RoomWidth - 1, Blocks.stained_glass, 14, 0);
	        	}
    		}
    	}
    	if(world.getBlock(XCoord, YCoord, ZCoord - 1) == Blocks.air){	
    		for(int airz = 0; airz < RoomWidth; airz++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airz >= 2 && airz <= 5 && world.getBlock(XCoord + airz, YCoord + airy, ZCoord) == Blocks.stonebrick)
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord, Blocks.stained_glass, 14, 0);
	        	}
    		}
    	}
	}
	public static void GenCastleStainWindowsR(World world, Random rand, int XCoord, int YCoord, int ZCoord){
    	if(world.getBlock(XCoord + RoomWidth, YCoord, ZCoord) == Blocks.air){  
    		for(int airx = 0; airx < RoomWidth; airx++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airx >= 2 && airx <= 5 && world.getBlock(XCoord + RoomWidth - 1, YCoord + airy, ZCoord  + airx) == Blocks.stonebrick) 
	        			world.setBlock(XCoord + RoomWidth - 1, YCoord + airy, ZCoord  + airx, Blocks.stained_glass, rand.nextInt(14) + 1, 0);
	        	}
    		}  	
    	}
    	if(world.getBlock(XCoord - 1, YCoord, ZCoord) == Blocks.air){	
    		for(int airx = 0; airx < RoomWidth; airx++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airx >= 2 && airx <= 5 && world.getBlock(XCoord, YCoord + airy, ZCoord + airx) == Blocks.stonebrick) 
	        			world.setBlock(XCoord, YCoord + airy, ZCoord + airx, Blocks.stained_glass,  rand.nextInt(14) + 1, 0);
	        	}
    		}
    	}
    	if(world.getBlock(XCoord, YCoord, ZCoord + RoomWidth) == Blocks.air){
    		for(int airz = 0; airz < RoomWidth; airz++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airz >= 2 && airz <= 5 && world.getBlock(XCoord + airz, YCoord + airy, ZCoord + RoomWidth - 1) == Blocks.stonebrick)
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord + RoomWidth - 1, Blocks.stained_glass,  rand.nextInt(14) + 1, 0);
	        	}
    		}
    	}
    	if(world.getBlock(XCoord, YCoord, ZCoord - 1) == Blocks.air){	
    		for(int airz = 0; airz < RoomWidth; airz++) {
	        	for(int airy = 1; airy <= 3; airy++) {
	        		if(airz >= 2 && airz <= 5 && world.getBlock(XCoord + airz, YCoord + airy, ZCoord) == Blocks.stonebrick)
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord, Blocks.stained_glass,  rand.nextInt(14) + 1, 0);
	        	}
    		}
    	}
	}
	public static void GenCastleRails(World world, Random rand, int XCoord, int YCoord, int ZCoord){
    	if(world.getBlock(XCoord + RoomWidth, YCoord - 1, ZCoord) != Blocks.stonebrick){  
    		for(int airx = 0; airx < RoomWidth; airx++) {
	        	for(int airy = 0; airy <= 1; airy++) { 
	        		if(airy == 0){
	        			world.setBlock(XCoord + RoomWidth - 1, YCoord + airy, ZCoord  + airx, Blocks.stonebrick);
	        		}
	        		else{
	        			world.setBlock(XCoord + RoomWidth - 1, YCoord + airy, ZCoord  + airx, Blocks.fence);
	        		}
	        	}
    		}  	
    	}
    	if(world.getBlock(XCoord - 1, YCoord - 1, ZCoord) != Blocks.stonebrick){	
    		for(int airx = 0; airx < RoomWidth; airx++) {
	        	for(int airy = 0; airy <= 1; airy++) { 
	        		if(airy == 0){
	        			world.setBlock(XCoord, YCoord + airy, ZCoord + airx, Blocks.stonebrick);
	        		}
	        		else{
	        			world.setBlock(XCoord, YCoord + airy, ZCoord + airx, Blocks.fence);
	        		}
	        	}
    		}
    	}
    	if(world.getBlock(XCoord, YCoord - 1, ZCoord + RoomWidth) != Blocks.stonebrick){
    		for(int airz = 0; airz < RoomWidth; airz++) {
	        	for(int airy = 0; airy <= 1; airy++) { 
	        		if(airy == 0){
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord + RoomWidth - 1, Blocks.stonebrick);
	        		}
	        		else{
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord + RoomWidth - 1, Blocks.fence);
	        		}
	        	}
    		}
    	}
    	if(world.getBlock(XCoord, YCoord - 1, ZCoord - 1) != Blocks.stonebrick){	
    		for(int airz = 0; airz < RoomWidth; airz++) {
	        	for(int airy = 0; airy <= 1; airy++) { 
	        		if(airy == 0){
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord, Blocks.stonebrick);
	        		}
	        		else{
	        			world.setBlock(XCoord + airz, YCoord + airy, ZCoord, Blocks.fence);
	        		}
	        	}
    		}
    	}
	}
}
