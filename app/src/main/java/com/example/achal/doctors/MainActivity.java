package com.example.achal.doctors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.preference.PreferenceManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public String loc, exp;

    public ArrayAdapter<String> itemsAdapter;
    TextView t1;
    ListView lv;
    Spinner s1, s2;
    CustomDBHelper dbHelper = new CustomDBHelper(this);
    Cursor res;
    ArrayList<String> tempList = new ArrayList<>();
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        t1 = (TextView) findViewById(R.id.countTV);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {

            /*------------------------------------------
                    Database setup on installation
            --------------------------------------------*/
            Random rone = new Random();
            Random rtwo = new Random();


            String[] name_array = getResources().getStringArray(R.array.name);
            String[] city_array = getResources().getStringArray(R.array.city);
            String[] exp_array = getResources().getStringArray(R.array.exp);

            for (int i = 0; i < 500; i++) {
                dbHelper.addData(name_array[rone.nextInt(5)], "MBBS", exp_array[rtwo.nextInt(6)], "Hospital Name", city_array[rtwo.nextInt(8)], "017");
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        /*------------------------------------------
                    Spinner setup on create
        --------------------------------------------*/

        s1 = (Spinner) findViewById(R.id.city);
        s2 = (Spinner) findViewById(R.id.exp);

        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_dropdown_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(myAdapter);
        ArrayAdapter<CharSequence> myAnotherAdapter = ArrayAdapter.createFromResource(this, R.array.exp, android.R.layout.simple_spinner_dropdown_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(myAnotherAdapter);


        /*------------------------------------------
                    ListView update Button onClick
            --------------------------------------------*/

        b1 = (Button) findViewById(R.id.search);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loc = s1.getSelectedItem().toString();
                exp = s2.getSelectedItem().toString();
                if (!res.isClosed()) res.close();
                if (loc.equalsIgnoreCase("All") || exp.equalsIgnoreCase("All")) res = dbHelper.getAllByCursor();
                else res = dbHelper.getBySpinner(loc, exp);
                tempList.clear();
                t1.setText(String.valueOf(res.getCount()));
                res.moveToFirst();
                for (int c = 0; c < res.getCount(); c++) {
                    tempList.add(res.getString(0));
                    res.moveToNext();
                }
              //  itemsAdapter.addAll(tempList);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        /*------------------------------------------
                    Default ListView ShowAll
         --------------------------------------------*/

        lv = (ListView) findViewById(R.id.lv);
        res = dbHelper.getAllByCursor();
        res.moveToFirst();
        while (res.moveToNext()) {
            tempList.add(res.getString(0));
            itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tempList);
            lv.setAdapter(itemsAdapter);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /*
                window.id = (String) lv.getItemAtPosition(i);
                String id, name, qa, chamber, expertise, location, contact;
                res.moveToPosition(i+1);
                id = res.getString(0);

                Toast.makeText(getApplicationContext(), "in cursor " + id, Toast.LENGTH_SHORT).show();
                */
                startActivity(new Intent(getApplicationContext(), window.class));
            }
        });


    }
}
