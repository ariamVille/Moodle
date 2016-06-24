package com.example.ibra.moodle;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Student extends Activity {

    Button folders, grades, out;
    ListView list;
    ArrayAdapter<String> listAdapter ;
    String subjects [] = new String [100];
    String titles [] = new String [100];
    String bodies [] = new String [100];
    String events[] = new String [100];
    String dates [] = new String [100];
    String times [] = new String [100];
    String locations [] = new String [100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        folders = (Button) findViewById(R.id.studSubFolderbtn);
        grades = (Button) findViewById(R.id.gradesBtn);
        out = (Button) findViewById(R.id.logoutBtn);
        list = (ListView) findViewById(R.id.studentlistView);
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,0);

        new GetAllAnnouncements().execute(new Database());
        new GetAllEvents().execute(new Database());

        list.setAdapter(listAdapter);

        goToFolders();
        goToGrades();
        logout();
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

    public void goToFolders()
    {
        folders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Student.this, StudentSubjectFolders.class);
                startActivity(i);
            }
        });
    }

    public void goToGrades()
    {
        grades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Student.this, StudentFinalGrades.class);
                startActivity(i);
            }
        });
    }

    public void getAnnouncements(JSONArray jsonArray)
    {
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                subjects[i] = json.getString("Subject");
                titles[i] = json.getString("Title");
                bodies[i] = json.getString("Body");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!subjects[i].equals(""))
            {
                listAdapter.add(subjects[i]+": "+titles[i]+"\n"+bodies[i]);
            }

        }
    }

    private class GetAllAnnouncements extends AsyncTask<Database,Long,JSONArray> {
        @Override
        protected JSONArray doInBackground(Database... params) {

            // it is executed on Background thread

            return params[0].getAllAnnouncements();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            getAnnouncements(jsonArray);
        }
    }

    public void getEvents(JSONArray jsonArray)
    {
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                events[i] = json.getString("Title");
                dates[i] = json.getString("Date");
                times[i] = json.getString("Time");
                locations[i] = json.getString("Location");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!subjects[i].equals(""))
            {
                listAdapter.add(events[i]+"\n"+dates[i]+": "+times[i]+"\n"+locations[i]);
            }

        }
    }

    private class GetAllEvents extends AsyncTask<Database,Long,JSONArray> {
        @Override
        protected JSONArray doInBackground(Database... params) {

            // it is executed on Background thread

            return params[0].getAllEvents();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            getEvents(jsonArray);
        }
    }
}
