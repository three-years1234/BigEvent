package com.example.bigevent.demos.web.utils;

public class ThreadLocalUtil {
    public static final ThreadLocal threadLocal = new ThreadLocal<>();

    public static final void set(Object obj){
        threadLocal.set(obj);
    }

    public static  final <T> T get(){
        return (T) threadLocal.get();
    }

    public static final void remove(){
        threadLocal.remove();
    }
}
