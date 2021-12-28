public class Kozlik extends Shorty implements Actor {
    private final Mood[] moods = {Mood.TENSE, Mood.ALERT, Mood.DAZED, Mood.SADNESS};
    @Override
    public void watch() {
        System.out.println("Ждет мячик в лицо");
    }

    @Override
    public void setMood(Mood mood) {
        System.out.println("Козлик: " + mood.getTranslation());
    }

    @Override
    public void eat(Food food) {
        System.out.println("Козлик ест " + food.getClass().getSimpleName());
        setMood(Mood.FUN);
    }

    @Override
    public void catchBall(int power) {
        System.out.print("В козлика попали мячом ");
        setMood(moods[power]);
    }

    public void earn(){
        System.out.println("Козлик заработал денег: " + getCurrency());
    }

    @Override
    public void join(Business business) throws ClosedBuildingException {
        if(business instanceof Balaganchik){
            ((Balaganchik) business).setPunchingBag(this);
            System.out.println("Козлик пришел в " + business.getClass().getSimpleName());
            setMood(Mood.TENSE);
            System.out.println("Козлик встал на сцену, просунул голову за занавеску");
            setMood(Mood.ALERT);
        }else if(business instanceof Cafe){
            System.out.println("Козлик пришел в Cafe");
            business.addVisitor(this);
        }
    }


    @Override
    public void throwBall(Shorty shorty, int power) {
        System.out.println("У козлика нет мячика");
    }

    @Override
    public String toString() {
        return "Козлик" +
                "мячей: " + numberOfBalls +
                ", настроение: " + mood +
                ", денег: " + currency;
    }
}
