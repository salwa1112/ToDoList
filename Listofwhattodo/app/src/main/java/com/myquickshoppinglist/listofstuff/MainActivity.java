package com.myquickshoppinglist.listofstuff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText item;
    Button add;
    ListView listview;
    ArrayList<String> itemlist = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = findViewById(R.id.editText);
        add = findViewById(R.id.button);
        listview = findViewById(R.id.list);

        //add previously added data by the user
        itemlist = Filehelper.readdata(this); //this method will read data and send it to the array list if there is any data
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemlist);
        listview.setAdapter(arrayAdapter);

        //add data
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = item.getText().toString();
                itemlist.add(itemName);
                item.setText("");
                Filehelper.writeData(itemlist, getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
            }
        });

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to delete this item from list?");
        alert.setCancelable(false);
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int i) {
            dialog.cancel();
        }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                itemlist.remove(position);
                arrayAdapter.notifyDataSetChanged();
                Filehelper.writeData(itemlist, getApplicationContext());
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        }
        });

        }
}
