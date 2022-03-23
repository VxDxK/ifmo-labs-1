package core.pojos;

import core.ValidationException;
import util.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Ticket class
 * Main class of all 5th lab
 */
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket implements Comparable<Ticket>{
    /**
     * Positive and auto-gen
     */
    private final int id;
    /**
     * Not null and not empty
     */
    private String name;
    /**
     * Not null
     */
    private Coordinates coordinates;
    /**
     * Auto input
     */
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private final LocalDateTime creationDate;
    /**
     * Positive
     */
    private int price;
    /**
     * 0-100 range
     */
    private int discount;
    /**
     * Not null and not empty
     */
    private String comment;
    private TicketType type;
    private Person person;

    private Ticket(int id, String name, Coordinates coordinates, LocalDateTime creationDate, int price, int discount, String comment, TicketType type, Person person) {
        this.id = id; // generate id
        this.name = name; // check not empty
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.discount = discount;
        this.comment = comment;
        this.type = type;
        this.person = person;
    }

    private Ticket() {
        this(0, null, null, null, 0, 0, null, null, null);
    }

    @Override
    public int compareTo(Ticket ticket) {
        return (price * (1 -(discount/100))) - (ticket.getPrice() * (1 - (ticket.getDiscount()/100)));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public String getComment() {
        return comment;
    }

    public TicketType getType() {
        return type;
    }

    public Person getPerson() {
        return person;
    }



    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", discount=" + discount +
                ", comment='" + comment + '\'' +
                ", type=" + type +
                ", person=" + person +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && price == ticket.price && discount == ticket.discount && Objects.equals(name, ticket.name) && Objects.equals(coordinates, ticket.coordinates) && Objects.equals(creationDate, ticket.creationDate) && Objects.equals(comment, ticket.comment) && type == ticket.type && Objects.equals(person, ticket.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, discount, comment, type, person);
    }

    /**
     * Builder to make Ticket
     */
    public static class TicketBuilder {
        private int id;
        private String name;
        private Coordinates coordinates;
        private LocalDateTime creationDate;
        private int price;
        private int discount;
        private String comment;
        private TicketType type;
        private Person person;

        public TicketBuilder(Ticket ticket) {
            id = ticket.getId();
            name = ticket.getName();
            coordinates = ticket.getCoordinates();
            creationDate = ticket.getCreationDate();
            price = ticket.price;
            discount = ticket.discount;
            comment = ticket.getComment();
            type = ticket.getType();
            person = ticket.getPerson();
        }

        public TicketBuilder() {
        }

        public void validateId(int id) throws ValidationException{
            if(id <= 0){
                throw new ValidationException("ID should be greater then 0");
            }
        }

        public boolean setId(int id) {
            try {
                validateId(id);
            }catch (ValidationException e){
                return false;
            }
            this.id = id;
            return true;
        }

        public void validateName(String name) throws ValidationException{
            if(name == null){
                throw new ValidationException("Name cannot be null");
            }else if(name.equals("")){
                throw new ValidationException("Name cannot be empty");
            }
        }

        public boolean setName(String name){
            try {
                validateName(name);
            }catch (ValidationException e){
                return false;
            }
            this.name = name;
            return true;
        }

        public void validateCoordinates(Coordinates coordinates) throws ValidationException {
            if(coordinates == null){
                throw new ValidationException("Coordinates is not null");
            }
        }

        public boolean setCoordinates(Coordinates coordinates) {
            try {
                validateCoordinates(coordinates);
            } catch (ValidationException e) {
                return false;
            }
            this.coordinates = coordinates;
            return true;
        }

        public void validateCreationDate(LocalDateTime creationDate) throws ValidationException {
            if(creationDate == null){
                throw new ValidationException("CreationDate cannot be null");
            }
        }

        public boolean setCreationDate(LocalDateTime creationDate){
            try {
                validateCreationDate(creationDate);
            }catch (ValidationException e){
                return false;
            }
            this.creationDate = creationDate;
            return true;
        }

        public void validatePrice(int price) throws ValidationException{
            if(price <= 0 || price > 1000000){
                throw new ValidationException("Price should pe positive and less then 1kk");
            }
        }
        public boolean setPrice(int price){
            try {
                validatePrice(price);
            }catch (ValidationException e){
                return false;
            }
            this.price = price;
            return true;
        }

        public void validateDiscount(int discount) throws ValidationException {
            if(discount < 0 || discount > 100){
                throw new ValidationException("Discount should be in range 0 to 100");
            }
        }

        public boolean setDiscount(int discount) {
            try {
                validateDiscount(discount);
            }catch (ValidationException e){
                return false;
            }
            this.discount = discount;
            return true;
        }

        public void validateComment(String comment) throws ValidationException {
            if(comment == null){
                throw new ValidationException("Comment cannot be null");
            }else if(comment.equals("")){
                throw new ValidationException("Comment cannot be empty");
            }
        }

        public boolean setComment(String comment) {
            try {
                validateComment(comment);
            }catch (ValidationException e){
                return false;
            }
            this.comment = comment;
            return true;
        }

        public boolean setType(TicketType type) {
            this.type = type;
            return true;
        }

        public boolean setPerson(Person person) {
            this.person = person;
            return true;
        }

        public Ticket build() throws ValidationException{
            validateId(id);
            validateName(name);
            validateCoordinates(coordinates);
            validateCreationDate(creationDate);
            validatePrice(price);
            validateDiscount(discount);
            validateComment(comment);

            return new Ticket(id, name, coordinates, creationDate, price, discount, comment, type, person);
        }


        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public LocalDateTime getCreationDate() {
            return creationDate;
        }

        public int getPrice() {
            return price;
        }

        public int getDiscount() {
            return discount;
        }

        public String getComment() {
            return comment;
        }

        public TicketType getType() {
            return type;
        }

        public Person getPerson() {
            return person;
        }
    }
}
