package com.example.projeto.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projeto.R;
import com.example.projeto.modelo.Fatura;

import java.util.ArrayList;

public class ListaFaturasAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fatura> listaFaturas;

    public ListaFaturasAdaptador(Context context, ArrayList<Fatura> listaFaturas) {
        this.context = context;
        this.listaFaturas = listaFaturas;
    }

    @Override
    public int getCount() {
        return listaFaturas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFaturas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaFaturas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_fatura, null);

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(listaFaturas.get(position));

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvData, tvValorTotal, tvEstado, tvIdUser;

        public ViewHolderLista(View view) {
            tvData = view.findViewById(R.id.tvData);
            tvValorTotal = view.findViewById(R.id.tvValorTotal);
            tvEstado = view.findViewById(R.id.tvEstado);
            tvIdUser = view.findViewById(R.id.tvIdUser);
        }

        public void update(Fatura fatura) {
            tvData.setText(fatura.getData());
            tvValorTotal.setText(String.valueOf(fatura.getValorTotal()));
            tvEstado.setText(fatura.getEstado());
            tvIdUser.setText(String.valueOf(fatura.getUsers_id()));
        }
    }
}
