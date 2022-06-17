package core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocalizedHelp implements Serializable {
    Map<String, String> localeStringMap = new HashMap<>();
    public String getHelp(Locale loc){
        if(localeStringMap.containsKey(loc.getLanguage())){
            return localeStringMap.get(loc.getLanguage());
        }
        return localeStringMap.getOrDefault(Locale.ENGLISH.getLanguage(), "");
    }

    public LocalizedHelp addHelp(Locale locale, String help){
        localeStringMap.put(locale.getLanguage(), help);
        return this;
    }

    @Override
    public String toString() {
        return "LocalizedHelp{" +
                "localeStringMap=" + localeStringMap +
                '}';
    }
}
