package tools;

public class Car {
    public String publicName;
    protected String protectedSpeed;
    String version;
    private String privateMfgDate;


    public void publicFunc() {
        System.out.println("public func");
    }

    void func() {
        System.out.println("func");
    }

    protected void protectedFunc() {
        System.out.println("protected func");
    }

    private void privateFunc() {
        System.out.println("private func");
    }
}
