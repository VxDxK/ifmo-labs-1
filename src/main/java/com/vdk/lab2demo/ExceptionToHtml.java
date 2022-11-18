package com.vdk.lab2demo;

import java.util.Arrays;

public class ExceptionToHtml {
    public ExceptionToHtml() {

    }

    public String produce(Exception e){
        StringBuilder builder = new StringBuilder();
        Arrays.stream(e.getSuppressed()).map(Throwable::getMessage).forEach(x -> {
            builder.append("<div>");
            builder.append(x);
            builder.append("</div><br>");
        });
        return builder.toString();
    }
}
