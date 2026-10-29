enum Status {
    Pending, Running, Success, Failed; // These are Constants, Named Constants, its raw value starts with 0. its raw values have incemented numbers.
}

public class EnumerationDemo {

    public static void main(String[] args) {

        
        System.out.println();
        Status runningStatus = Status.Running;
        System.out.println("runningStatus = " + runningStatus);

        System.out.println("Superclass of Status Enum is "+ runningStatus.getClass().getSuperclass());
        System.out.println("Superclass x2 of Status Enum is "+ runningStatus.getClass().getSuperclass().getSuperclass());

        Status failedStatus = Status.Failed;
        System.out.println("failedStatus = " + failedStatus);

        // Access raw values
        System.out.println("failedStatus rawValue = " + failedStatus.ordinal());
        System.out.println("runningStatus rawValue = " + runningStatus.ordinal());

        // fetch all values of enum (Iterating)
        System.out.println();
        System.out.println("accessing all elements");
        Status allStatus[] = Status.values();
        System.out.println("All Statuses - " + allStatus); // it will print memory address of allStatus array.
        System.out.println("First Element = " + allStatus[0]);

        // Use ForEach (Enchanced for loop)
        System.out.println();
        System.out.println("Iterating all elements of Status");
        for (Status status: allStatus) {
            System.out.println("[" + status.ordinal() + "]: " + status);
        }

        Status status = Status.Pending;

         if(status == Status.Pending) {
            System.out.println("Pending in if condition");
        } else if(status == Status.Running) {
            System.out.println("Running in if condition");
        } else if(status == Status.Success) {
            System.out.println("Success in if condition");
        } else if(status == Status.Failed) {
            System.out.println("Failed in if condition");
        } else {
            System.out.println("Something went wrong...");
        }

        switch (status) {
            case Pending:
                System.out.println("It is pending in Switch case");
                break;
            case Running:
                System.out.println("It is running in Switch case");
                break;
            case Success:
                System.out.println("It is Success in Switch case");
                break;

            default:
                System.out.println("It is running in Switch case");
                break;
        }
    }

}
