package com.example.projeto.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projeto.R;
import com.example.projeto.modelo.User;

import java.util.ArrayList;

public class ListaUsersAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<User> listaUsers;

    public ListaUsersAdaptador(Context context, ArrayList<User> listaUsers) {
        this.context = context;
        this.listaUsers = listaUsers;
    }

    @Override
    public int getCount() {
        return listaUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return listaUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaUsers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_user, null);

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(listaUsers.get(position));

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvUser, tvEmail, tvNif;

        public ViewHolderLista(View view) {
            tvUser = view.findViewById(R.id.tvUser);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvNif = view.findViewById(R.id.tvNif);
        }

        public void update(User user) {
            tvUser.setText(user.getUsername());
            tvEmail.setText(user.getEmail());
            tvNif.setText(String.valueOf(user.getNif()));
        }
    }
}
