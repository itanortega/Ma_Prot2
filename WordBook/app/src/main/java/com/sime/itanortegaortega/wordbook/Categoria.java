package com.sime.itanortegaortega.wordbook;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.sime.itanortegaortega.wordbook.R;

/**
 * Created by itanortegaortega on 23/04/18.
 */

public class Categoria {
    private int id;
    private String nombre_en;
    private String nombre_es;
    private String estado;
    private String url;

    public Categoria() {

    }

    public Categoria(int id, String nombre_en, String nombre_es, String estado, String url) {
        this.id = id;
        this.nombre_en = nombre_en;
        this.nombre_es = nombre_es;
        this.estado = estado;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
