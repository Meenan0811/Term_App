package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.Meenan.Term_App.R;

public class AddAssesment extends AppCompatActivity {

    private Intent intent;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assesment);

        courseId = getIntent().getIntExtra("courseID", -1);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.back_menu, menu);
            return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.back) {
            intent = new Intent(AddAssesment.this, ModifyCourses.class);
            intent.putExtra("courseID", courseId);
            startActivity(intent);
            return true;
        }
        return true;
    }
}

