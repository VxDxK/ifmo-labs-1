public class Main {
    public static void main(String[] args) throws Exception{
        Dunno dunno = new Dunno();
        Kozlik kozlik = new Kozlik();
        Master master = new Master();
        Balaganchik balaganchik = (Balaganchik) master.getBusiness();
        balaganchik.addVisitor(master);
        balaganchik.setVisitors(10);
        balaganchik.setTemp(25);

        System.out.println();
        Thread.sleep(1000);
        dunno.join(balaganchik);
        kozlik.join(balaganchik);
        balaganchik.startScene();
        Thread.sleep(1000);

        System.out.println();
        master.pay(kozlik);
        balaganchik.setStatus(Status.CLOSED);
        System.out.println(balaganchik);

        Thread.sleep(1000);
        System.out.println();

        Cook cook = new Cook();
        Cafe cafe = (Cafe) cook.getBusiness();
        dunno.join(cafe);
        kozlik.join(cafe);
        cafe.scene(dunno, kozlik);

    }
}
