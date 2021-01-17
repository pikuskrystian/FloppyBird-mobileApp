package com.example.birdv2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.view.View;



public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void startGame(View view)
    {
        Intent intent;

        intent = new Intent(this,StartGame.class);
        startActivity(intent);
        finish();
    }

}
