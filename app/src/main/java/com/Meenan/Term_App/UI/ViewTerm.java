package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewTerm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);

        FloatingActionButton fButton = findViewById(R.id.addtermbutton);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTerm.this, ModifyTerm.class);
                startActivity(intent);
            }
        });
    }
}