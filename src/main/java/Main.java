public class Main {
    public static void main(String[] args) throws Exception{
        Dunno dunno = new Dunno();
        Kozlik kozlik = new Kozlik();
        Master master = new Master();
        Balaganchik balaganchik = (Balaganchik) master.getBusiness();
        balaganchik.setVisitors(10);
        balaganchik.setTemp(25);

        System.out.println();
        Thread.sleep(1000);

        dunno.move();
        kozlik.move();
        balaganchik.setPunchingBag(kozlik);
        balaganchik.addVisitor(dunno);

        Thread.sleep(1000);
        balaganchik.startScene();
        System.out.println();
        master.pay(kozlik);
        balaganchik.setStatus(Status.CLOSED);
        System.out.println(balaganchik);
    }
}
