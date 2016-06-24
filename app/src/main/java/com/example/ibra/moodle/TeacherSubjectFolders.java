package com.example.ibra.moodle;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TeacherSubjectFolders extends Activity {

    ListView list;
    ArrayAdapter<String>listAdapter;
    int IDs [] = new int [100];
    String usernames [] = new String [100];
    String sub1s [] = new String [100];
    String sub2s [] = new String [100];
    String sub3s [] = new String [100];
    String sub4s [] = new String [100];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_subject_folders);

        list = (ListView)findViewById(R.id.teachSubFolListView);
        listAdapter = new ArrayAdapter<String>(TeacherSubjectFolders.this,android.R.layout.simple_list_item_1,0);

        new GetAllRecordsTask().execute(new Database());

        list.setAdapter(listAdapter);

        selectSubject();
    }

    private void selectSubject() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sub = list.getItemAtPosition(position).toString();
                Intent i = new Intent (TeacherSubjectFolders.this, TeacherSpecificSubject.class);
                i.putExtra("subject", sub);
                startActivity(i);
            }
        });
    }

    public void setData(JSONArray jsonArray)
    {
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                IDs[i] = json.getInt("ID");
                usernames[i] = json.getString("Username");
                sub1s[i] = json.getString("Subject1");
                sub2s[i] = json.getString("Subject2");
                sub3s[i] = json.getString("Subject3");
                sub4s[i] = json.getString("Subject4");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (usernames[i].equals(readFromFile()))
            {
                listAdapter.add(sub1s[i]);
                listAdapter.add(sub2s[i]);
                listAdapter.add(sub3s[i]);
                listAdapter.add(sub4s[i]);
            }
        }
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



    private class GetAllRecordsTask extends AsyncTask<Database,Long,JSONArray> {
        @Override
        protected JSONArray doInBackground(Database... params) {

            // it is executed on Background thread

            return params[0].getAllSubs();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setData(jsonArray);
        }
    }
}
