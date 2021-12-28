public class Dunno extends Shorty{
    @Override
    public void watch() {
        this.mood = Mood.ANGRY;
        toAggro();
    }

    public void toAggro(){
        if(Math.random() < 0.5){
            System.out.println("Незнайка злится на зрителей");
        }else{
            System.out.println("Незнайка злится на владельца балаганчика");
        }
    }

    @Override
    public void join(Business business) {
        System.out.println("Незнайка пришел в " + business.getClass().getSimpleName());
        business.addVisitor(this);
    }

    @Override
    public void eat(Food food) {
        setMood(Mood.RELAXED);
        System.out.println("Незнайка ест " + food.getClass().getSimpleName());
    }

    @Override
    public void throwBall(Shorty shorty, int power) {

    }

    @Override
    public void catchBall(int power) {

    }
}
