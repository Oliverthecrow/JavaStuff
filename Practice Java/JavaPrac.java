
abstract class Animal {
    private int j = 5;

    Animal() {
        j = 5;
        System.out.println(j);
    }

    Animal(int t) {
        j = t;
        System.out.println(j);
    }

    public void makeSound(String sound) {
        System.out.println(sound);
    }

    public void df() {
        System.out.println("sdf");
    }
}

class cow extends Animal {
    private int b;

    cow() {
        System.out.println("2");
    }

    cow(int t) {
        super(t);
        b = t;
    }
}

class brown extends cow {
    brown() {
        System.out.println("3");
    }

    public void makeSound(String sound) {
        df();
        System.out.println(sound);
    }
}

public class JavaPrac {
    public static void main(String[] args) {
        cow g = new brown();
        g.makeSound("awd");

        System.out.println(doSomething("yuhhhh"));

        System.out.println("fit".compareTo("hat"));
    }

    public static String doSomething(String str) {
        if (str.length() < 1) {
            return "";
        } else {
            return str.substring(0, 1) + doSomething(str.substring(1));
        }
    }
}