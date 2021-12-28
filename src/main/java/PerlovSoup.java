public class PerlovSoup extends Food{
    protected int temp = 50;

    public PerlovSoup() {

    }

    @Override
    public void action() {
        System.out.println("");
    }

    public int getTemp() {
        return temp;
    }

    public void coolDown(){
        if(temp >= 35) {
            temp -= 10;
        }
    }

}
