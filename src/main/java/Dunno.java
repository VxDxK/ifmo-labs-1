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
    public void move() {
        System.out.println("Незнайка пришел в Балаганчик");
    }
}
