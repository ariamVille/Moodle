package com.example.ibra.moodle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentSpecificSubject extends Activity {

    TextView subTitle;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_specific_subject);

        subTitle = (TextView) findViewById(R.id.subjectTitle);
        String s = getIntent().getExtras().getString("subject");
        subTitle.setText(s);

        submit = (Button) findViewById(R.id.submitAssignmentBtn);

        gotoSubmit();
    }

    public void gotoSubmit()
    {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentSpecificSubject.this,SubmitAssignment.class);
                startActivity(i);
            }
        });
    }
}
