public class MainClass {
    public int getLocalNumber() {
        int number = 14;
        System.out.println(number);
        return number;
    }

    private int class_number = 20;

    public int getClassNumber() {
        return class_number;
    }

    private String class_string = "hey, world";

    public String getClassString() {
        return class_string;
    }
}