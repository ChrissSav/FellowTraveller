package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    int SPLASH_TIME = 2000;
    private GlobalClass globalClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        globalClass = (GlobalClass) getApplicationContext();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                //This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
                load2();
            }
        }, SPLASH_TIME);

        //Code to start timer and take action after the timer ends

    }

    //Method to run progress bar for 5 seconds





    public void load2() {
        FileInputStream fis = null;
        boolean flag = false;
        try {
            fis = openFileInput(getString(R.string.FILE_USER_INFO));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String m ="";
            int i =0;
            while ((text = br.readLine()) != null) {
                if (i==2){
                    globalClass.setName(text);
                }else if(i==3){
                    globalClass.setEmail(text);
                }else if(i==1){
                    globalClass.setId(Integer.parseInt(text));
                    CheckUserInfoToUpdate(globalClass.getId());
                }else if (i==0) {
                    if(text.equals("true")){
                        LoadUserPic();
                        flag = true;
                    }
                }
                i++;
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
        if(flag){
            Intent mainIntent = new Intent(SplashActivity.this, HomeBetaActivity.class);
            startActivity(mainIntent);
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }else{
            save();
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
    }


    public void LoadUserPic() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(getString(R.string.FILE_USER_PICTURE));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line,line1 = "";
            try
            {
                while ((line = br.readLine()) != null)
                    line1+=line;
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            globalClass.setUser_icon(line1);
            return;
        } catch (FileNotFoundException e) {
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

    private void CheckUserInfoToUpdate(int id){
        JsonApi jsonPlaceHolderApi;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        Call<UserAuth> call = jsonPlaceHolderApi.CheckUserInfo(id);

        call.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> mcall, Response<UserAuth> response) {
                if (!response.isSuccessful()) {
                    // Toast.makeText(MainActivity.this,"response "+response.errorBody()+"\n"+"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                final UserAuth user = response.body();
                if(user.getName()!=null){
                    //save("true",user.getId()+"",user.getName(),user.getEmail());
                    globalClass.setUser_icon(user.getPicture());
                    SaveUserPicture(user.getPicture());
                    return;
                }

                // Toast.makeText(MainActivity.this,"Ανεπιτυχής είσοδος",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                //  Toast.makeText(LoginActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void SaveUserPicture(String image) {
        String text = image;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(getString(R.string.FILE_USER_PICTURE), MODE_PRIVATE);
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


    public void save() {
        DeleteUserPicture();
        String text = "false";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(getString(R.string.FILE_USER_INFO), MODE_PRIVATE);
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

    public void DeleteUserPicture() {
        String text = "null";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(getString(R.string.FILE_USER_PICTURE), MODE_PRIVATE);
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
}

