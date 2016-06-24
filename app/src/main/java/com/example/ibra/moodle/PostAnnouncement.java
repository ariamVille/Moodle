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

public class PostAnnouncement extends Activity {

    TextView title;
    EditText et1, et2;
    Button p;
    public static String url = "http://10.0.3.2/announce.php";
    InputStream is = null;
    String exceptionMessage = "There seems to be some problem connecting to database. " +
            "Please check your Internet Connection and try again.";
    String successMessage = "Data has been entered successfully";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_announcement);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        title = (TextView)findViewById(R.id.postAnnTitleText);
        title.setText(getIntent().getExtras().getString("subjectName"));
        et1 = (EditText)findViewById(R.id.announcementTitleEdit);
        et2 = (EditText)findViewById(R.id.announcementBodyEdit);
        p = (Button)findViewById(R.id.postBtn);

        post();
    }

    private void post() {
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = title.getText().toString();
                String title = et1.getText().toString();
                String body = et2.getText().toString();

                List<NameValuePair> list = new ArrayList<NameValuePair>(1);
                list.add(new BasicNameValuePair("Subject",subject));
                list.add(new BasicNameValuePair("Title",title));
                list.add(new BasicNameValuePair("Body",body));

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(list));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                    et1.setText("");
                    et2.setText("");
                    Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_SHORT).show();
                    is.close();
                }catch(IOException e){
                    Toast.makeText(getApplicationContext(), exceptionMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
