package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Stopper extends Thread{
    Logger logger = Logger.getLogger(Stopper.class.getName());
    private final Thread toStop;
    private final BufferedReader reader;

    public Stopper(Thread toStop, InputStream stream) {
        this.toStop = toStop;
        this.reader = new BufferedReader(new InputStreamReader(stream));
    }

    @Override
    public void run() {
        while (true){
            try {
                String str = reader.readLine();
                if(str.equalsIgnoreCase("exit")){
                    reader.close();
                    System.exit(0);
                    break;
                }
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }
    }
}
