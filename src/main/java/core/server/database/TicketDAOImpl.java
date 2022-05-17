package core.server.database;

import core.pojos.*;
import core.server.ValidationException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO, AutoCloseable{
    private final ServerDataSource dataSource;
    private final UserDAO userDAO;
    public TicketDAOImpl(ServerDataSource dataSource, UserDAO userDAO) {
        this.dataSource = dataSource;
        this.userDAO = userDAO;
    }

    public ServerDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<TicketWrap> all(){
        List<TicketWrap> ans = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM s338999_tickets")) {
            ResultSet set = statement.executeQuery();
            Ticket.TicketBuilder builder = new Ticket.TicketBuilder();
            while (set.next()){
                builder.setId(set.getInt("id"));
                builder.setName(set.getString("name"));

                Coordinates.CoordinatesBuilder coordinatesBuilder = new Coordinates.CoordinatesBuilder();
                coordinatesBuilder.setX(set.getFloat("x"));
                coordinatesBuilder.setY(set.getDouble("y"));
                builder.setCoordinates(coordinatesBuilder.build());

                builder.setCreationDate(LocalDateTime.parse(set.getString("creationDate")));
                builder.setPrice(set.getInt("price"));
                builder.setDiscount(set.getInt("discount"));
                builder.setComment(set.getString("comment"));
                builder.setType(TicketType.valueOf(set.getString("type")));

                Person.PersonBuilder personBuilder = new Person.PersonBuilder();
                personBuilder.setWeight(set.getFloat("weight"));
                if(set.getString("eyeColor").equalsIgnoreCase("null")){
                    personBuilder.setEyeColor(null);
                }else{
                    personBuilder.setEyeColor(Color.valueOf(set.getString("eyeColor")));
                }

                if(set.getString("hairColor").equalsIgnoreCase("null")){
                    personBuilder.setHairColor(null);
                }else{
                    personBuilder.setHairColor(Color.valueOf(set.getString("hairColor")));
                }
                personBuilder.setNationality(Country.valueOf(set.getString("nationality")));

                builder.setPerson(personBuilder.build());

                TicketWrap wrap = new TicketWrap();
                wrap.setTicket(builder.build());
                wrap.setUser(userDAO.getByID(set.getInt("ownerID")));
                ans.add(wrap);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ValidationException e){
            e.printStackTrace();
        }
        return ans;
    }

    public List<TicketWrap> allOfOwner(int ownerID){
        List<TicketWrap> ans = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM s338999_tickets WHERE ownerid=?")) {
            statement.setInt(1, ownerID);
            ResultSet set = statement.executeQuery();
            Ticket.TicketBuilder builder = new Ticket.TicketBuilder();
            while (set.next()){
                builder.setId(set.getInt("id"));
                builder.setName(set.getString("name"));

                Coordinates.CoordinatesBuilder coordinatesBuilder = new Coordinates.CoordinatesBuilder();
                coordinatesBuilder.setX(set.getFloat("x"));
                coordinatesBuilder.setY(set.getDouble("y"));
                builder.setCoordinates(coordinatesBuilder.build());

                builder.setCreationDate(LocalDateTime.parse(set.getString("creationDate")));
                builder.setPrice(set.getInt("price"));
                builder.setDiscount(set.getInt("discount"));
                builder.setComment(set.getString("comment"));
                builder.setType(TicketType.valueOf(set.getString("type")));

                Person.PersonBuilder personBuilder = new Person.PersonBuilder();
                personBuilder.setWeight(set.getFloat("weight"));

                EnumAdapter<Color> colorAdapter = new EnumAdapter<>(Color.class);
                personBuilder.setEyeColor(colorAdapter.parse(set.getString("eyeColor")));
                personBuilder.setHairColor(colorAdapter.parse(set.getString("hairColor")));

//                if(set.getString("eyeColor").equalsIgnoreCase("null")){
//                    personBuilder.setEyeColor(null);
//                }else{
//                    personBuilder.setEyeColor(Color.valueOf(set.getString("eyeColor")));
//                }
//
//                if(set.getString("hairColor").equalsIgnoreCase("null")){
//                    personBuilder.setHairColor(null);
//                }else{
//                    personBuilder.setHairColor(Color.valueOf(set.getString("hairColor")));
//                }

                personBuilder.setNationality(Country.valueOf(set.getString("nationality")));

                builder.setPerson(personBuilder.build());
                TicketWrap wrap = new TicketWrap();
                wrap.setTicket(builder.build());
                wrap.setUser(userDAO.getByID(set.getInt("ownerID")));
                ans.add(wrap);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ValidationException e){
            e.printStackTrace();
        }
        return ans;
    }

    @Override
    public boolean add(TicketWrap element) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO s338999_tickets (id, name, x, y, creationdate, price, discount, comment, type, weight, eyecolor, haircolor, nationality, ownerid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setInt(1, element.getTicket().getId());
            statement.setString(2, element.getTicket().getName());
            statement.setFloat(3, element.getTicket().getCoordinates().getX());
            statement.setDouble(4, element.getTicket().getCoordinates().getY());
            statement.setString(5, element.getTicket().getCreationDate().toString());
            statement.setInt(6, element.getTicket().getPrice());
            statement.setInt(7, element.getTicket().getDiscount());
            statement.setString(8, element.getTicket().getComment());
            statement.setString(9, element.getTicket().getType().toString());
            statement.setFloat(10, element.getTicket().getPerson().getWeight());
            EnumAdapter<Color> colorAdapter = new EnumAdapter<>(Color.class);
            statement.setString(11, colorAdapter.transForm(element.getTicket().getPerson().getEyeColor()));
            statement.setString(12, colorAdapter.transForm(element.getTicket().getPerson().getHairColor()));
            statement.setString(13, element.getTicket().getPerson().getNationality().toString());
            statement.setInt(14, userDAO.getIDByLogin(element.getUser().getLogin()));

//            if(element.getTicket().getPerson().getEyeColor() == null){
//                statement.setString(11, "null");
//            }else{
//                statement.setString(11, element.getTicket().getPerson().getEyeColor().toString());
//            }

//            if(element.getTicket().getPerson().getHairColor() == null){
//                statement.setString(12, "null");
//            }else{
//                statement.setString(12, element.getTicket().getPerson().getHairColor().toString());
//            }


            return statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TicketWrap getByID(int id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM s338999_tickets WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            Ticket.TicketBuilder builder = new Ticket.TicketBuilder();
            if(set.next()){
                builder.setId(set.getInt("id"));
                builder.setName(set.getString("name"));

                Coordinates.CoordinatesBuilder coordinatesBuilder = new Coordinates.CoordinatesBuilder();
                coordinatesBuilder.setX(set.getFloat("x"));
                coordinatesBuilder.setY(set.getDouble("y"));
                builder.setCoordinates(coordinatesBuilder.build());

                builder.setCreationDate(LocalDateTime.parse(set.getString("creationDate")));
                builder.setPrice(set.getInt("price"));
                builder.setDiscount(set.getInt("discount"));
                builder.setComment(set.getString("comment"));
                builder.setType(TicketType.valueOf(set.getString("type")));

                Person.PersonBuilder personBuilder = new Person.PersonBuilder();
                personBuilder.setWeight(set.getFloat("weight"));

                EnumAdapter<Color> colorAdapter = new EnumAdapter<>(Color.class);
                personBuilder.setEyeColor(colorAdapter.parse(set.getString("eyeColor")));
                personBuilder.setHairColor(colorAdapter.parse(set.getString("hairColor")));
//                if(set.getString("eyeColor").equalsIgnoreCase("null")){
//                    personBuilder.setEyeColor(null);
//                }else{
//                    personBuilder.setEyeColor(Color.valueOf(set.getString("eyeColor")));
//                }

//                if(set.getString("hairColor").equalsIgnoreCase("null")){
//                    personBuilder.setHairColor(null);
//                }else{
//                    personBuilder.setHairColor(Color.valueOf(set.getString("hairColor")));
//                }
                personBuilder.setNationality(Country.valueOf(set.getString("nationality")));

                builder.setPerson(personBuilder.build());
                TicketWrap ticketWrap = new TicketWrap();
                ticketWrap.setTicket(builder.build());
                ticketWrap.setUser(userDAO.getByID(set.getInt("ownerID")));

                return ticketWrap;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ValidationException e){
            deleteByID(id);
        }
        return null;
    }

    @Override
    public void update(TicketWrap element) {
        throw new UnsupportedOperationException("I will do it later");
    }

    @Override
    public void deleteByID(int id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM s338999_tickets WHERE id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(TicketWrap element) {
        deleteByID(element.getTicket().getId());
    }

    @Override
    public void deleteAll() {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute("DROP table s338999_tickets");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createSchema();
    }

    @Override
    public boolean createSchema() {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.execute("CREATE TABLE IF NOT EXISTS s338999_tickets(\n" +
                    "    id INT NOT NULL PRIMARY KEY,\n" +
                    "    name TEXT,\n" +
                    "    x float4,\n" +
                    "    y float8,\n" +
                    "    creationDate TEXT,\n" +
                    "    price INT,\n" +
                    "    discount INT,\n" +
                    "    comment TEXT,\n" +
                    "    type TEXT,\n" +
                    "    weight float4,\n" +
                    "    eyeColor TEXT,\n" +
                    "    hairColor TEXT,\n" +
                    "    nationality TEXT,\n" +
                    "    ownerID INT\n" +
                    ")");
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close(){
        dataSource.close();
    }
}
