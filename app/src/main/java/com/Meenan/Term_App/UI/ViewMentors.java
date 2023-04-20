package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Mentor;
import com.Meenan.Term_App.R;

import java.util.ArrayList;
import java.util.List;

public class ViewMentors extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private EditText email;
    private String mName;
    private String mPhone;
    private String mEmail;
    private int mentorId;
    private int courseId;
    Button saveMentor;
    Intent intent;
    List<Mentor> mentorList = new ArrayList<>();
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mentors);

        //Assign EditText fields
        name = findViewById(R.id.editmentorname);
        phone = findViewById(R.id.editmentorphone);
        email = findViewById(R.id.editmentoremail);
        saveMentor = findViewById(R.id.savementorbutton);

        //Assign passed information
        mentorId = getIntent().getIntExtra("mentorId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        repository = new Repository(getApplication());

        try {
            mentorList = repository.getAllMentors();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (mentorId != -1) {
            for (Mentor m : mentorList) {
                if (mentorId == m.getMentorID()) {
                    name.setText(m.getMentorName());
                    phone.setText(m.getPhone());
                    email.setText(m.getEmail());
                }
            }
        }

        saveMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName = name.getText().toString();
                mPhone = phone.getText().toString();
                mEmail = email.getText().toString();
                if (!mName.isEmpty() || !mPhone.isEmpty() || !mEmail.isEmpty()) {
                    if (mentorId == -1) {
                        Mentor mMentor = new Mentor(mName, mPhone, mEmail, courseId);
                        try {
                            repository.insert(mMentor);
                            Toast.makeText(ViewMentors.this, "Mentor, " + mName + " added", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Mentor mMentor = new Mentor(mentorId, mName, mPhone, mEmail, courseId);
                        try {
                            repository.update(mMentor);
                            Toast.makeText(ViewMentors.this, "Mentor, " + mName + " updated", Toast.LENGTH_LONG).show();;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    intent = new Intent(ViewMentors.this, ModifyCourses.class);
                    intent.putExtra("mentorID'", mentorId);
                    intent.putExtra("courseID", courseId);
                    startActivity(intent);

                }else {
                    Toast.makeText(ViewMentors.this, "Mentor not added, please check all fields are completed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}