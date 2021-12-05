public class Kozlik extends Shorty implements Worker {
    @Override
    public void watch() {
        System.out.println("Ждет мячик в лицо");
    }

    @Override
    public void catchBall() {
        System.out.println("В козлика попали мячом");
    }

    public void earn(){
        System.out.println("Козлик заработал денег: " + getCurrency());
    }

    @Override
    public void move() throws InterruptedException {
        System.out.println("Козлик пришел в балаганчик.");
        Thread.sleep(1000);
        System.out.println("Козлик встал на место актера Балаганчика, начал ловить мячи лицом.");
    }

    @Override
    public String toString() {
        return "Козлик" +
                "мячей: " + numberOfBalls +
                ", настроение: " + mood +
                ", денег: " + currency;
    }
}
