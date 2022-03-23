package core.readers;

import core.ValidationException;
import core.pojos.Color;
import core.pojos.Country;
import core.pojos.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class PersonReader extends ElementReader<Person> {
    @Override
    public Person read(BufferedReader reader, OutputStreamWriter writer) throws IOException {
        Person.PersonBuilder builder = new Person.PersonBuilder();

        boolean weight_stat = false;
        while(!weight_stat){
            writer.write("Enter weight: ");
            writer.flush();
            String inp = reader.readLine();
            try {
                builder.validateWeight(Float.parseFloat(inp));
                weight_stat = builder.setWeight(Float.parseFloat(inp));
            } catch (ValidationException e) {
                writer.write(e.getMessage() + '\n');
                writer.flush();
            }catch (NumberFormatException e){
                writer.write("Not a float type was provided\n");
                writer.flush();
            }
        }

        boolean eyeColor_stat = false;
        while(!eyeColor_stat){
            writer.write("Enter eye color " + Arrays.toString(Color.values()) + ": ");
            writer.flush();
            String inp = reader.readLine();
            if(inp.equals("")){
                eyeColor_stat = builder.setEyeColor(null);
            }else{
                try {
                    eyeColor_stat = builder.setEyeColor(Color.valueOf(inp));
                } catch (IllegalArgumentException e){
                    writer.write("specified enum type has no constant with the specified name\n");
                    writer.flush();
                }
            }
        }

        boolean hairColor_stat = false;
        while(!hairColor_stat){
            writer.write("Enter hair color " + Arrays.toString(Color.values()) + ": ");
            writer.flush();
            String inp = reader.readLine();
            if(inp.equals("")){
                hairColor_stat = builder.setHairColor(null);
            }else{
                try {
                    hairColor_stat = builder.setHairColor(Color.valueOf(inp));
                }catch (IllegalArgumentException e){
                    writer.write("specified enum type has no constant with the specified name\n");
                    writer.flush();
                }
            }
        }

        boolean nationality_stat = false;
        while(!nationality_stat){
            writer.write("Enter nationality " + Arrays.toString(Country.values()) + ": ");
            writer.flush();
            String inp = reader.readLine();
            if(inp.equals("")){
                writer.write("Nationality is not null\n");
                writer.flush();
            }else{
                try {
                    nationality_stat = builder.setNationality(Country.valueOf(inp));
                }catch (IllegalArgumentException e){
                    writer.write("specified enum type has no constant with the specified name\n");
                    writer.flush();
                }
            }

        }

        Person person;
        try {
           person = builder.build();
        } catch (ValidationException e) {
            throw new IOException("Error in script file: "  + e.getMessage());
        }
        return person;
    }

    @Override
    public Person read(BufferedReader reader) throws IOException {
        Person.PersonBuilder builder = new Person.PersonBuilder();

        String weightStr = reader.readLine();
        try {
            builder.validateWeight(Float.parseFloat(weightStr));
            builder.setWeight(Float.parseFloat(weightStr));
        } catch (ValidationException | NumberFormatException e) {
            throw new IOException("Script file error: " + e.getMessage());
        }

        String eyeColorStr = reader.readLine();
        if(eyeColorStr.equals("")){
            builder.setEyeColor(null);
        }else{
            try {
                builder.setEyeColor(Color.valueOf(eyeColorStr));
            } catch (IllegalArgumentException e){
                throw new IOException("Script file error: " + e.getMessage());
            }
        }


        String hairColorStr = reader.readLine();
        if(hairColorStr.equals("")){
            builder.setHairColor(null);
        }else{
            try {
                builder.setHairColor(Color.valueOf(hairColorStr));
            }catch (IllegalArgumentException e){
                throw new IOException("Script file error: " + e.getMessage());
            }
        }

        String countryInp = reader.readLine();
        try {
            builder.validateNationality(Country.valueOf(countryInp));
            builder.setNationality(Country.valueOf(countryInp));
        }catch (ValidationException | IllegalArgumentException e) {
            throw new IOException("Script file error: " + e.getMessage());
        }

        Person person;
        try {
            person = builder.build();
        } catch (ValidationException e) {
            throw new IOException("Error in script file: "  + e.getMessage());
        }
        return person;
    }
}
