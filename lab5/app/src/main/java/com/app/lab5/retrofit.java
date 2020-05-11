package com.app.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofit extends AppCompatActivity implements View.OnClickListener {

    private TextView t_num;
    private ImageView arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

                t_num =  findViewById(R.id.t_num);

                arrow =  findViewById(R.id.arrow);
                arrow.setOnClickListener(this);

                        fetchData();



            }
            private void fetchData() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Pojo> call = api.getJsonObjectData();
                call.enqueue(new Callback<Pojo>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<Pojo> call, Response<Pojo> response){

                        Pojo o=response.body();
                        String s=new Gson().toJson(response.body());
                    System.out.println("People"+o.getNumber());;

                             t_num.setText(""+o.getNumber());


                    }

                    @Override
                    public void onFailure(Call<Pojo> call, Throwable t) {

                    }
                });









            }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow:
                onBackPressed();
                break;
        }
    }
}


