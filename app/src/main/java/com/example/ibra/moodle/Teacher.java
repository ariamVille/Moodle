package com.example.ibra.moodle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Teacher extends Activity {

    Button subFolders, event, out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        subFolders = (Button) findViewById(R.id.teacherSubjectFoldersBtn);
        event = (Button) findViewById(R.id.eventsBtn);
        out = (Button) findViewById(R.id.teacherLogoutBtn);

        logout();
        goToSubjects();
    }

    private void goToSubjects() {
        subFolders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Teacher.this, TeacherSubjectFolders.class);
                startActivity(i);
            }
        });
    }

    public void logout()
    {
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
