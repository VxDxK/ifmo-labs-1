public class Cook extends Shorty implements Businessman{
    Business cooker = new Cafe(this);

    @Override
    public void sell(Shorty shorty) {
        if(shorty.getCurrency() > 0){
            shorty.setCurrency(shorty.getCurrency() - 1);
        }
    }

    @Override
    public void pay(Shorty shorty) {
        shorty.setCurrency(shorty.getCurrency() + 3);
    }

    @Override
    public Business getBusiness() {
        return cooker;
    }

    @Override
    public void join(Business business) throws ClosedBuildingException {
        business.addVisitor(this);
    }

    @Override
    public void throwBall(Shorty shorty, int power) {
        System.out.println("У меня нет мячика, чтобы кинуть");
    }

    @Override
    public void catchBall(int power) {
        System.out.println("Получил мячиком по лицу");
    }

    @Override
    public void watch() {
        System.out.println("Смотрит с кухни, как едят коротышки");
    }
}
