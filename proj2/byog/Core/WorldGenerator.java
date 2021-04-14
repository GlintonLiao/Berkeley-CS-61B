package byog.Core;

import byog.Core.RandomUtils;
import byog.TileEngine.TETile;
import byog.lab5.Position;

import java.util.Random;

public class WorldGenerator {
    private static double usage;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void generator(TETile[][] world, Position p, int s, TETile t){
        if (usage > 0.5) {
            return;
        } else {
        }
    }

    public static void makeRoom(TETile[][] world, Position p, TETile t) {
        int width = RandomUtils.uniform(RANDOM, 10);
        int height = RandomUtils.uniform(RANDOM, 10);
        for (int j = p.y; j < p.y + height; j += 1) {
            for (int i = p.x; i < p.x + width; i += 1) {
                world[i][j] = t;
            }
        }
    }

    public static void makeHallway(TETile[][] world, Position p, TETile t) {
        int width = RandomUtils.uniform(RANDOM, 10);
        int height = RandomUtils.uniform(RANDOM, 10);
    }


}
