package org.vdk;


public class Main {

    public static void main(String[] args) {
        var ln = new Ln(10);

        var value = Math.E;
        System.out.println(ln.apply(value));
        System.out.println(Math.log(value));
    }
}