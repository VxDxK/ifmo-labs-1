package core.readers;

import core.server.ValidationException;
import core.pojos.Coordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CoordinatesReader extends ElementReader<Coordinates> {

    @Override
    public Coordinates read(BufferedReader reader, OutputStreamWriter writer) throws IOException {
        Coordinates.CoordinatesBuilder builder = new Coordinates.CoordinatesBuilder();
        boolean x_stat = false;
        while (!x_stat){
            writer.write("Enter x: ");
            writer.flush();
            String inp = reader.readLine();
            try{
                if(inp.equals("")){
                    builder.validateX(null);
                    x_stat = builder.setX(null);
                }else{
                    builder.validateX(Float.parseFloat(inp));
                    x_stat = builder.setX(Float.parseFloat(inp));
                }
            } catch (ValidationException e) {
                writer.write(e.getMessage() + '\n');
                writer.flush();
            }catch (NumberFormatException e){
                writer.write("Not a float type was provided\n");
                writer.flush();
            }
        }

        boolean y_stat = false;
        while (!y_stat){
            writer.write("Enter y: ");
            writer.flush();
            String inp = reader.readLine();
            try{
                if(inp.equals("")){
                    builder.validateY(null);
                    y_stat = builder.setY(null);
                }else{
                    builder.validateY(Double.parseDouble(inp));
                    y_stat = builder.setY(Double.parseDouble(inp));
                }
            } catch (ValidationException e) {
                writer.write(e.getMessage() + '\n');
                writer.flush();
            }catch (NumberFormatException e){
                writer.write("Not a double type was provided\n");
                writer.flush();
            }
        }
        Coordinates coordinates;
        try {
            coordinates = builder.build();
        } catch (ValidationException e) {
            throw new IOException("Error in script file: "  + e.getMessage());
        }
        return coordinates;
    }

    @Override
    public Coordinates read(BufferedReader reader) throws IOException {
        Coordinates.CoordinatesBuilder builder = new Coordinates.CoordinatesBuilder();
        String xStr = reader.readLine();
        try{
            builder.validateX(Float.parseFloat(xStr));
            builder.setX(Float.parseFloat(xStr));
        } catch (ValidationException | NumberFormatException e) {
            throw new IOException("Script file error: " + e.getMessage());
        }

        String yStr = reader.readLine();
        try{
            builder.validateY(Double.parseDouble(yStr));
            builder.setY(Double.parseDouble(yStr));
        } catch (ValidationException | NumberFormatException e) {
            throw new IOException("Script file error: " + e.getMessage());
        }

        Coordinates coordinates;
        try {
            coordinates = builder.build();
        } catch (ValidationException e) {
            throw new IOException("Error in script file: " + e.getMessage());
        }
        return coordinates;
    }


}
