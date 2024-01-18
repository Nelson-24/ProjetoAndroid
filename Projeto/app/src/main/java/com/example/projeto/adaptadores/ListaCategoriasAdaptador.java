package com.example.projeto.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projeto.R;
import com.example.projeto.modelo.Categoria;

import java.util.ArrayList;

public class ListaCategoriasAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Categoria> listaCategorias;

    public ListaCategoriasAdaptador(Context context, ArrayList<Categoria> listaCategorias) {
        this.context = context;
        this.listaCategorias = listaCategorias;
    }

    @Override
    public int getCount() {
        return listaCategorias.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCategorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaCategorias.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_categoria, null);

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(listaCategorias.get(position));

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvDescricao;

        public ViewHolderLista(View view) {
            tvDescricao = view.findViewById(R.id.tvDescricao);
        }

        public void update(Categoria categoria) {
            tvDescricao.setText(categoria.getDescricao());
        }
    }
}
