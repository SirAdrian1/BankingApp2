package bank;

public class Pos {
    protected static void introduce() {
        System.out.println("number");
    }
    public static void seria() {
        System.out.println("number");
    }
    public  void main(String[] args) {
        Pos.<Object>introduce();
        Pos.<Object>seria();
    }
}