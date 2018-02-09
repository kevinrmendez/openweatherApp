package com.example.kevin.asynctasktest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText cityTv;
    private TextView urlTv;
    private TextView finalResult;
    private  final String API_KEY = "905bc71df94ed57dd2e2ee64fd13ebf0" ;
    private WeatherDbHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityTv = (EditText)findViewById(R.id.city);
        button = (Button)findViewById(R.id.btn_run);
        finalResult = (TextView)findViewById(R.id.result);
        urlTv = (TextView)findViewById(R.id.url_tv);

         dbHelper = new WeatherDbHelper(this);
         db = dbHelper.getWritableDatabase();
        /*
        values = new ContentValues();
        values.put("id","1");
        values.put("city","Tokyo");
        values.put("temperature","234");


        long row = db.insert("weather",null,values);
        if(row > 0){
            Toast.makeText(this,"data inserted",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"data not inserted",Toast.LENGTH_SHORT).show();
        }
        */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            MyAsyncTask asyncTask = new MyAsyncTask(getApplicationContext());
                String cityQuery;
                String appId;
                String URL;
                appId = API_KEY;
                Uri.Builder builder = Uri.parse("http://api.openweathermap.org/data/2.5/weather").buildUpon();
                 cityQuery = cityTv.getText().toString();

                builder.appendQueryParameter("q",cityQuery);
                builder.appendQueryParameter("appid",appId);
                URL = builder.toString();
                urlTv.setText(URL);
                Context context = getApplicationContext();
                Toast.makeText(context,URL,Toast.LENGTH_SHORT).show();




                //asyncTask.execute("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22\"");
               asyncTask.execute(URL);
                //asyncTask.execute("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=905bc71df94ed57dd2e2ee64fd13ebf0");

            }
        });

    }
    public class MyAsyncTask extends AsyncTask<String,String,String> {
        private Context mContext;
        public MyAsyncTask(Context context){
            mContext = context;
        }
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line="";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String jsonData = buffer.toString();
                JSONObject parentObject = new JSONObject(jsonData);
                String city = parentObject.getString("name");
                JSONObject main = parentObject.getJSONObject("main");
                String temp = main.getString("temp");
                String weather = city + " and the temp is " + temp;
                values = new ContentValues();
                values.put("id","1");
                values.put("city",city);
                values.put("temperature",temp);


                long row = db.insert("weather",null,values);
                if(row > 0){
                    Toast.makeText(mContext,"data inserted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext,"data not inserted",Toast.LENGTH_SHORT).show();
                }

                return weather;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }
        @Override
        protected  void onPostExecute(String res){
            super.onPostExecute(res);
            if(res != null) {
                finalResult.setText(res);


            }else {
                finalResult.setText("NULL");
            }
        }
    }

}
