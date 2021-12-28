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
            if(System.getProperty("full") != null){
                System.out.println("Владелец  продал мяч");
            }
            now.setNumberOfBalls((int)((random() + 1) * 3));
            business.setProfit(business.getProfit() + 1);
        }
    }

    @Override
    public void pay(Shorty shorty) {
        if(shorty instanceof Actor){
            System.out.println("Владелец Балаганчика выплатил деньги актеру");
            Actor actor = (Actor) shorty;
            shorty.setCurrency(getCurrency() + 3);
            actor.earn();
        }
    }

    @Override
    public Business getBusiness() {
        return business;
    }

    @Override
    public void join(Business business) {
        if(business instanceof Balaganchik){
            ((Balaganchik) business).addVisitor(this);
        }
    }

    @Override
    public void throwBall(Shorty shorty, int power) {

    }

    @Override
    public void catchBall(int power) {

    }

    @Override
    public void watch() {
        System.out.println("Хозяин смотрит с наслаждением");
    }

    @Override
    public String toString() {
        return "Хозяин Балаганчика";
    }
}
