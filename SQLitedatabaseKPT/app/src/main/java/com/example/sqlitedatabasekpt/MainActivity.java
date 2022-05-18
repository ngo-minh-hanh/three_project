package com.example.sqlitedatabasekpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView liv_ten;
    ArrayList<CongViec> congViecListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        liv_ten = findViewById(R.id.liv_ten);
        congViecListView = new ArrayList<CongViec>();

        database = new Database(this, "ghichu.sql", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");

        getDataCV();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_Them){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_them_cong_viec);

            EditText edt_Them_cong_viec = dialog.findViewById(R.id.edt_Them_cong_viec);
            Button btn_Them = dialog.findViewById(R.id.btn_Them);
            Button btn_Xoa = dialog.findViewById(R.id.btn_Xoa);

            btn_Xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            btn_Them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tenCV = edt_Them_cong_viec.getText().toString();
                    if (tenCV.equals("")){
                        Toast.makeText(MainActivity.this, "Vui lòng nhập thêm tên công việc", Toast.LENGTH_SHORT).show();
                    }else {
                        database.QueryData("INSERT INTO CongViec VALUES(null, '"+tenCV+"')");
                        dialog.dismiss();
                        getDataCV();
                    }
                }
            });
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getDataCV(){
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        while (dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            CongViec congViec = new CongViec(id, ten);
            congViecListView.add(congViec);
        }
        AdapterListview adapterListview = new AdapterListview(this, congViecListView);
        liv_ten.setAdapter(adapterListview);
        adapterListview.notifyDataSetChanged();
    }
    public void dialog_Insert_Cv(String tenCV, int id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_insert_cv);
        dialog.show();
        EditText edt_dialog_insert_vc = dialog.findViewById(R.id.edt_dialog_insert_vc);
        Button btn_dialog_xacnhan_cv = dialog.findViewById(R.id.btn_dialog_xacnhan_cv);
        Button btn_dialog_huy_cv = dialog.findViewById(R.id.btn_dialog_huy_cv);

        edt_dialog_insert_vc.setText(tenCV);

        btn_dialog_xacnhan_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    String tenMoi = edt_dialog_insert_vc.getText().toString();
                    database.QueryData("UPDATE CongViec SET TenCV = '"+tenMoi+"'WHERE Id = '"+id+"'");
                    getDataCV();
                    dialog.dismiss();
                }
            }
        });

        btn_dialog_huy_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void dialog_Delete_CV(int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có chắc chắn muốn xóa công việc này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+id+"'");
                getDataCV();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialogXoa.show();
    }
}