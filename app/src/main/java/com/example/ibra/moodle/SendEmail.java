package com.example.ibra.moodle;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendEmail extends Activity {

    EditText et1, et2;
    Button s;
    TextView tv;
    String sub1s [] = new String [100];
    String sub2s [] = new String [100];
    String sub3s [] = new String [100];
    String sub4s [] = new String [100];
    String emails [] = new String [100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        et1 = (EditText)findViewById(R.id.subjectET);
        et2 = (EditText)findViewById(R.id.emailBodyET);
        s = (Button)findViewById(R.id.sendEmailBtn);
        tv = (TextView)findViewById(R.id.sendMailTitle);
        tv.setText(getIntent().getExtras().getString("sName"));

        sendMailBtn();
    }

    private void sendMailBtn() {
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAllRecordsTask().execute(new Database());
            }
        });
    }

    private void sendMail(String m) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{m});
        i.putExtra(Intent.EXTRA_SUBJECT, et1.getText().toString());
        i.putExtra(Intent.EXTRA_TEXT   , et2.getText().toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SendEmail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(JSONArray jsonArray)
    {
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                sub1s[i] = json.getString("Subject1");
                sub2s[i] = json.getString("Subject2");
                sub3s[i] = json.getString("Subject3");
                sub4s[i] = json.getString("Subject4");
                emails[i] = json.getString("Email");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ((tv.getText().equals(sub1s[i])) || (tv.getText().equals(sub2s[i])) || (tv.getText().equals(sub3s[i])) || (tv.getText().equals(sub4s[i])))
                sendMail(emails[i]);
        }

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
