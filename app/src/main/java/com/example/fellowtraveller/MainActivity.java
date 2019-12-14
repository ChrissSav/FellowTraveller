package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellowtraveller.BetaActivity.NotificationActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "fellow_login_state.txt";
    private Button btn;
    private Button btn1;
    private TextView text;
    private TextView text1,text3;
    private TextView eisodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eisodos = findViewById(R.id.main_textView9);
        btn = findViewById(R.id.main_button1);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mainIntent);
            }
        });

        btn1 = findViewById(R.id.main_button2);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(mainIntent);
            }
        });
        text = findViewById(R.id.main_textView10);
        text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, MainHomeActivity.class);
                startActivity(mainIntent);
            }
        });

        text1 = findViewById(R.id.main_textView11);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save("true","96","Χρήστος Σαβ","uom@uom.gr");
                Intent intent = new Intent(MainActivity.this, MainHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        text3 = findViewById(R.id.main_textView3);
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TripPageCreatorActivity.class);
                startActivity(intent);
                finish();
            }
        });
        load();

        eisodos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HomeBetaActivity.class);
                startActivity(intent);
            }
        });

    }

    public void save(String status,String id,String name,String email) {
        String text = status+"\n"+id+"\n"+name+"\n"+email;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            if (!(sb.toString().equals("false"))){
                Intent mainIntent = new Intent(MainActivity.this, MainHomeActivity.class);
                startActivity(mainIntent);
                finish();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }







}
