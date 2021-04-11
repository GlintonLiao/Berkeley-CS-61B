package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        int totalLine = s * 2;
        int currentLine = 1;

        while (currentLine <= totalLine) {
            for (int i = p.x; i < p.x + s; i += 1) {
                world[i][p.y] = t;
            }
            p = getNextStart(p, currentLine, totalLine);
            s = getNextLength(s, currentLine, totalLine);
            currentLine += 1;
        }
    }

    /**
     * When currentLine is less than half of the hexagon, increase x by 1
     * When currentLine equals to half the totalLine, x stays the same
     * When currentLine is greater than half of the hexagon, decrease x by 1
     */
    private static Position getNextStart(Position p, int currentLine, int totalLine) {
        if (currentLine == totalLine / 2) {
            p.y += 1;
            return p;
        } else if (currentLine < totalLine / 2) {
            p.y += 1;
            p.x -= 1;
            return p;
        } else {
            p.y += 1;
            p.x += 1;
            return p;
        }
    }

    /**
     * When currentLine is less than half of the hexagon, increase length by 2
     * When currentLine equals to half the totalLine, length stays the same
     * When currentLine is greater than half of the hexagon, decrease length by 2
     */
    private static int getNextLength(int s, int currentLine, int totalLine) {
        if (currentLine == totalLine / 2) {
            return s;
        } else if (currentLine < totalLine / 2) {
            return s + 2;
        } else {
            return s - 2;
        }
    }


      //Test for the addHexagon method
/*    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);
        TETile newTile = Tileset.WALL;

        TETile[][] world = new TETile[30][30];
        for (int x = 0; x < 30; x += 1) {
            for (int y = 0; y < 30; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Position p = new Position(10, 5);
        addHexagon(world, p, 6, newTile);

        ter.renderFrame(world);
    }*/

}
