package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cse489.coursemanagement.Models.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_course extends AppCompatActivity {
    private DatabaseReference courseRef;
    private TextView show;
    private Button createCourseBtn, cancelBtn;
    private TextInputEditText course_Id, name, credit, resource_id;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        show = findViewById(R.id.show);
        createCourseBtn = findViewById(R.id.createCourseButton);
        cancelBtn = findViewById(R.id.cancelButton);

        name = findViewById(R.id.CourseName);
        course_Id = findViewById(R.id.courseId);
        credit = findViewById(R.id.credit);
        resource_id = findViewById(R.id.recourse_id);

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();


        courseRef = FirebaseDatabase.getInstance().getReference().child("course");

        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString().trim();
                String course_id1 = course_Id.getText().toString().trim();
                String credit1 = credit.getText().toString().trim();
                String res_id = resource_id.getText().toString().trim();

                if (TextUtils.isEmpty(name1)) {
                    name.setError("Name is required");
                }
                if (TextUtils.isEmpty(course_id1)) {
                    name.setError("CourseId is required");
                }
                if (TextUtils.isEmpty(credit1)) {
                    course_Id.setError("Credit is required");
                } else {
                    if (TextUtils.isEmpty(res_id)) {
                        res_id = "";
                    }


                    courseRef = FirebaseDatabase.getInstance().getReference().child("course").child(course_id1);

                    HashMap courseInfo = new HashMap();
                    courseInfo.put("course_id",course_id1);
                    courseInfo.put("course_Name", name1);
                    courseInfo.put("course_Credit", credit1);
                    courseInfo.put("created_by", currentUserId);
                    courseInfo.put("resource_id", res_id);


                    courseRef.updateChildren(courseInfo).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull  Task task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Data stored successfully!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Data was not stored successfully!", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                    });

//                    show.setText(name1);

                }

            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Create_course.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        String uid = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
//        show.setText(uid);

        System.out.println(uid);


    }
}