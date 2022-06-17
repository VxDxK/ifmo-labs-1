import apps.Client;
import apps.GUI;
import apps.Server;


/**
 * Main class with main method
 */
public class Main {
    /**
     *Run app from properties
     */
    public static void main(String[] args){
        String runningMode = System.getProperty("mode");
        if(runningMode == null){
            System.out.println("No such application, specify running mode");
        }else{
            if(runningMode.equalsIgnoreCase("client")){
                Thread serv = new Thread(new Client());
                serv.setName("Client thread");
                serv.start();
            }else if(runningMode.equalsIgnoreCase("server")){
                Thread client = new Thread(new Server());
                client.setName("Server thread");
                client.start();
            }else if(runningMode.equalsIgnoreCase("gui")){
                Thread gui = new Thread(new GUI());
                gui.setName("gui thread");
                gui.start();
            }else{
                System.out.println("No such mode, available: client, server, gui");
            }
        }
    }
}
