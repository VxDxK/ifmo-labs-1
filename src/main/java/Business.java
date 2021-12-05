import java.util.Objects;

public abstract class Business {
    protected Businessman owner;
    protected Status status;
    protected int profit;

    public Business(Businessman owner) {
        this.owner = owner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        System.out.println("Владелец изменил статус бизнеса");
        this.status = status;
    }



    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "Бизнес" +
                "владелец: " + owner +
                ", статус: " + status +
                ", доход" + profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Business)) return false;
        Business business = (Business) o;
        return profit == business.profit && owner.equals(business.owner) && status == business.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, status, profit);
    }
}
