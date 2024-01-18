package com.example.projeto.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projeto.R;
import com.example.projeto.modelo.Carrinho;

import java.util.ArrayList;

public class ListaCarrinhosAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Carrinho> carrinhos;

    public ListaCarrinhosAdaptador(Context context, ArrayList<Carrinho> carrinhos) {
        this.context = context;
        this.carrinhos = carrinhos;
    }

    @Override
    public int getCount() {
        return carrinhos.size();
    }

    @Override
    public Object getItem(int position) {
        return carrinhos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return carrinhos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_carrinho, null);

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(carrinhos.get(position));

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvData, tvValorTotal, tvIdUser;

        public ViewHolderLista(View view) {
            tvData = view.findViewById(R.id.tvData);
            tvValorTotal = view.findViewById(R.id.tvValorTotal);
            tvIdUser = view.findViewById(R.id.tvIdUser);
        }

        public void update(Carrinho carrinho) {
            tvData.setText(carrinho.getData());
            tvValorTotal.setText(carrinho.getValorTotal()+"");
            tvIdUser.setText(carrinho.getUsers_id()+"");
        }
    }
}
