package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Assesment;
import com.Meenan.Term_App.R;

public class AddAssesment extends AppCompatActivity {

    private Intent intent;
    private EditText editTitle;
    private Spinner assTypeSpinner;
    private EditText startDate;
    private EditText editDate;
    private TextView assLabel;
    private int courseId;
    private int assId;
    private String assTitle;
    private String assType;
    private String assStart;
    private String assEnd;
    private Button saveButton;
    private Assesment mAss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assesment);

        //Assign local variables to activity ID
        editTitle = findViewById(R.id.editassesmenttitle);
        editDate = findViewById(R.id.editassesmentend);
        assLabel = findViewById(R.id.assesmentlabel);
        saveButton = findViewById(R.id.saveassesmentbutton);
        startDate = findViewById(R.id.assstartedit);
        assTypeSpinner = findViewById(R.id.asstypespinner);

        //Assign data passed from previoous screen to local variables
        courseId = getIntent().getIntExtra("courseID", -1);
        assTitle = getIntent().getStringExtra("assTitle");
        assEnd = getIntent().getStringExtra("assEnd");
        assId = getIntent().getIntExtra("assId", -1);

        //Populate Assessment type spinner
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(this, R.array.ass_spinner, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        assTypeSpinner.setAdapter(ad);

        if (assId != -1) {
            editTitle.setText(assTitle);
            editDate.setText(assEnd);
            startDate.setText(assStart);
            assLabel.setText("Edit Assessment");
        } else { assLabel.setText("Add Assessment"); }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AddAssesment.this, ModifyCourses.class);
                intent.putExtra("courseId", courseId);
                Repository repository = new Repository(getApplication());
                assTitle = editTitle.getText().toString();
                assEnd = editDate.getText().toString();
                assStart = startDate.getText().toString();
                assType = assTypeSpinner.getSelectedItem().toString();
                if (assId != -1) {
                    mAss = new Assesment(assId, assTitle, assStart,  assEnd,assType, courseId);
                    try {
                        repository.update(mAss);
                        Toast.makeText(AddAssesment.this, "Assessment updated successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    mAss = new Assesment(assTitle, assStart, assEnd, assType, courseId);
                    try {
                        repository.insert(mAss);
                        Toast.makeText(AddAssesment.this, "Assessment added successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        /*assTitle = editTitle.getText().toString();
        assEnd = editTitle.getText().toString(); */
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

