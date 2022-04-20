package core.readers;

import core.server.ValidationException;
import core.pojos.Ticket;
import core.pojos.TicketType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class TicketReader extends ElementReader<Ticket.TicketBuilder> {

    @Override
    public Ticket.TicketBuilder read(BufferedReader reader, OutputStreamWriter writer) throws IOException {
        Ticket.TicketBuilder builder = new Ticket.TicketBuilder();

        boolean name_stat = false;
        while (!name_stat){
            writer.write("Enter name: ");
            writer.flush();
            String inp = reader.readLine();
            try {
                builder.validateName(inp);
                name_stat = builder.setName(inp);
            }catch (ValidationException e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            }
        }
        writer.write("Enter coordinates: \n");
        writer.flush();
        CoordinatesReader coordinatesReader = new CoordinatesReader();
        builder.setCoordinates(coordinatesReader.read(reader, writer));

        boolean price_stat = false;
        while (!price_stat){
            writer.write("Enter price: ");
            writer.flush();
            String inp = reader.readLine();
            try {
                builder.validatePrice(Integer.parseInt(inp));
                price_stat = builder.setPrice(Integer.parseInt(inp));
            }catch (ValidationException e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            }catch (NumberFormatException e){
                writer.write("Not a int type was provided\n");
                writer.flush();
            }
        }

        boolean discount_stat = false;
        while (!discount_stat){
            writer.write("Enter discount: ");
            writer.flush();
            String inp = reader.readLine();
            try {
                builder.validateDiscount(Integer.parseInt(inp));
                discount_stat = builder.setDiscount(Integer.parseInt(inp));
            }catch (ValidationException e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            }catch (NumberFormatException e){
                writer.write("Not a int type was provided\n");
                writer.flush();
            }
        }

        boolean comment_stat = false;
        while (!comment_stat){
            writer.write("Enter comment: ");
            writer.flush();
            String inp = reader.readLine();
            try {
                builder.validateComment(inp);
                comment_stat = builder.setComment(inp);
            } catch (ValidationException e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            }
        }

        boolean type_stat = false;
        while(!type_stat){
            writer.write("Enter ticket type color " + Arrays.toString(TicketType.values()) + ": ");
            writer.flush();
            String inp = reader.readLine();
            if(inp.equals("")){
                type_stat = builder.setType(null);
            }else{
                try {
                    type_stat = builder.setType(TicketType.valueOf(inp));
                } catch (IllegalArgumentException e){
                    writer.write("specified enum type has no constant with the specified name\n");
                    writer.flush();
                }
            }
        }

        PersonReader personReader = new PersonReader();
        writer.write("Wanna enter person y/n: ");
        writer.flush();
        String inp = reader.readLine();
        if(inp.equalsIgnoreCase("y")){
            builder.setPerson(personReader.read(reader, writer));
        }else{
            builder.setPerson(null);
        }


        return builder;
    }

    @Override
    public Ticket.TicketBuilder read(BufferedReader reader) throws IOException {
        Ticket.TicketBuilder builder = new Ticket.TicketBuilder();

        String nameStr = reader.readLine();
        try {
            builder.validateName(nameStr);
            builder.setName(nameStr);
        }catch (ValidationException | NumberFormatException e) {
            throw new IOException("Script file error: " + e.getMessage());
        }


        CoordinatesReader coordinatesReader = new CoordinatesReader();
        builder.setCoordinates(coordinatesReader.read(reader));

        String priceStr = reader.readLine();
        try {
            builder.validatePrice(Integer.parseInt(priceStr));
            builder.setPrice(Integer.parseInt(priceStr));
        }catch (ValidationException | NumberFormatException e) {
            throw new IOException("Script file error: " + e.getMessage());
        }

        String discountStr = reader.readLine();
        try {
            builder.validateDiscount(Integer.parseInt(discountStr));
            builder.setDiscount(Integer.parseInt(discountStr));
        }catch (ValidationException | NumberFormatException e) {
            throw new IOException("Script file error: " + e.getMessage());
        }

        String commentStr = reader.readLine();
        try {
            builder.validateComment(commentStr);
            builder.setComment(commentStr);
        } catch (ValidationException e) {
            throw new IOException("Script file error: " + e.getMessage());
        }

        String typeStr = reader.readLine();
        if(typeStr.equals("")){
            builder.setType(null);
        }else{
            try {
                builder.setType(TicketType.valueOf(typeStr));
            } catch (IllegalArgumentException e){
                throw new IOException("Script file error: " + e.getMessage());
            }
        }

        PersonReader personReader = new PersonReader();
        builder.setPerson(personReader.read(reader));

        return builder;
    }
}
