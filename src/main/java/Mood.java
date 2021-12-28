public enum Mood {
    RELAXED("расслаблен"), //Расслаблен
    TENSE("напряжен"), //Напряжен
    ALERT("насторожен"), //Насторожен
    DAZED("ошеломлен"), //Ошеломлен
    SADNESS("расстроен"),
    ANGRY("зол"),
    NEUTRAL("нейтрален"),
    FUN("развеселен");

    public final String translate;
    Mood(String translate) {
        this.translate = translate;
    }
    public String getTranslation(){
        return translate;
    }
}
