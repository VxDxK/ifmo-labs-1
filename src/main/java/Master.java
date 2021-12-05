import static java.lang.Math.random;

public class Master extends Shorty implements Businessman{
    protected Business business;

    public Master() {
        business = new Balaganchik(this);
        business.setStatus(Status.OPENED);
    }

    @Override
    public void sell(Shorty shorty) {
        if(shorty instanceof Visitor){
            Visitor now = (Visitor) shorty;
            System.out.println("Владелец  продал мяч");
            now.setNumberOfBalls((int)((random() + 1) * 3));
            business.setProfit(business.getProfit() + 1);
        }
    }

    @Override
    public void pay(Shorty shorty) {
        if(shorty instanceof Worker){
            System.out.println("Владелец Балаганчика выплатил деньги актеру");
            Worker worker = (Worker) shorty;
            shorty.setCurrency(getCurrency() + 3);
            worker.earn();
        }
    }

    @Override
    public Business getBusiness() {
        return business;
    }

    @Override
    public String toString() {
        return "Хозяин Балаганчика";
    }
}
