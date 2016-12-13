package com.example.shivangikanchan.navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.net.URL;

/**
 * Created by Shivangi Kanchan on 16-10-2016.
 */

public class MainActivity extends AppCompatActivity {
    int answer;
    UserDbHelper db;
    SQLiteDatabase sqLiteDatabase, db1;
    String connectivity = null;
    EditText nam, pass;
  public static int cs=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView reg = (TextView) findViewById(R.id.textView);
        Button btn = (Button) findViewById(R.id.button);
        nam = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        db=new UserDbHelper(this,"new12",null,1);
        Log.e("fff","Database created");
        sqLiteDatabase= db.getReadableDatabase();
        db1=db.getWritableDatabase();
        final String stat = getData();
        if (stat.contentEquals("logout")){
            Toast toast2 = Toast.makeText(getApplicationContext(), " please login or register ", Toast.LENGTH_LONG);
            toast2.show();

        }
        else{

            Intent cinemaIntent = new Intent(getApplicationContext(), navigation.class);
            startActivity(cinemaIntent);
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new GetDataTask().execute("http://192.168.43.31:3000/api/login");
                adddata1();
               // cs=viewcontact();


            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), Register.class));

            }
        });
    }

    class GetDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return getData(params[0]);
            } catch (IOException ex) {
                return null;
            }
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            connectivity = result;
            if (connectivity == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                String res = "s";
                //String c =connectivity.toString();
                Log.e("ddd", connectivity.compareTo(res) + " ");
                // Toast toast = Toast.makeText(getApplicationContext(), connectivity, Toast.LENGTH_SHORT);
                //toast.show();
                // String res="s";
                if (connectivity.compareTo(res) == 1) {
                    // Toast toast = Toast.makeText(getApplicationContext(), connectivity, Toast.LENGTH_SHORT);
                    //toast.show();
                    saveData(nam.getText().toString());
                    Log.e("mainactivity","adding data");

                    Intent cinemaIntent = new Intent(getApplicationContext(), navigation.class);
                    cinemaIntent.putExtra("id",cs);
                    startActivity(cinemaIntent);
                }

            }
            //set data response to textView
            // mResult.setText(result);

            //cancel progress dialog
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        public String ConvertData(String name, String lname) {
            JSONObject root = new JSONObject();
            try {
                root.put("name", name);
                root.put("pass", lname);
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String getData(String urlPath) throws IOException {
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader = null;
            String mo = null;
            String dat = ConvertData(nam.getText().toString(), pass.getText().toString());
            try {

                //Initialize and config request, then connect to server
                URL url = new URL(urlPath + "/" + dat);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Read data response from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }

            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }

            String s = result.toString();
            return s;
        }
    }

    public void saveData(String s) {
        SharedPreferences loginData = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("status", s);
        editor.apply();
    }

    public String getData() {
        SharedPreferences loginData = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String stat = loginData.getString("status", "");
        return stat;
    }

    public String adddata1() {


        //db.insertdata1(Integer.parseInt(nam.getText().toString()), pass.getText().toString());
        //   Toast.makeText(getBaseContext(),"Data inserted", Toast.LENGTH_LONG).show();
        Log.e("ddd","data inserted");

        return "Hello";
    }
  /*  public int viewcontact(){
        Student s;
        int s1;
        s = db.readMyDb();
//Log.e("bbs",s.getE()+"");
//     s1=s.getE();
        // userdbhelper.getinformation();
        return s1;
    }*/
    public static int getcs(){
        Log.e("cs",cs+"");
        return cs;
    }

}