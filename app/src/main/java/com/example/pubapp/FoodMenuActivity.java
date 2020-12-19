package com.example.pubapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.cardview.widget.CardView;

public class FoodMenuActivity extends Activity {
public CardView panini, bevande, dolci;
public Button cartShop;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodmenu_activity);

        panini=(CardView)findViewById(R.id.panini);
        bevande=(CardView)findViewById(R.id.bevande);
        dolci=(CardView)findViewById(R.id.dolci);

        panini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(FoodMenuActivity.this, PaniniActivity.class);
                startActivity(in);
            }
        });
        bevande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(FoodMenuActivity.this, BevandeActivity.class);
                startActivity(in);
            }
        });
        dolci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(FoodMenuActivity.this, DolciActivity.class);
                startActivity(in);
            }
        });

    }
}
