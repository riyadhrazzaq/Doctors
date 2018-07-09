package com.example.achal.doctors;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Spinner s1,s2;
    CustomDBHelper dbHelper = new CustomDBHelper(this);
    Cursor res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rone= new Random();
        Random rtwo= new Random();



        String[] name_array = getResources().getStringArray(R.array.name);
        String[] city_array = getResources().getStringArray(R.array.city);
        String[] exp_array = getResources().getStringArray(R.array.exp);

        for (int i = 0;i<50;i++){
            dbHelper.addData(name_array[rone.nextInt(5)],"MBBS",exp_array[rtwo.nextInt(6)],city_array[rtwo.nextInt(8)],"Hospital Name","017");
        }


        s1 = (Spinner) findViewById(R.id.city);
        s2 = (Spinner) findViewById(R.id.exp);

        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_dropdown_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(myAdapter);
        ArrayAdapter<CharSequence> myAnotherAdapter = ArrayAdapter.createFromResource(this,R.array.exp,android.R.layout.simple_spinner_dropdown_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(myAnotherAdapter);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "city selected"+s1.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "expertise selected"+s2.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> tempList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lv);
        res = dbHelper.getAllByCursor();


        res.moveToFirst();
        while (res.moveToNext()) {
            tempList.add(res.getString(1));
            ListAdapter myRealAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tempList);
            lv.setAdapter(myRealAdapter);

        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                window.id = (String) lv.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(), "Item Clicked " + res.getCount(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), window.class));
            }
        });


    }
}
