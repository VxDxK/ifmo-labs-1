package core.pojos;

import core.ValidationException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Pojo class of coordinates
 */

@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    /**
     * More than -117 not null
     */
    private Float x;
    /**
     * Not null
     */
    private Double y;

    private Coordinates(Float x, Double y) {
        this.x = x;
        this.y = y;
    }

    private Coordinates(){
        this(0f, 0d);
    }

    public Float getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    /**
     * Builder of coordinates
     */
    public  static class CoordinatesBuilder{
        private Float x;
        private Double y;

        public CoordinatesBuilder(Coordinates coordinates) {
            this.x = coordinates.getX();
            this.y = coordinates.getY();
        }

        public CoordinatesBuilder() {

        }

        public boolean setX(Float x){
            try {
                validateX(x);
            }catch (ValidationException e){
                return false;
            }
            this.x = x;
            return true;
        }

        public void validateX(Float x) throws ValidationException{
            if(x == null){
                throw new ValidationException("X cannot be null");
            }else if(x <= -117){
                throw new ValidationException("X should be greater then -117");
            }
        }

        public boolean setY(Double y){
            try {
                validateY(y);
            }catch (ValidationException e){
                return false;
            }
            this.y = y;
            return true;
        }

        public void validateY(Double y) throws ValidationException {
            if(y == null){
                throw new ValidationException("X cannot be null");
            }
        }

        public Coordinates build() throws ValidationException {
            validateX(x);
            validateY(y);
            return new Coordinates(x, y);
        }

    }

}
