package com.example.projeto.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ArtigoBDHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BDArtigos", TABLE_NAME = "Artigos";
    private static final String ID="id", REFERENCIA="referencia", PRECO="preco", STOCK="stock", DESCRICAO="descricao", CATEGORIA="categoria_id", IVA="ivas_id", IMAGEM="imagem";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public ArtigoBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY, "+
                REFERENCIA + " TEXT NOT NULL, "+
                DESCRICAO + " TEXT NOT NULL, "+
                PRECO + " FLOAT NOT NULL, "+
                STOCK + " INTEGER NOT NULL, "+
                CATEGORIA + " INTEGER NOT NULL, "+
                IVA + " INTEGER NOT NULL, "+
                IMAGEM + " TEXT NOT NULL) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //region CRUD
    public Artigo adicionarArtigoBD(Artigo artigo){
        ContentValues values=new ContentValues();
        values.put(ID, artigo.getId());
        values.put(REFERENCIA, artigo.getReferencia());
        values.put(DESCRICAO, artigo.getDescricao());
        values.put(PRECO, artigo.getPreco());
        values.put(STOCK, artigo.getStock());
        values.put(CATEGORIA, artigo.getIdCategoria());
        values.put(IVA, artigo.getIdIva());
        values.put(IMAGEM, artigo.getImagem());
        db.insert(TABLE_NAME, null, values);
        return artigo;
    }

    public boolean editarArtigoBD(Artigo artigo){
        ContentValues values=new ContentValues();
        values.put(ID, artigo.getId());
        values.put(REFERENCIA, artigo.getReferencia());
        values.put(DESCRICAO, artigo.getDescricao());
        values.put(PRECO, artigo.getPreco());
        values.put(STOCK, artigo.getStock());
        values.put(CATEGORIA, artigo.getIdCategoria());
        values.put(IVA, artigo.getIdIva());
        values.put(IMAGEM, artigo.getImagem());
        int nLinhasEditadas= db.update(TABLE_NAME, values, ID+"=?",new String[] {artigo.getId()+""});
        return nLinhasEditadas>0;
    }

    public boolean removerArtigoBD(int idArtigo){
        int nLinhasDel= db.delete(TABLE_NAME, ID+"=?",new String[] {idArtigo+""});
        return nLinhasDel>0;
    }

    public void removerAllArtigosBD(){
        db.delete(TABLE_NAME, null, null);
    }

    public ArrayList<Artigo> getAllArtigosBD(){
        ArrayList<Artigo> artigos = new ArrayList<>();

        Cursor cursor=db.query(TABLE_NAME, new String[]{ID, REFERENCIA, PRECO, STOCK, CATEGORIA, IVA, DESCRICAO, IMAGEM},
                null,null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                Artigo auxArtigo = new Artigo(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2),
                        cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7
                ));
                artigos.add(auxArtigo);
            }while (cursor.moveToNext());
            cursor.close();

        }
        return artigos;
    }

    //end region


}
