/*
this is used to code temorary code to test how things work 
 */

public class Java {
    public static void main(String[] args) {
        javaClass p = new javaClass();
        totalAmount(p);
        p.totalAmount();

        String str = "AP";
        str += "cs" + 1 + 2;
        System.out.println(str);
    }

    public static double calculate(double p) {
        return (p + 1.5);
    }

    public static void totalAmount(javaClass p) {
        System.out.println(p.money + p.tax);
    }
}
