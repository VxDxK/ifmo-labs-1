public class Visitor extends Shorty {
    @Override
    public void join(Business business) {
        //Пришел в Балаганчик
    }

    @Override
    public void watch() {
        this.mood = Mood.FUN;
    }

    @Override
    public void throwBall(Shorty shorty, int power) {
        if(numberOfBalls > 0){
            if(System.getProperty("full") != null){
                System.out.println("Зритель кинул мяч");
            }
            shorty.catchBall(power);
            numberOfBalls--;
        }
    }

    @Override
    public void catchBall(int power) {

    }

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public void setNumberOfBalls(int numberOfBalls) {
        this.numberOfBalls = numberOfBalls;
    }
}
