public class OffByN implements CharacterComparator {
    private int offNum;

    public OffByN(int x) {
        this.offNum = x;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return (Math.abs((int) x - (int) y) == offNum);
    }
}
