public class DrawTriangle {
    public static void drawTrangle(int N) {
        String p = "*";
        for (int x = 1; x <= N; x++) {
            System.out.println(p);
            p = p + "*";
        }
    }
    public static void main(String[] args) {
        drawTrangle(10);
    }

}
