public class Visitor extends Shorty {
    @Override
    public void watch() {
        this.mood = Mood.FUN;
    }

    @Override
    public void throwBall() {
        if(numberOfBalls > 0){
            System.out.println("Зритель кинул мяч");
            numberOfBalls--;
        }
    }

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public void setNumberOfBalls(int numberOfBalls) {
        this.numberOfBalls = numberOfBalls;
    }
}
