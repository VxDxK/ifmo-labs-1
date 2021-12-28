import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Balaganchik extends Business {
    private int temp;
    private Shorty punchingBag;
    private final List<Shorty> visitors = new ArrayList<>();
    public Balaganchik(Businessman owner) {
        super(owner);
    }

    public void startScene(){
        double increase_power = 0.0;
        while (visitors.size() != 2){
            for(int i = 0; i < visitors.size(); i++){
                Shorty v = visitors.get(i);
                v.watch();
                if(v.equals(owner)){
                    System.out.println("Хозяин ухмыляется");
                }
                if(v instanceof Visitor){
                    Visitor now = (Visitor) v;
                    now.throwBall(punchingBag, (int)(increase_power));
                    if(increase_power + 0.3 < 3){
                        increase_power += 0.1;
                    }
                    if(now.getNumberOfBalls() <= 0){
                        if(System.getProperty("full") != null){
                            System.out.println("Зритель ушел, температура упала");
                        }
                        visitors.remove(v);
                        temp--;
                    }
                }
            }
        }
        System.out.println("Все зрители разошлись");
    }

    public void setPunchingBag(Shorty punchingBag) {
        this.punchingBag = punchingBag;
    }

    public Shorty getPunchingBag() {
        return punchingBag;
    }

    public void setVisitors(int count) {
        for(int i = 0; i < count; i++){
            Visitor now = new Visitor();
            owner.sell(now);
            now.join(this);
            visitors.add(now);
        }
    }

    @Override
    public void addVisitor(Shorty shorty) throws ClosedBuildingException{
        if(status == Status.CLOSED){
            throw new ClosedBuildingException("Вы не можете посетить закрытый Балаганчик");
        }
        visitors.add(shorty);
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Статус Балаганчика: " + status + ", Температура в нем: " + temp + "\n" +
                "владелец: " + owner.toString() +
                ", статус: " + status +
                ", доход: " + profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Balaganchik)) return false;
        if (!super.equals(o)) return false;
        Balaganchik that = (Balaganchik) o;
        return temp == that.temp && Objects.equals(punchingBag, that.punchingBag) && Objects.equals(visitors, that.visitors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), temp, punchingBag, visitors);
    }
}
