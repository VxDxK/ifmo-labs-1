public class Pie extends Food{
    protected int pieces = 8;

    @Override
    public void action() {
        System.out.println("Пирог порезали");
    }

    public int getPieces() {
        return pieces;
    }

    public void getPiece() {
        pieces--;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }
}
