public abstract class Food {
    protected boolean isDied = false;

    public Food() {}

    public abstract void action();
    public boolean isDied() {
        return isDied;
    }

}
