package core.server.database;

import util.SQLAdapter;

public class EnumAdapter<T extends Enum<T>> implements SQLAdapter<T, String> {
    private final Class<T> enumClass;

    public EnumAdapter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String transForm(T val) {
        if(val == null){
            return "null";
        }
        return val.toString();
    }

    @Override
    public T parse(String val) {
        try{
            return T.valueOf(enumClass, val);
        }catch (Exception e){
            return null;
        }
    }
}
