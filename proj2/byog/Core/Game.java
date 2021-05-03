package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

import byog.lab5.Position;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;

    private static long SEED;
    private static Random RANDOM;

    public TETile[][] world = new TETile[WIDTH][HEIGHT];
    int[] player;

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
        boolean movingBack = RandomUtils.bernoulli(RANDOM);

        if (height == 1) {
            if (movingBack) {
                x = RandomUtils.uniform(RANDOM, prev.x, prev.x + width);
            }
            x = RandomUtils.uniform(RANDOM, prev.x, prev.x + width);
            y = prev.y;
        } else if (width == 1){
            x = prev.x;
            y = RandomUtils.uniform(RANDOM, prev.y, prev.y + height);
        }

        return new Position(x, y);
    }

    /*public Position getNextAtCorner(Position prev, int width, int height) {
        int corner = RandomUtils.uniform(RANDOM, 0, 3);
        int x = 0;
        int y = 0;
        switch (corner) {
            case 0:
                x = prev.x;
                y = prev.y;
            case 1:
                x = prev.x;
                y = prev.y + height;
            case 2:
                x = prev.x + width;
                y = prev.y + height;
            case 3:
                x = prev.x + width;
                y = prev.y;
        }
        return new Position(x, y);
    }*/


    /* Move start point randomly */
    public Position movePoint(Position p, int width, int height) {

        int xDistance = RandomUtils.uniform(RANDOM, 2);

        /*if (isLeft) {
            p.x -= xDistance;
        }

        if (isDown) {
            p.y -= yDistance;
        }*/

        p.x = p.x - xDistance + 1;
        return p;
    }

    /* Make a random room by previous geometry */
    public void makeRoom(TETile[][] world, Position prev, int prevWidth, int prevHeight){
        int width = RandomUtils.uniform(RANDOM, 1, 8);
        int height = RandomUtils.uniform(RANDOM, 1, 10);
        boolean isBack = RandomUtils.bernoulli(RANDOM);

        Position originStart = getNextPosition(prev, prevWidth, prevHeight);
        /*Position newStart = movePoint(originStart, width, height);*/
        Position newStart = originStart;

        if (prevWidth == 1) {
            newStart.y = RandomUtils.uniform(RANDOM, prev.y, prev.y + prevHeight);
            if (isBack) {
                newStart.x = prev.x - width + 1;
            } else {
                newStart.x = prev.x;
            }
        } else {
            newStart.x = RandomUtils.uniform(RANDOM, prev.x, prev.x + prevWidth);
            if (isBack) {
                newStart.y = prev.y - height + 1;
            } else {
                newStart.y = prev.y;
            }
        }


        if (newStart.x + width >= WIDTH) {
            addLocker(world);
            return;
        } else if (newStart.y + height >= HEIGHT) {
            newStart.y = newStart.y - height + 1;
        }
        if (newStart.y <= 0) {
            newStart.y = newStart.y + height - 1;
        } if (newStart.x <= 0) {
            newStart.x = 2;
        }

        // Start over
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

        // Start over
        makeHallway(world, p, width, height);

    }

    /* Generate a Hallway */
    public void makeHallway(TETile[][] world, Position prev, int prevWidth, int prevHeight) {
        Position next = new Position(0, 0);
        boolean isHorizontal = RandomUtils.bernoulli(RANDOM);
        int corner = RandomUtils.uniform(RANDOM, 0, 4);

        int width1;
        int height1;

        if (isHorizontal) {
            width1 = RandomUtils.uniform(RANDOM, 8, 12);
            height1 = 1;
        } else {
            height1 = RandomUtils.uniform(RANDOM, 6, 20);
            width1 = 1;
        }

        /*switch (corner) {
            case 0:
                if (isHorizontal) {
                    next.x = prev.x - width1 + 1;
                    next.y = prev.y;
                } else {
                    next.x = prev.x;
                    next.y = prev.y - height1 + 1;
                }
                break;
            case 1:
                if (isHorizontal) {
                    next.x = prev.x - width1 + 1;
                    next.y = prev.y + prevWidth - 1;
                } else {
                    next.x = prev.x;
                    next.y = prev.y + prevHeight - 1;
                }
                break;
            case 2:
                next.x = prev.x + prevWidth - 1;
                next.y = prev.y + prevHeight - 1;
                break;
            case 3:
                next.x = prev.x + prevWidth - 1;
                if (isHorizontal) {
                    next.y = prev.y;
                } else {
                    next.y = prev.y - height1 + 1;
                }
                break;
        }*/


        // Pick a corner as the start point of the hallway randomly
        switch (corner) {
            case 0:
                next.x = prev.x - width1 + 1;
                next.y = RandomUtils.uniform(RANDOM, prev.y, prev.y + prevHeight);
                break;
            case 1:
                next.x = RandomUtils.uniform(RANDOM, prev.x, prev.x + prevWidth);
                next.y = prev.y + prevHeight - 1;
                break;
            case 2:
                next.x = prev.x + prevWidth - 1;
                next.y = RandomUtils.uniform(RANDOM, prev.y, prev.y + prevHeight);
                break;
            case 3:
                next.x = RandomUtils.uniform(RANDOM, prev.x, prev.x + prevWidth);
                next.y = prev.y - height1 + 1;
                break;
        }

        // Check feasibility
        if (next.x + width1 >= WIDTH) {
            // The generation process reaches the end
            addLocker(world);
            return;
        } else if (next.y + height1 >= HEIGHT) {
            next.y = prev.y - height1 + 1;
        } else if (next.y <= 0) {
            next.y = prev.y + prevHeight - 1;
        } else if (next.x <= 0) {
            next.x = prev.x + prevWidth - 1;
        }

        drawHallway(world, next, width1, height1);

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

        // 1/2 chance to generate a room when the hallway is vertical
        makeEndWay(world, p, width, height);

        //start over
        makeRoom(world, p, width, height);


    }

    /** There is 1/2 chance of placing a room at the end of the vertical hallway.
     * if the previous hallway is vertical, then pick the highest or the lowest point and generate a horizontal hallway pointing back.
     * And generate a room at the leftmost of the hallway.
     */
    public void makeEndWay(TETile[][] world, Position prev, int prevWidth, int prevHeight) {
        boolean isEnding = RandomUtils.bernoulli(RANDOM);
        boolean upmost = RandomUtils.bernoulli(RANDOM);
        int width = RandomUtils.uniform(RANDOM, 2, 10);
        int height = RandomUtils.uniform(RANDOM, 2, 6);
        Position next = prev;

        // When the hallway is horizontal or the ending factor equals to 0, do nothing
        if (prevWidth > 1 || !isEnding) {
            return;
        }

        // pick the ending point of the hallway in last round
        if (upmost) {
            next.y = prev.y + prevHeight - 1;
        } else {
            next.y = prev.y;
        }

        // Check feasibility
        if (next.x + width >= WIDTH) {
            // The generation process reaches the end
            addLocker(world);
            return;
        } else if (next.y + height >= HEIGHT) {
            next.y = next.y - height + 1;
        }
        if (next.y <= 0) {
            next.y = next.y + height - 1;
        } if (next.x <= 0) {
            next.x = 2;
        }

        drawEndRoom(world, next, width, height);

    }

    /* Draw a room without invoke another makeHallway method.*/
    public void drawEndRoom(TETile[][] world, Position p, int width, int height) {
        // fill with floor tile
        for (int i = p.x; i < p.x + width; i += 1) {
            for (int j = p.y; j < p.y + height; j += 1) {
                world[i][j] = Tileset.FLOOR;
            }
        }

        // Add wall to the outlines
        addWall(world, p, width, height);
    }

    /* Add a frame to the geometry */
    public void addWall(TETile[][] world, Position p, int width, int height) {
        int newWidth = width + 2;
        int newHeight = height + 2;
        Position newP = new Position(p.x - 1, p.y - 1);


        // Prevent from setting the wall out of bound
        if (newP.x > WIDTH) {
            newP.x = WIDTH;
        } else if (newP.x < 0) {
            newP.x = 0;
        }

        if (newP.y > HEIGHT) {
            newP.y = HEIGHT;
        } else if (newP.y < 0) {
            newP.y = 0;
        }

        // Fill with WALL tile
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

    /* Find a golden door in WALL tile */
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
        StdDraw.setCanvasSize(1280, 1280);
        StdDraw.setXscale(0, 1280);
        StdDraw.setYscale(0, 1280);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(640, 960, "CS61B: THE GAME");

        Font smallFont = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(640, 680, "New Game (N)");
        StdDraw.text(640, 640, "Load Game(L)");
        StdDraw.text(640, 600, "Quit (Q)");
        StdDraw.show();

        char inputChar = solicitInput();

        if (inputChar == 'n') {
            StdDraw.clear(Color.BLACK);
            StdDraw.text(640, 640, "Please enter the seed: ");
            StdDraw.show();

            String input = "";

            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char key = Character.toLowerCase(StdDraw.nextKeyTyped());

                    if (key == 's') {
                        break;
                    }

                    input += key;
                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(640, 640, "Please enter the seed: ");
                    StdDraw.text(640, 600, "Your input: " + input);
                    StdDraw.show();
                }
            }

            SEED = Long.parseLong(input);
            RANDOM = new Random(SEED);

            ter.initialize(WIDTH, HEIGHT);
            fillWithNothing(world);

            Position p = new Position(8, 8);
            makeRoom(world, p, 1, 4);

            generatePlayer();

            ter.renderFrame(world);
            showTile(world);

        }

        if (inputChar == 'q') {
            System.exit(0);
        }

        if (inputChar == 'l') {
            System.exit(0);
        }

        play();
    }

    public char solicitInput() {
        ArrayList<Character> nlq = new ArrayList<>();
        nlq.add('n');
        nlq.add('l');
        nlq.add('q');
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (nlq.contains(key)) {
                    return key;
                }
            }
        }
    }

    public void play() {

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                showTile(world);
                char input = Character.toLowerCase(StdDraw.nextKeyTyped());

                if (input == 'w' || input == 'a' || input == 's' || input == 'd') {
                    move(input);
                    ter.renderFrame(world);
                    showTile(world);
                }

                if (input == ':') {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char keyToExit = Character.toLowerCase(StdDraw.nextKeyTyped());
                            if (keyToExit == 'q') {
                                System.exit(0);
                            } else {
                                showTile(world);
                                ter.renderFrame(world);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void generatePlayer() {
        int x = RandomUtils.uniform(RANDOM, 0, WIDTH);
        int y = RandomUtils.uniform(RANDOM, 0, HEIGHT);
        while (world[x][y] != Tileset.FLOOR) {
            x = RandomUtils.uniform(RANDOM, 0, WIDTH);
            y = RandomUtils.uniform(RANDOM, 0, HEIGHT);
        }
        player = new int[]{x, y};
        world[x][y] = Tileset.PLAYER;
    }

    public void moveInt(int x, int y) {
        int px = this.player[0];
        int py = this.player[1];

        if (this.world[px + x][py + y].equals(Tileset.FLOOR)) {
            this.world[px + x][py + y] = Tileset.PLAYER;
            this.world[px][py] = Tileset.FLOOR;
            this.player = new int[]{px + x, py + y};
        }
    }

    public void move(char c) {
        if (c == 'w') {
            moveInt(0, 1);
        }
        if (c == 'a') {
            moveInt(-1, 0);
        }
        if (c == 's') {
            moveInt(0, -1);
        }
        if (c == 'd') {
            moveInt(1, 0);
        }

    }

    public void showTile(TETile[][] world) {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String tileName = "";

        if (x < WIDTH && y < HEIGHT) {
            tileName = world[x][y].description();
            StdDraw.setPenColor(Color.white);
            Font nameFont = new Font("Monaco", Font.PLAIN, 20);
            StdDraw.setFont(nameFont);
            StdDraw.textLeft(1, HEIGHT - 1, tileName);
        }

        StdDraw.show();
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

        SEED = Long.parseLong(input);
        ter.initialize(WIDTH, HEIGHT);
        fillWithNothing(world);

        Position startPosition = new Position(8, 8);
        makeRoom(world, startPosition, 3, 4);

        TETile[][] finalWorldFrame = world;
        return finalWorldFrame;
    }


    // Test for Phase 1
    /*public static void main(String[] args) {
        Game newGame = new Game();
        newGame.ter.initialize(WIDTH, HEIGHT);
        newGame.fillWithNothing(newGame.world);

        Position p = new Position(8, 8);
        newGame.makeRoom(newGame.world, p, 1, 4);

        newGame.ter.renderFrame(newGame.world);
    }*/
}
