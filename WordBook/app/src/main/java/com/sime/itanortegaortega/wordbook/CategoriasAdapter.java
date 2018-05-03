package com.sime.itanortegaortega.wordbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sime.itanortegaortega.wordbook.Categoria;
import com.sime.itanortegaortega.wordbook.R;

import java.util.ArrayList;

/**
 * Created by itanortegaortega on 24/04/18.
 */

public class CategoriasAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Categoria> categorias;

    public CategoriasAdapter(Context context, ArrayList<Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Categoria getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categorias.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cardview_categoria, parent, false);
        }

        TextView Cv_Categoria_en = (TextView) convertView.findViewById(R.id.Cv_Categoria_en);
        TextView Cv_Categoria_es = (TextView) convertView.findViewById(R.id.Cv_Categoria_es);
        ImageView Cv_Imagen_categoria = (ImageView) convertView.findViewById(R.id.Cv_Imagen_categoria);
        //TextView Txt_estado = (TextView) convertView.findViewById(R.id.Txt_estado);

        final Categoria item = getItem(position);
        Cv_Categoria_en.setText(item.getNombre_en());
        Cv_Categoria_es.setText(item.getNombre_es());
        CAFData data = CAFData.dataWithContentsOfFile(item.getUrl());

        if(data != null){
            Bitmap bitmap = data.toImage();
            if(bitmap != null){
                Cv_Imagen_categoria.setImageBitmap(bitmap);
            }
        }
        return convertView;
    }
}
