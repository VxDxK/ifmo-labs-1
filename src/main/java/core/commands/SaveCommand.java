package core.commands;

import core.CommandManager;
import core.EncapsulatedCollection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing save to file command
 */
public class SaveCommand implements Command{
    private final CommandManager manager;
    public SaveCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        try {
            Path file = manager.getFile();
            if(context.isInteractive()){
                Writer writer = manager.getWriter();
                if(!Files.exists(file)){
                    writer.write("File is not exists");
                    writer.flush();
                    return;
                }
                if(!Files.isRegularFile(file)){
                    writer.write("File is not regular file");
                    writer.flush();
                    return;
                }
                if(!Files.isWritable(file)){
                    writer.write("File is not writable");
                    writer.flush();
                    return;
                }

            }else{
                throw new IOException("This file is very bad");
            }
            PrintWriter fileWriter = new PrintWriter(Files.newOutputStream(file));

            JAXBContext jaxbContext = JAXBContext.newInstance(EncapsulatedCollection.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(manager.getCollection(), fileWriter);

        } catch (IOException | JAXBException e) {
            System.out.println("Error on data file operations");
        }
    }

    @Override
    public String getHelp() {
        return "Save collection to file";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("save");
    }
}
