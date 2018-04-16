package com.baking.siva.bakingrecipe.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.baking.siva.bakingrecipe.R;

import java.util.ArrayList;

public class IngActivity extends AppCompatActivity {

    ArrayList<String> myArrList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ing);

        Intent intent = getIntent();
        myArrList = intent.getStringArrayListExtra("mIngList");

        //myArrList = savedInstanceState.getStringArrayList("mIngList");
        Log.v("IngActivity", String.valueOf((myArrList)));
        TextView textView = findViewById(R.id.ing_list);
        for (int idx = 0; idx < myArrList.size(); idx++) {
            textView.setText(myArrList.get(idx));
        }
        //textView.setText("HelloW");
    }
}
