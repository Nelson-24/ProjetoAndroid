package com.example.projeto.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.projeto.R;
import com.example.projeto.modelo.Artigo;

import java.util.ArrayList;

public class ListaArtigosAdaptador extends BaseAdapter{
    private Context context;
    private LayoutInflater inflator;
    private ArrayList<Artigo> artigos;

    public ListaArtigosAdaptador(Context context, ArrayList<Artigo> artigos){
        this.context = context;
        this.artigos = artigos;
    }


    @Override
    public int getCount() {
        return artigos.size();
    }

    @Override
    public Object getItem(int i) {
        return artigos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return artigos.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (inflator==null)
            inflator= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view==null)
            view=inflator.inflate(R.layout.item_lista_artigo, null);

        ViewHolderLista viewHolder= (ViewHolderLista) view.getTag();
        if (viewHolder==null){
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }
        viewHolder.update(artigos.get(position));

        return view;
    }

    private class ViewHolderLista{
        private TextView tvReferencia, tvPreco, tvDescricao;
        private ImageView foto;
        public ViewHolderLista(View view) {
            tvReferencia =view.findViewById(R.id.tvReferencia);
            tvPreco =view.findViewById(R.id.tvPreco);
            tvDescricao =view.findViewById(R.id.tvDescricao);
        }

        public void update(Artigo artigo){
            tvReferencia.setText(artigo.getReferencia()+"");
            tvPreco.setText(artigo.getPreco()+"");
            tvDescricao.setText(artigo.getDescricao());


//            Glide.with(context)
//                    .load(artigo.getFoto())
//                    .placeholder(R.drawable.logocrm)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(foto);

        }
    }
}
