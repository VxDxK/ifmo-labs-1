package core.pojos;

import core.server.ValidationException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * Person class
 */
public final class Person implements Serializable {
    /**
     * Positive
     */
    private float weight;
    private Color eyeColor;
    private Color hairColor;
    /**
     * Not null
     */
    private Country nationality;

    private Person(float weight, Color eyeColor, Color hairColor, Country nationality) {
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    private Person(){
        this(0f, null, null, null);
    }

    public float getWeight() {
        return weight;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Float.compare(person.weight, weight) == 0 && eyeColor == person.eyeColor && hairColor == person.hairColor && nationality == person.nationality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, eyeColor, hairColor, nationality);
    }

    @Override
    public String toString() {
        return "Person{" +
                "weight=" + weight +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", nationality=" + nationality +
                '}';
    }

    /**
     * Builder to make person
     */
    public static class PersonBuilder implements Serializable{
        private float weight;
        private Color eyeColor;
        private Color hairColor;
        private Country nationality;

        public PersonBuilder(Person person) {
            this.weight = person.getWeight();
            this.eyeColor = person.getEyeColor();
            this.hairColor = person.getHairColor();
            this.nationality = person.getNationality();
        }

        public PersonBuilder(){

        }

        public void validateWeight(float weight) throws ValidationException{
            if(weight <= 0 || weight > 300){
                throw new ValidationException("Weight should be positive and less then 300");
            }
        }



        public boolean setWeight(float weight) {
            try {
                validateWeight(weight);
            }catch (ValidationException e){
                return false;
            }
            this.weight = weight;
            return true;
        }

        public boolean setEyeColor(Color eyeColor) {
            this.eyeColor = eyeColor;
            return true;
        }

        public boolean setHairColor(Color hairColor) {
            this.hairColor = hairColor;
            return true;
        }

        public void validateNationality(Country nationality) throws ValidationException{
            if(nationality == null){
                throw new ValidationException("Nationality is not nullable");
            }
        }
        public boolean setNationality(Country nationality){
            try {
                validateNationality(nationality);
            }catch (ValidationException e){
                return false;
            }
            this.nationality = nationality;
            return true;
        }

        public Person build() throws ValidationException {
            validateWeight(weight);
            validateNationality(nationality);
            return new Person(weight, eyeColor, hairColor, nationality);
        }

        @Override
        public String toString() {
            return "PersonBuilder{" +
                    "weight=" + weight +
                    ", eyeColor=" + eyeColor +
                    ", hairColor=" + hairColor +
                    ", nationality=" + nationality +
                    '}';
        }
    }
}
