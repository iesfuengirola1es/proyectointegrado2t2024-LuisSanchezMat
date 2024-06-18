package com.example.appluissuscripciones.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appluissuscripciones.R;
import com.example.appluissuscripciones.activities.EditSubActivity;
import com.example.appluissuscripciones.entidades.Suscripcion;

import java.util.List;

public class SubsAdapter extends ArrayAdapter<Suscripcion> {

    private Context mContext;
    private List<Suscripcion> mSuscripciones;

    public SubsAdapter(@NonNull Context context, @NonNull List<Suscripcion> suscripciones) {
        super(context, 0, suscripciones);
        mContext = context;
        mSuscripciones = suscripciones;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.custom_adapter_sub, parent, false);
        }

        final Suscripcion currentSuscripcion = mSuscripciones.get(position);

        TextView textView = listItem.findViewById(R.id.texto);
        textView.setText(currentSuscripcion.getNombreSuscripcion() + " | " +
                currentSuscripcion.getImporte() + "€ | " +
                currentSuscripcion.getFechaFin());

        // Configurar onClickListener para el elemento de la lista
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir EditSubActivity y pasar los datos de la suscripción
                Intent intent = new Intent(mContext, EditSubActivity.class);
                intent.putExtra("id_suscripcion", currentSuscripcion.getIdSuscripcion());
                mContext.startActivity(intent);
            }
        });

        return listItem;
    }
}
