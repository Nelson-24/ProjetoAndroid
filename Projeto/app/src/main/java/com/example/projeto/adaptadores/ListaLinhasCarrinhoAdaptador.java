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

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_linha_carrinho, null);

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(linhasCarrinho.get(position));

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvDescricao, tvQuantidade, tvPreco;

        public ViewHolderLista(View view) {
            tvDescricao = view.findViewById(R.id.tvDescricao);
            tvQuantidade = view.findViewById(R.id.tvQuantidade);
            tvPreco = view.findViewById(R.id.tvPreco);
        }

        public void update(LinhaCarrinho linhaCarrinho) {
            tvDescricao.setText(linhaCarrinho.getArtigos_id());
            tvQuantidade.setText(linhaCarrinho.getQuantidade()+"");
            tvPreco.setText(linhaCarrinho.getValor()+"");
        }
    }
}
