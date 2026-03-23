package pen;

public class Main {

    public static void main(String[] args) {

        System.out.println("===== Ball Pen Demo =====");
        Pen ballPen = new BallPen("Reynolds", 10);
        ballPen.start();
        ballPen.write("Hello");
        ballPen.write("World"); // ink runs out here (10 chars used)
        ballPen.write("more text"); // should show 'out of ink'
        ballPen.close();

        System.out.println();

        System.out.println("===== Gel Pen Demo =====");
        GelPen gelPen = new GelPen("Pilot", 5);
        gelPen.start();
        gelPen.write("Hi"); // uses 2 units
        gelPen.write("Okay"); // uses 3 units, ink = 0, goes EMPTY
        gelPen.start(); // cannot start – empty
        gelPen.refill(50); // refill 50 units
        gelPen.start(); // now works
        gelPen.write("Refilled!");
        gelPen.close();

        System.out.println();

        System.out.println("===== Fountain Pen Demo =====");
        FountainPen fountainPen = new FountainPen("Parker", 80);
        fountainPen.start();
        fountainPen.write("Low Level Design");
        fountainPen.close();
        fountainPen.write("tries to write while closed"); // should warn
        fountainPen.refill(20); // top it up
        System.out.println("Ink after refill: " + fountainPen.getInkLevel() + "%");
    }
}
