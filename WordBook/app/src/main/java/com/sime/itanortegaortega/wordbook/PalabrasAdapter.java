package com.sime.itanortegaortega.wordbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sime.itanortegaortega.wordbook.Palabra;
import com.sime.itanortegaortega.wordbook.R;

import java.util.List;

/**
 * Created by itanortegaortega on 25/04/18.
 */

public class PalabrasAdapter  extends RecyclerView.Adapter <PalabrasAdapter.PalabraViewHolder>{

    public List<Palabra> palabras;
    public Context context;

    public PalabrasAdapter(List<Palabra> palabras, Context context) {
        this.palabras = palabras;
        this.context = context;
    }

    @Override
    public PalabraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_palabra, parent, false);
        PalabrasAdapter.PalabraViewHolder palabraViewHolder = new PalabrasAdapter.PalabraViewHolder(cardview);
        return palabraViewHolder;
    }

    @Override
    public void onBindViewHolder(PalabraViewHolder holder, int position) {
        holder.Cv_Palabra_en.setText(palabras.get(position).getNombre_en());
        holder.Cv_Palabra_es.setText(palabras.get(position).getNombre_es());
    }

    @Override
    public int getItemCount() {
        return palabras.size();
    }

    public class PalabraViewHolder extends RecyclerView.ViewHolder{
        TextView Cv_Palabra_en;
        TextView Cv_Palabra_es;
        public PalabraViewHolder(View item) {
            super(item);
            Cv_Palabra_en = (TextView) item.findViewById(R.id.Cv_Palabra_en);
            Cv_Palabra_es = (TextView) item.findViewById(R.id.Cv_Palabra_es);
        }
    }

}
