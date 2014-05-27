package castle.gen.generator;

import java.util.Random;

import net.minecraft.world.World;

public class CastleChunk {
    public int XCoord;
    public int YCoord;
    public int ZCoord;
    public int direct;
    public int spawn;
    public int floor;
    public World world;
    public Random rand;
    
    public CastleChunk(World world_, Random rand_, int XCoord_, int YCoord_, int ZCoord_, int direct_, int spawn_, int floor_){
    	world = world_;
    	rand = rand_;
    	XCoord = XCoord_;
    	YCoord = YCoord_;
    	ZCoord = ZCoord_;
    	direct = direct_;
    	spawn = spawn_;
    	floor = floor_;
    }
    public void Generate(){}
    public void Window(){}
}
