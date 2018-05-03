package com.sime.itanortegaortega.wordbook;

/**
 * Created by itanortegaortega on 25/04/18.
 */

public class Palabra {
    private int i;
    private String nombre_en;
    private String nombre_es;
    private String url;

    public Palabra(){
        
    }

    public Palabra(int i, String nombre_en, String nombre_es, String url) {
        this.i = i;
        this.nombre_en = nombre_en;
        this.nombre_es = nombre_es;
        this.url = url;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getNombre_en() {
        return nombre_en;
    }

    public void setNombre_en(String nombre_en) {
        this.nombre_en = nombre_en;
    }

    public String getNombre_es() {
        return nombre_es;
    }

    public void setNombre_es(String nombre_es) {
        this.nombre_es = nombre_es;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
