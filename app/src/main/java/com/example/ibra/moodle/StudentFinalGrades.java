package com.example.ibra.moodle;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StudentFinalGrades extends Activity {

    ListView list;
    ArrayAdapter<String>listAdapter;
    TextView WAM;

    int IDs [] = new int [100];
    String usernames [] = new String [100];
    int years [] = new int [100];
    String sessions [] = new String [100];
    String subjects [] = new String [100];
    int credits [] = new int [100];
    int marks [] = new int [100];
    String grades [] = new String [100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_final_grades);

        list = (ListView)findViewById(R.id.finalGradesList);
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,0);
        WAM = (TextView)findViewById(R.id.wamTV);

        new GetAllRecordsTask().execute(new Database());

        list.setAdapter(listAdapter);

    }

    public String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("username.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void setData(JSONArray jsonArray)
    {
        int wamMarks [] = new int [100];
        int count = 0;
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                IDs[i] = json.getInt("ID");
                usernames[i] = json.getString("Username");
                years[i] = json.getInt("Year");
                sessions[i] = json.getString("Session");
                subjects[i] = json.getString("Subject");
                credits[i] = json.getInt("Credits");
                marks[i] = json.getInt("Mark");
                grades[i] = json.getString("Grade");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (usernames[i].equals(readFromFile()))
            {
                listAdapter.add(years[i]+" "+sessions[i]+" "+subjects[i]+" "+credits[i]+" "+marks[i]+" "+grades[i]);
                wamMarks[count] = marks[i];
                count++;
            }
        }
        int total = 0;
        for (int i=0; i<count; i++)
        {
            total += wamMarks[i];
        }
        int average = total/count;
        WAM.setText(average+"");
    }



    private class GetAllRecordsTask extends AsyncTask<Database,Long,JSONArray> {
        @Override
        protected JSONArray doInBackground(Database... params) {

            // it is executed on Background thread

            return params[0].getAllGrades();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setData(jsonArray);
        }
    }
}
