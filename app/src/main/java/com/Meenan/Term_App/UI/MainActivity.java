package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.viewtermbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewTerm.class);
                startActivity(intent);
            }
        });

        //Test Items
        Term term = new Term("01/01/2023", "01/30/2023", "Term 1");
        Repository repository = new Repository(getApplication());
        try {
            repository.insert(term);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.viewterms:
                 intent = new Intent(MainActivity.this, ViewTerm.class);
                 startActivity(intent);
                 return true;


            case R.id.viewcourses:
                intent = new Intent(MainActivity.this, ViewCourses.class);
                startActivity(intent);
                return true;

            case R.id.viewmentors:
                intent = new Intent(MainActivity.this, ViewMentors.class);
                startActivity(intent);
                return true;
        }
        return true;
    }
}