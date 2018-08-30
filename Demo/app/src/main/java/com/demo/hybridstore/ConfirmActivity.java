package com.demo.hybridstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hybridstore.app.R;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Order confirmed");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        //Initialize view ids
        final Button confirmButton = findViewById(R.id.confirmButton);
        //When button is pressed switch to confirm activity
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
