package com.example.projeto.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projeto.R;
import com.example.projeto.modelo.Iva;

import java.util.ArrayList;

public class ListaIvasAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Iva> listaIvas;

    public ListaIvasAdaptador(Context context, ArrayList<Iva> listaIvas) {
        this.context = context;
        this.listaIvas = listaIvas;
    }

    @Override
    public int getCount() {
        return listaIvas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaIvas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaIvas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_iva, null);

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(listaIvas.get(position));

        return convertView;
    }


    private class ViewHolderLista {
        private TextView tvDescricao, tvPercentagem;

        public ViewHolderLista(View view) {
            tvDescricao = view.findViewById(R.id.tvDescricao);
            tvPercentagem = view.findViewById(R.id.tvPercentagem);
        }

        public void update(Iva iva) {
            tvDescricao.setText(iva.getDescricao());
            tvPercentagem.setText(String.valueOf(iva.getPercentagem()));
        }
    }
}
