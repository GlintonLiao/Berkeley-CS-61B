package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.Core.RandomUtils;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static double usage;
    private static int size;
    public TETile[][] world = new TETile[WIDTH][HEIGHT];

    /**
     * Fills the given 2D array of tiles with NOTHING tiles.
     * @param tiles tiles to be filled.
     */
    public static void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* Get next position by previous geometry */
    public static Position getNextPosition(Position prev, int width, int height){
        int x = RandomUtils.uniform(RANDOM, prev.x, prev.x + width);
        int y = RandomUtils.uniform(RANDOM, prev.y, prev.y + height);
        return new Position(x, y);
    }

    /* Make a random room by previous geometry */
    public static void makeRoom(TETile[][] world, Position prev, int prevWidth, int prevHeight){
        if (usage > 0.5) { return; }
        int width = RandomUtils.uniform(RANDOM, 0, 7);
        int height = RandomUtils.uniform(RANDOM, 0, 7);
        Position next = getNextPosition(prev,prevWidth, prevHeight);
        drawRoom(world, next, width, height);
    }

    /* Draw a Room */
    public static void drawRoom(TETile[][] world, Position p, int width, int height) {
        for (int i = p.x; i < p.x + width; i += 1) {
            for (int j = p.y; j < p.y + height; j += 1) {
                world[i][j] = Tileset.FLOOR;
            }
        }
        size += width * height;
        usage = (double) size / (double) (WIDTH * HEIGHT);

        // Add wall to the outlines
        addWall(world, p, width, height);

        makeHallway(world, p, width, height);
    }

    /* Generate a Hallway */
    public static void makeHallway(TETile[][] world, Position prev, int prevWidth, int prevHeight) {
        if (usage > 0.5) { return; }
        Position next = getNextPosition(prev, prevWidth, prevHeight);
        boolean isHorizontal = RandomUtils.bernoulli(RANDOM);
        int width;
        int height;
        if (isHorizontal) {
            width = RandomUtils.uniform(RANDOM, 0, 7);
            height = 1;
        } else {
            height = RandomUtils.uniform(RANDOM, 0, 7);
            width = 1;
        }
        drawHallway(world, next, width, height);
    }

    /* Draw a Hallway */
    public static void drawHallway(TETile[][] world, Position p, int width, int height) {
        if (width == 1) {
            for (int i = p.y; i < p.y + height; i += 1) {
                world[p.x][i] = Tileset.FLOOR;
            }
        } else {
            for (int i = p.x; i < p.x + width; i += 1) {
                world[i][p.y] = Tileset.FLOOR;
            }
        }

        size += width * height;
        usage = (double) size / (double) (WIDTH * HEIGHT);

        // Add wall to the outlines
        addWall(world, p, width, height);

        // Decide which one is the next geometry
        boolean isNextHallway = RandomUtils.bernoulli(RANDOM);
        if (isNextHallway) {
            makeHallway(world, p, width, height);
        } else {
            makeRoom(world, p, width, height);
        }
    }

    /* Add a frame to the geometry */
    public static void addWall(TETile[][] world, Position p, int width, int height) {
        int newWidth = width + 2;
        int newHeight = height + 2;
        Position newP = new Position(p.x - 1, p.y - 1);
        for (int i = newP.x; i < newP.x + newWidth; i += 1) {
            for (int j = newP.y; j < newP.y + newHeight; j += 1) {
                if (world[i][j] == Tileset.FLOOR) {
                    continue;
                } else {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        Position startPosition = new Position(5, 5);
        makeRoom(world, startPosition, 3, 4);
        TETile[][] finalWorldFrame = world;
        return finalWorldFrame;
    }
}
