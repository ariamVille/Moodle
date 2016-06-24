package com.example.ibra.moodle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TeacherSpecificSubject extends Activity {

    TextView title;
    Button upload, announce, sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_specific_subject);

        title = (TextView)findViewById(R.id.subTtlTV);
        title.setText(getIntent().getExtras().getString("subject"));

        upload = (Button)findViewById(R.id.uploadMarksBtn);
        announce = (Button)findViewById(R.id.postAnnBtn);
        sendMail = (Button)findViewById(R.id.mailAnnounceBtn);

        goToUploadMarks();
        goToPostAnnouncement();
        goToSendMail();
    }

    private void goToSendMail() {
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TeacherSpecificSubject.this, SendEmail.class);
                i.putExtra("sName", title.getText().toString());
                startActivity(i);
            }
        });
    }

    private void goToPostAnnouncement() {
        announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TeacherSpecificSubject.this, PostAnnouncement.class);
                i.putExtra("subjectName", title.getText().toString());
                startActivity(i);
            }
        });
    }

    private void goToUploadMarks() {
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TeacherSpecificSubject.this, UploadMarks.class);
                i.putExtra("subName", title.getText().toString());
                startActivity(i);
            }
        });
    }
}
