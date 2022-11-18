package com.vdk.lab2demo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Monadka <T>{
    private final T obj;
    protected final List<Exception> exceptions = new ArrayList<>();
    private Monadka(T obj, List<Exception> exceptions){
        this.obj = obj;
        this.exceptions.addAll(exceptions);
    }

    public Monadka(T obj) {
        this.obj = obj;
    }

    public static <T> Monadka<T> of(T t) {
        return new Monadka<>(t);
    }

    public Monadka<T> check(Predicate<T> p, String error){
        try {
            if(!p.test(obj)){
                exceptions.add(new Exception(error));
            }
        }catch (Exception e){
            exceptions.add(e);
        }
        return this;
    }

    public Monadka<T> checkFinal(Predicate<T> p, String error){
        try {
            if(!p.test(obj)){
                exceptions.add(new Exception(error));
                return new FailToMap<>(exceptions);
            }
        }catch (Exception e){
            exceptions.add(e);
            return new FailToMap<>(exceptions);
        }
        return this;
    }

    public <U> Monadka<U> map(Function<T, U> function){
        try {
            return new Monadka<>(function.apply(obj), exceptions);
        }catch (Exception e){
            return new FailToMap<>(exceptions);
        }
    }

    public <U> Monadka<U> map(Function<T, U> function, String err){
        try {
            return new Monadka<>(function.apply(obj), exceptions);
        }catch (Exception e){
            exceptions.add(new Exception(err));
        }
        return new FailToMap<>(exceptions);
    }


    public Monadka<T> onFailure(Consumer<Exception> consumer){
        RuntimeException runtimeException =  new RuntimeException();
        exceptions.forEach(runtimeException::addSuppressed);
        consumer.accept(runtimeException);
        return this;
    }

    public Monadka<T> onSucces(Consumer<T> consumer){
        consumer.accept(obj);
        return this;
    }

    public T get(){
        if(exceptions.size() == 0){
            return obj;
        }
        RuntimeException runtimeException =  new RuntimeException();
        exceptions.forEach(runtimeException::addSuppressed);
        throw runtimeException;
    }

    private static class FailToMap<T> extends Monadka<T>{
        private FailToMap(List<Exception> e) {
            super(null, e);
        }

        @Override
        public Monadka<T> check(Predicate<T> p, String error) {
            return this;
        }

        @Override
        public Monadka<T> checkFinal(Predicate<T> p, String error) {
            return this;
        }

        @Override
        public <U> Monadka<U> map(Function<T, U> function) {
            return new FailToMap<>(super.exceptions);
        }

        @Override
        public <U> Monadka<U> map(Function<T, U> function, String err) {
            return new FailToMap<>(super.exceptions);
        }

        @Override
        public Monadka<T> onSucces(Consumer<T> consumer) {
            return this;
        }

        @Override
        public Monadka<T> onFailure(Consumer<Exception> consumer) {
            RuntimeException runtimeException =  new RuntimeException();
            exceptions.forEach(runtimeException::addSuppressed);
            consumer.accept(runtimeException);
            return this;
        }

        @Override
        public T get() {
            RuntimeException runtimeException =  new RuntimeException();
            exceptions.forEach(runtimeException::addSuppressed);
            throw runtimeException;
        }
    }

}
