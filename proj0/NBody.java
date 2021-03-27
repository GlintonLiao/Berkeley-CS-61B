import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NBody {

    public static double readRadius(String filename) {
        In in = new In(filename);
        int num = in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int size = in.readInt();
        Planet[] planets = new Planet[size];
        double radius = in.readDouble();
        int i = 0;
        while(i < size) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            Planet newP = new Planet(xP, yP, xV, yV, m, img);
            planets[i] = newP;
            i += 1;
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);
        StdDraw.setScale(-radius, radius);

        StdDraw.enableDoubleBuffering();

        Double[] xForce = new Double[planets.length];
        Double[] yForce = new Double[planets.length];
        double time = 0;

        while (time != T) {
            for (int i = 0; i < planets.length; i++) {
                xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForce[i], yForce[i]);
            }

            /* Draw background*/
            StdDraw.clear();
            String background = "images/starfield.jpg";
            StdDraw.picture(0, 0, background);

            /* draw all the planets */
            for (Planet item: planets) {
                item.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            time = time + dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }

}