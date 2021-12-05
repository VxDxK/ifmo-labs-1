import java.util.Objects;

public abstract class Shorty {
    protected int numberOfBalls;
    protected Mood mood = Mood.NEUTRAL;
    protected int currency;

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Mood getMood() {
        return mood;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getCurrency() {
        return currency;
    }

    public void move() throws InterruptedException {

    }

    public void throwBall(){

    }

    public void catchBall(){

    }

    public abstract void watch();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shorty)) return false;
        Shorty shorty = (Shorty) o;
        return numberOfBalls == shorty.numberOfBalls && currency == shorty.currency && mood == shorty.mood;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfBalls, mood, currency);
    }

    @Override
    public String toString() {
        return "Коротышка" +
                "мячей: " + numberOfBalls +
                ", настроение: " + mood +
                ", денег: " + currency;
    }
}