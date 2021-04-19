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

    public TETile[][] world = new TETile[WIDTH][HEIGHT];

    /**
     * Fills the given 2D array of tiles with NOTHING tiles.
     * @param tiles tiles to be filled.
     */
    public void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* Get next position by previous geometry */
    public Position getNextPosition(Position prev, int width, int height){
        int x = 0;
        int y = 0;

        if (height == 1) {
            x = RandomUtils.uniform(RANDOM, prev.x, prev.x + width);
            y = prev.y;
        } else if (width == 1){
            x = prev.x;
            y = RandomUtils.uniform(RANDOM, prev.y, prev.y + height);
        } else {
            x = prev.x + width;
            y = RandomUtils.uniform(RANDOM, prev.y, prev.y + height);
        }

        return new Position(x, y);
    }


    /* Move start point randomly */
    public Position movePoint(Position p, int width, int height) {

        int xDistance = RandomUtils.uniform(RANDOM, Math.max(2, width));
        int yDistance = RandomUtils.uniform(RANDOM, Math.max(2, height));

        /*if (isLeft) {
            p.x -= xDistance;
        }

        if (isDown) {
            p.y -= yDistance;
        }*/

        p.x -= xDistance;
        p.y -= yDistance;
        return p;
    }

    /* Make a random room by previous geometry */
    public void makeRoom(TETile[][] world, Position prev, int prevWidth, int prevHeight){
        int width = RandomUtils.uniform(RANDOM, 2, 6);
        int height = RandomUtils.uniform(RANDOM, 2, 6);

        Position originStart = getNextPosition(prev, prevWidth, prevHeight);
        Position newStart = movePoint(originStart, width, height);

        if (newStart.x + width >= WIDTH) {
            addLocker(world);
            return;
        } else if (newStart.y + height >= HEIGHT) {
            newStart.y -= height;
        }
        if (newStart.y <= 0) {
            newStart.y = 2;
        }

        drawRoom(world, newStart, width, height);
    }

    /* Draw a Room */
    public void drawRoom(TETile[][] world, Position p, int width, int height) {

        // fill with floor tile
        for (int i = p.x; i < p.x + width; i += 1) {
            for (int j = p.y; j < p.y + height; j += 1) {
                world[i][j] = Tileset.FLOOR;
            }
        }

        // Add wall to the outlines
        addWall(world, p, width, height);

        makeHallway(world, p, width, height);

    }

    /* Generate a Hallway */
    public void makeHallway(TETile[][] world, Position prev, int prevWidth, int prevHeight) {
        Position next1 = getNextPosition(prev, prevWidth, prevHeight);
        boolean isHorizontal = RandomUtils.bernoulli(RANDOM);


        int width1;
        int height1;
        if (isHorizontal) {
            width1 = RandomUtils.uniform(RANDOM, 6, 15);
            height1 = 1;
        } else {
            height1 = RandomUtils.uniform(RANDOM, 9, 18);
            width1 = 1;
        }

        Position newStart = movePoint(next1, width1, height1);

        if (newStart.x + width1 >= WIDTH) {
            addLocker(world);
            return;
        } else if (newStart.y + height1 >= HEIGHT) {
            newStart.y -= height1;
        } else if (newStart.y <= 0) {
            newStart.y = 2;
        }

        drawHallway(world, newStart, width1, height1);

        /*int width2;
        int height2;
        if (!isHorizontal) {
            width2 = RandomUtils.uniform(RANDOM, 5, 10);
            height2 = 1;
        } else {
            height2 = RandomUtils.uniform(RANDOM, 5, 10);
            width2 = 1;
        }

        if (next2.x + width2 >= WIDTH) {
            return;
        } else if (next2.y + height2 >= HEIGHT || !isDown) {
            next2.y = (next2.y - height2 <= 0)? 1: next2.y - height2;
        }

        drawHallway(world, next2, width2, height2);*/

        // Decide which one is the next geometry
        /*boolean isNextHallway = RandomUtils.bernoulli(RANDOM);
        if (isNextHallway) {
            makeHallway(world, next1, width1, height1);
        } else {
            makeRoom(world, next2, width2, height2);
        }*/

        /*makeRoom(world, next2, width2, height2);
        makeHallway(world, next1, width1, height1);*/

    }

    /* Draw a Hallway */
    public void drawHallway(TETile[][] world, Position p, int width, int height) {

        // fill with floor tile
        if (width == 1) {
            for (int i = p.y; i < p.y + height; i += 1) {
                world[p.x][i] = Tileset.FLOOR;
            }
        } else {
            for (int i = p.x; i < p.x + width; i += 1) {
                world[i][p.y] = Tileset.FLOOR;
            }
        }


        // Add wall to the outlines
        addWall(world, p, width, height);

        makeRoom(world, p, width, height);

    }

    /* Add a frame to the geometry */
    public void addWall(TETile[][] world, Position p, int width, int height) {
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

    public void addLocker(TETile[][] world) {
        int x = RandomUtils.uniform(RANDOM, WIDTH);
        int y = RandomUtils.uniform(RANDOM, HEIGHT);
        while (world[x][y] != Tileset.WALL) {
            Random newSeed = new Random(x);
            x = RandomUtils.uniform(newSeed, WIDTH);
            y = RandomUtils.uniform(newSeed, HEIGHT);
        }
        world[x][y] = Tileset.LOCKED_DOOR;
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

        ter.initialize(WIDTH, HEIGHT);
        fillWithNothing(world);


        Position startPosition = new Position(5, 5);
        makeRoom(world, startPosition, 3, 4);

        TETile[][] finalWorldFrame = world;
        return finalWorldFrame;
    }

    public static void main(String[] args) {
        Game newGame = new Game();
        newGame.ter.initialize(WIDTH, HEIGHT);
        newGame.fillWithNothing(newGame.world);

        Position p = new Position(8, 15);
        newGame.makeRoom(newGame.world, p, 2, 4);

        newGame.ter.renderFrame(newGame.world);
    }
}
