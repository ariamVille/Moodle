package com.example.ibra.moodle;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadMarks extends Activity {

    TextView sub;
    EditText nameET, yearET, sessionET, creditET, markET, gradeET;
    Button submit;
    public static String url = "http://10.0.3.2/uploadMarks.php";
    InputStream is = null;
    String exceptionMessage = "There seems to be some problem connecting to database. " +
            "Please check your Internet Connection and try again.";
    String successMessage = "Data has been entered successfully";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_marks);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        sub = (TextView)findViewById(R.id.subNameTV);
        sub.setText(getIntent().getExtras().getString("subName"));
        nameET = (EditText)findViewById(R.id.nameEdit);
        yearET = (EditText)findViewById(R.id.yearEdit);
        sessionET = (EditText)findViewById(R.id.sessionEdit);
        creditET = (EditText)findViewById(R.id.creditEdit);
        markET = (EditText)findViewById(R.id.markEdit);
        gradeET = (EditText)findViewById(R.id.gradeEdit);
        submit = (Button)findViewById(R.id.submitUploadGradesBtn);

        upload();
    }

    private void upload() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String subject = sub.getText().toString();
                String year = yearET.getText().toString();
                String session = sessionET.getText().toString();
                String credit = creditET.getText().toString();
                String mark = markET.getText().toString();
                String grade = gradeET.getText().toString();

                List<NameValuePair> list = new ArrayList<NameValuePair>(1);
                list.add(new BasicNameValuePair("Username",name));
                list.add(new BasicNameValuePair("Subject",subject));
                list.add(new BasicNameValuePair("Year",year));
                list.add(new BasicNameValuePair("Session",session));
                list.add(new BasicNameValuePair("Credits",credit));
                list.add(new BasicNameValuePair("Mark",mark));
                list.add(new BasicNameValuePair("Grade",grade));

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(list));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                    nameET.setText("");
                    sub.setText("");
                    yearET.setText("");
                    sessionET.setText("");
                    creditET.setText("");
                    markET.setText("");
                    gradeET.setText("");
                    Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_SHORT).show();
                    is.close();
                }catch(IOException e){
                    Toast.makeText(getApplicationContext(), exceptionMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
