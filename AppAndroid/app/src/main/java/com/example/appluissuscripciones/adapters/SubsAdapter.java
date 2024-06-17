package com.example.appluissuscripciones.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appluissuscripciones.R;
import com.example.appluissuscripciones.entidades.Suscripcion;
import com.example.appluissuscripciones.entidades.SuscripcionesService;

import java.util.List;

public class SubsAdapter extends ArrayAdapter<Suscripcion> {
    private List<Suscripcion> subs;
    public SubsAdapter(Context context, List<Suscripcion> list) {
        super(context,0, list);
        this.subs = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
    View convertView, @NonNull ViewGroup parent)
    {
        return initView(position,convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
    View convertView, @NonNull ViewGroup parent){
        return initView (position, convertView,parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View vistaPersonalizada = inflater.inflate(R.layout.custom_adapter_sub, parent, false);

        Suscripcion sub = getItem(position);

        TextView texto = vistaPersonalizada.findViewById(R.id.texto);
       // TextView nombreSub = vistaPersonalizada.findViewById(R.id.nombreSub);
       // TextView importeSub  = vistaPersonalizada.findViewById(R.id.importeSub);
       // TextView fechaFinSub = vistaPersonalizada.findViewById(R.id.fechaFinSub);
        // String.valueOf

        if (sub != null){
            texto.append(sub.getNombreSuscripcion() + " | " + sub.getImporte() + " | " + sub.getFechaFin());
        }


        return vistaPersonalizada;
    }

}
