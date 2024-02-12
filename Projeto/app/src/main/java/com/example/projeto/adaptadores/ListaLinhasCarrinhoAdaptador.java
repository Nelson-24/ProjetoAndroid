package com.example.projeto.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projeto.R;
import com.example.projeto.modelo.LinhaCarrinho;

import java.util.ArrayList;

public class ListaLinhasCarrinhoAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<LinhaCarrinho> linhasCarrinho;

    public ListaLinhasCarrinhoAdaptador(Context context, ArrayList<LinhaCarrinho> linhasCarrinho) {
        this.context = context;
        this.linhasCarrinho = linhasCarrinho;
    }

    @Override
    public int getCount() {
        return linhasCarrinho.size();
    }

    @Override
    public Object getItem(int position) {
        return linhasCarrinho.get(position);
    }

    @Override
    public long getItemId(int position) {
        return linhasCarrinho.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolderLista viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lista_linha_carrinho, null);
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderLista) convertView.getTag();
        }

        viewHolder.update(linhasCarrinho.get(position));

        return convertView;
    }


    static class ViewHolderLista {
        TextView tvIdArtigo, tvQuantidade, tvPreco;

        ViewHolderLista(View view) {
            tvIdArtigo = view.findViewById(R.id.tvIdArtigo);
            tvQuantidade = view.findViewById(R.id.tvQuantidade);
            tvPreco = view.findViewById(R.id.tvPreco);
        }

        void update(LinhaCarrinho linhaCarrinho) {
            tvIdArtigo.setText(String.valueOf(linhaCarrinho.getArtigos_id()));
            tvQuantidade.setText(String.valueOf(linhaCarrinho.getQuantidade()));
            tvPreco.setText(String.valueOf(linhaCarrinho.getValor()));
        }
    }
}
