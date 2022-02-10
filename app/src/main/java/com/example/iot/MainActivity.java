package com.example.iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {

    private TextView mTextView , txt_pir , txt_luz ;
    private Switch mSwitch;
    private DatabaseReference mdatabase;
    private Spinner a1, a2;
    private ArrayList<String> lista = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.txt_dis);
        txt_pir = (TextView ) findViewById(R.id.txt_pir);
        txt_luz = (TextView) findViewById(R.id.tex_ldr);
        inicioXML();
        agregandoValores();
        darClic();




        mdatabase = FirebaseDatabase.getInstance().getReference();

        a1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()=="OFF"){
                    String v = "OFF";
                    mdatabase.child("Actuadores").child("LUCES/Estado").setValue(v);
                }

                if(adapterView.getItemAtPosition(i).toString()=="ON"){
                    String v = "ON";
                    mdatabase.child("Actuadores").child("LUCES/Estado").setValue(v);
                }

                if(adapterView.getItemAtPosition(i).toString()=="Normal"){
                    String v = "Normal";
                    mdatabase.child("Actuadores").child("LUCES/Estado").setValue(v);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        a2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()=="OFF"){
                    String v = "OFF";
                    mdatabase.child("Actuadores").child("BUZZER/Estado").setValue(v);
                }

                if(adapterView.getItemAtPosition(i).toString()=="ON"){
                    String v = "ON";
                    mdatabase.child("Actuadores").child("BUZZER/Estado").setValue(v);
                }

                if(adapterView.getItemAtPosition(i).toString()=="Normal"){
                    String v = "Normal";
                    mdatabase.child("Actuadores").child("BUZZER/Estado").setValue(v);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mdatabase.child("Sensores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if (datasnapshot.exists()){
                    String distancia = datasnapshot.child("HC-SR02/Valor").getValue().toString();
                    mTextView.setText("Distancia:  "+ distancia.toString());

                    String pir = datasnapshot.child("PIR/Estado").getValue().toString();
                    txt_pir.setText("Movimiento :  "+ pir.toString());

                    String ldr = datasnapshot.child("LDR/Estado").getValue().toString();
                    txt_luz.setText("Sensor de Luz:  "+ ldr.toString());



                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError databaseError) {

            }
        });


        mdatabase.child("Actuadores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if (datasnapshot.exists()){



                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError databaseError) {

            }
        });








    }

    private void inicioXML()
    {
        a1 = findViewById(R.id.sp_a1);
        a2 = findViewById(R.id.sp_a2);
    }

    private void agregandoValores()
    {
        lista.add("Normal");
        lista.add("ON");
        lista.add("OFF");

    }

    private void darClic()
    {
        a1.setOnItemSelectedListener( this.a1.getOnItemSelectedListener() );
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        a1.setAdapter(adapter);

        a2.setOnItemSelectedListener( this.a2.getOnItemSelectedListener() );
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        a2.setAdapter(adapt);

    }
}