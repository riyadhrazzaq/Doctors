package com.example.achal.doctors;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

public class window extends AppCompatActivity {

    Button b1,b2,b3;
    TextView name,qa,exp,chamber,loc,con;

    public static String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        Intent intent  = getIntent();
        final String[] values= intent.getStringArrayExtra("values");

        name = (TextView) findViewById(R.id.nameTV);
        loc = (TextView) findViewById(R.id.cityTV);
        con = (TextView) findViewById(R.id.conTV);
        exp = (TextView) findViewById(R.id.expTV);
        qa = (TextView) findViewById(R.id.qaTV);
        chamber = (TextView) findViewById(R.id.chamberTV);

        name.setText(values[1]);
        qa.setText("Qualifications: "+values[2]);
        chamber.setText("Chamber: "+values[4]);
        exp.setText("Field of Expertise: "+values[3]);
        loc.setText("Location: "+values[5]);
        con.setText("Contact: "+values[6]);


        b1 = (Button) findViewById(R.id.cancel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        b2 = (Button) findViewById(R.id.call);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+values[6]));
                startActivity(intent);
            }
        });
        b3 = (Button) findViewById(R.id.gmap);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:23.8103,90.4125?q="+values[4]);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }
}
