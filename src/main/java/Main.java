import apps.CLI;

/**
 * Main class with main method
 */

public class Main {
    /**
     *Run app from properties
     */
    public static void main(String[] args){
        String runningMode = System.getProperty("mode");
        if(runningMode == null || runningMode.equalsIgnoreCase("cli")){
            new Thread(new CLI()).start();
        }
    }
}
