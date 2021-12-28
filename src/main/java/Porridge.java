public class Porridge extends Food{
    public int nubs = 10;
    @Override
    public void action() {
        System.out.println("Разбил комочки");
        nubs /= 2;
    }

}
