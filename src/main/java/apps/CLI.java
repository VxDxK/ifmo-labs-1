package apps;

import core.CommandManager;
import core.EncapsulatedCollection;
import core.commands.*;

import javax.xml.bind.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * CLI app for 5th lab
 */
public class CLI implements Runnable{
    EncapsulatedCollection collection = new EncapsulatedCollection();
    @Override
    public void run() {
        String fileName = System.getenv("dataFile");
        if(fileName == null){
            System.out.println("No one env variable was set");
            System.exit(-1);
        }
        Path file = Paths.get(fileName);

        if(!Files.isRegularFile(file)){
            System.out.println(file.getFileName() + " not a regular file");
            System.exit(-1);
        }

        if(!Files.isReadable(file) || !Files.isWritable(file)){
            System.out.println("File should be readable and writable");
            System.exit(-1);
        }

        try {
            JAXBContext context = JAXBContext.newInstance(EncapsulatedCollection.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            if(Files.size(file) == 0){
                collection = new EncapsulatedCollection();
            }else{
                collection = (EncapsulatedCollection) unmarshaller.unmarshal(new InputStreamReader(Files.newInputStream(file)));
                if(!collection.validate()){
                    System.out.println("Something wrong with xml model");
                    System.exit(-1);
                }
            }
        } catch (IOException | JAXBException e) {
            System.out.println("Error on data file parsing");
            System.exit(-1);
        }

        try (CommandManager manager = new CommandManager(System.in, System.out, collection, file);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            manager
                    .addCommand(HelpCommand::new)
                    .addCommand(InfoCommand::new)
                    .addCommand(ShowCommand::new)
                    .addCommand(AddCommand::new)
                    .addCommand(UpdateCommand::new)
                    .addCommand(RemoveById::new)
                    .addCommand(ClearCommand::new)
                    .addCommand(SaveCommand::new)
                    .addCommand(ExecScriptCommand::new)
                    .addCommand(ExitCommand::new)
                    .addCommand(RemoveHeadCommand::new)
                    .addCommand(AddIfMaxCommand::new)
                    .addCommand(AddIfMinCommand::new)
                    .addCommand(RemoveAnyByPriceCommand::new)
                    .addCommand(MaxByDiscountCommand::new)
                    .addCommand(PrintFieldAscendingComment::new);
            while (!Thread.currentThread().isInterrupted()){
                try {
                    System.out.println("Enter command: ");
                    manager.handle(br.readLine());
                }catch (IOException e){
                    System.out.println("Error IO operation in runtime: " + e.getMessage());
                }

            }
        } catch (IOException e) {
            System.out.println("Error on IO on startup: " + e.getMessage());
        }
    }
}
