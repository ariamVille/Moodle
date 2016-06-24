package com.example.ibra.moodle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class Login extends Activity {

    Button log;
    EditText user, pass;
    RadioButton stud, teach;

    int IDs [] = new int [10];
    String usernames [] = new String [10];
    String passwords [] = new String [10];
    String types [] = new String [10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log = (Button) findViewById(R.id.loginBtn);
        user = (EditText) findViewById(R.id.usernameEdit);
        pass = (EditText) findViewById(R.id.passwordEdit);
        stud = (RadioButton) findViewById(R.id.studentButton);
        teach = (RadioButton) findViewById(R.id.teacherButton);

        new GetAllRecordsTask().execute(new Database());

    }

    @Override
    protected void onPause() {
        super.onPause();
        user.setText("");
        pass.setText("");
        if (stud.isChecked())
            stud.setChecked(false);
        if (teach.isChecked())
            teach.setChecked(false);
    }

    public void login()
    {
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean teacherChecked = teach.isChecked();
                boolean studentChecked = stud.isChecked();
                String username = user.getText().toString();
                String enteredPass = pass.getText().toString();


                if (studentChecked == true)
                {
                    String password = "";
                    for (int i=0; i<10; i++)
                    {
                            if (!(usernames[i] == null) && (!(types[i] == null)))
                            {
                                if ((usernames[i].equals(username)) && (types[i].equals("Student")))
                                {
                                    password = passwords[i];
                                    break;
                                }
                            }
                    }
                    if (enteredPass.equals(password)){
                        Intent i = new Intent (Login.this, Student.class);
                        writeToFile(username);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (teacherChecked == true)
                {
                    String password = "";
                    for (int i=0; i<10; i++)
                    {
                        if (!(usernames[i] == null) && (!(types[i] == null)))
                        {
                            if ((usernames[i].equals(username)) && (types[i].equals("Teacher")))
                            {
                                password = passwords[i];
                                break;
                            }
                        }
                    }
                    if (enteredPass.equals(password)){
                        Intent i = new Intent (Login.this, Teacher.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(Login.this, "Please select teacher or student", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData(JSONArray jsonArray)
    {
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                IDs[i] = json.getInt("ID");
                usernames[i] = json.getString("Username");
                passwords[i] = json.getString("Password");
                types[i] = json.getString("Type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        login();
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Login.this.openFileOutput("username.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }



    private class GetAllRecordsTask extends AsyncTask<Database,Long,JSONArray> {
        @Override
        protected JSONArray doInBackground(Database... params) {

            // it is executed on Background thread

            return params[0].GetAllLoginRecords();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            getData(jsonArray);
        }
    }
}
