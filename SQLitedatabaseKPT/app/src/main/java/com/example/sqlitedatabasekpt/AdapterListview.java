package com.example.sqlitedatabasekpt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterListview extends BaseAdapter {

    private MainActivity context;
    private ArrayList<CongViec> congViecList;


    public AdapterListview(MainActivity context, ArrayList<CongViec> congViecList) {

        this.context = context;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {

        return 10;
    }

    @Override
    public Object getItem(int i) {

        return congViecList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.custom_listview, null);
        TextView txt_Ghichu = view.findViewById(R.id.txt_Ghichu);
        ImageView img_insert = view.findViewById(R.id.img_insert);
        ImageView img_delete = view.findViewById(R.id.img_delete);

        txt_Ghichu.setText(congViecList.get(i).getTenCV());

        img_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialog_Insert_Cv(congViecList.get(i).getTenCV(), congViecList.get(i).getIdCV());
            }
        });

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            context.dialog_Delete_CV(congViecList.get(i).getIdCV());
            }
        });
        return view;
    }

}
