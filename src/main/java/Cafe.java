import java.util.ArrayList;
import java.util.List;

public class Cafe extends Business{
    protected int products = 10;
    List<Shorty> shorties = new ArrayList<>();

    public void scene(Dunno dunno, Kozlik kozlik){
        owner.sell(kozlik);
        List<Food> food = new ArrayList<>();
        CookerPan pan = new CookerPan();
        CookerBakery bakery = new CookerBakery();
        CookerBowler bowler = new CookerBowler();
        try {
            food.add(pan.cookFood());
            food.add(bakery.cookFood());
            food.add(bowler.cookFood());
        }catch (ExpiredFoodException e){
            e.printStackTrace();
        }
        for(Food a : food){
            kozlik.eat(a);
            dunno.eat(a);
        }
    }

    public int getProducts() {
        return products;
    }

    public Cafe(Businessman owner) {
        super(owner);
    }

    @Override
    public void addVisitor(Shorty shorty) throws ClosedBuildingException {
        if(status == Status.CLOSED){
            throw new ClosedBuildingException("Вы не можете посетить закрытую столовую");
        }
        shorties.add(shorty);
    }
    //Плита
    public abstract class Cooker<T extends Food>{
        public abstract T cookFood() throws ExpiredFoodException;
    }

    public class CookerPan extends Cooker<PerlovSoup>{
        @Override
        public PerlovSoup cookFood() throws ExpiredFoodException {
            products -= 2;
            if(Math.random() <= 0.02){
                throw new ExpiredFoodException("Каша подгорела");
            }
            return new PerlovSoup();
        }
    }

    public class CookerBakery extends Cooker<Pie>{
        @Override
        public Pie cookFood() throws ExpiredFoodException {
            products--;
            if(Math.random() <= 0.03){
                throw new ExpiredFoodException("Пирог сгорел");
            }
            return new Pie();
        }
    }

    public class CookerBowler extends Cooker<Porridge>{
        @Override
        public Porridge cookFood() throws ExpiredFoodException {
            products -= 3;
            if(Math.random() <= 0.15){
                throw new ExpiredFoodException("Каша подгорела");
            }
            return new Porridge();
        }
    }

    //Хлебница
    public static class BreadBascket{
        public static Food getBread(){
            return new Food() {
                private boolean withered = false;
                @Override
                public void action() {
                    System.out.println("Хлеб засох");
                    withered = true;
                }

                public boolean isWithered() {
                    return withered;
                }
            };
        }
    }
}
