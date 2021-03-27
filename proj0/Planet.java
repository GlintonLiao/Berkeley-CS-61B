public class Planet {

   public double xxPos;
   public double yyPos;
   public double xxVel;
   public double yyVel;
   public double mass;
   public String imgFileName;
   public final double G = 0.0000000000667;

   public Planet(double xP, double yP, double xV, double yV, double m, String img) {
       this.xxPos = xP;
       this.yyPos = yP;
       this.xxVel = xV;
       this.yyVel = yV;
       this.mass = m;
       this.imgFileName = img;
   }

   public Planet(Planet p) {
       this.xxPos = p.xxPos;
       this.yyPos = p.yyPos;
       this.xxVel = p.xxVel;
       this.yyVel = p.yyVel;
       this.mass = p.mass;
       this.imgFileName = p.imgFileName;
   }

   public double calcDistance(Planet p) {
       double dx = this.xxPos - p.xxPos;
       double dy = this.yyPos - p.yyPos;
       double sqdistance = dy * dy + dx * dx;
       double distance = Math.sqrt(sqdistance);
       return distance;
   }

   public double calcForceExertedBy(Planet given) {
       double distance = this.calcDistance(given);
       double force = (this.mass * given.mass * G) / (distance * distance);
       return force;
   }

   public double calcForceExertedByX(Planet given) {
       double dx = given.xxPos - this.xxPos;
       double forceX = this.calcForceExertedBy(given) * dx / this.calcDistance(given);
       return forceX;
   }

    public double calcForceExertedByY(Planet given) {
        double dy = given.yyPos - this.yyPos;
        double forceY= this.calcForceExertedBy(given) * dy / this.calcDistance(given);
        return forceY;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
       double netForceX = 0;
       for (int i = 0; i<allPlanets.length; i++) {
           if (allPlanets[i].equals(this) != true) {
               netForceX += calcForceExertedByX(allPlanets[i]);
           }
       }
       return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double netForceY = 0;
        for (int i = 0; i<allPlanets.length; i++) {
            if (allPlanets[i].equals(this) != true) {
                netForceY += calcForceExertedByY(allPlanets[i]);
            }
        }
        return netForceY;
    }

    public void update(double dt, double fX, double fY) {
        double netaX = fX / this.mass;
        double netaY = fY / this.mass;
        this.xxVel = this.xxVel + dt * netaX;
        this.yyVel = this.yyVel + dt * netaY;
        this.xxPos = this.xxPos + dt * xxVel;
        this.yyPos = this.yyPos + dt * yyVel;
    }

    public void draw() {
       StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }

}
