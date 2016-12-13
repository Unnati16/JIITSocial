package com.example.shivangikanchan.navigation.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivangikanchan.navigation.MyAdapter2;
import com.example.shivangikanchan.navigation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shivangi Kanchan on 14-11-2016.
 */

public class YearBook extends Fragment {

    String connectivity,name,enroll,s1,s2,cs="";
    Context context;
    RecyclerView rv;
    String[] stro={"huh"};
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.yearbook_layout,null);
        context=container.getContext();

        new GetDataTask3().execute("http://192.168.43.31:3000/api/showyearbook");
        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e("unnati", "fabb");


                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.custom1);

                Button dialogButton = (Button) dialog.findViewById(R.id.button2);
                Button send = (Button) dialog.findViewById(R.id.button);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                send.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        EditText nameof=(EditText)dialog.findViewById(R.id.editText1);
                        name=nameof.getText().toString();
                        EditText enrollm=(EditText)dialog.findViewById(R.id.editText2);
                        enroll=enrollm.getText().toString();
                        CharSequence text = "Hello toast!";
                        int duration = Toast.LENGTH_SHORT;
                        int e=Integer.parseInt(enroll);
                        if(e>=13000000&&e<=13999999) {
                            new GetDataTask().execute("http://192.168.43.31:3000/api/addtoyearbook");
                            new GetDataTask3().execute("http://192.168.43.31:3000/api/showyearbook");
                        }
                        else {
                            CharSequence text1 = "Sorry! Not eligible";
                            Toast toast = Toast.makeText(context, text1, duration);
                            toast.show();
                        }

                    }
                });
                dialog.show();
            }
        });
        cs=getData();
        FloatingActionButton fab1 = (FloatingActionButton)v.findViewById(R.id.comment);
        fab1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e("unnati", "fabb");


                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.comment);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, stro);
                AutoCompleteTextView userInput;
                userInput = (AutoCompleteTextView) dialog
                        .findViewById(R.id.editText);
                userInput.setAdapter(adapter);

                Button dialogButton = (Button) dialog.findViewById(R.id.button2);
                Button send = (Button) dialog.findViewById(R.id.button);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                send.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick (View v){

                        AutoCompleteTextView s=(AutoCompleteTextView) dialog.findViewById(R.id.editText);
                        s1=s.getText().toString();
                        EditText ss=(EditText)dialog.findViewById(R.id.editText3);
                        s2=ss.getText().toString();

                        new GetDataTask2().execute("http://192.168.43.31:3000/api/addpost");
                        new GetDataTask3().execute("http://192.168.43.31:3000/api/showyearbook");
                        CharSequence text = "Hello toast!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        // new GetDataTask().execute("http://192.168.43.144:3000/api/notification");

                    }
                });
                dialog.show();
            }

        });
        rv = (RecyclerView)v.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return v;
    }
    public String getData() {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String stat = loginData.getString("status", "");
        return stat;
    }


    @Override
    public void onResume() {
        super.onResume();
        new GetDataTask3().execute("http://192.168.43.31:3000/api/showyearbook");

    }

    public class GetDataTask3 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            // progressDialog = new ProgressDialog(getActivity());
            // progressDialog.setMessage("Loading data...");
            // progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return getData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String getData(String urlPath) throws IOException {
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader = null;
            String mo = null;
            //        String dat = ConvertData();

            try {

                //Initialize and config request, then connect to server
                URL url = new URL(urlPath);
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


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            connectivity = result;
            if (connectivity == null) {
                Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                JSONObject json = null;
                //String str[]=new String[jarr.length()];
                try {
                    JSONArray jarr = new JSONArray(connectivity);
                    String str[] = new String[jarr.length()];
                    stro = new String[jarr.length()];
                    String name1[][]=new String[jarr.length()][20];
                    String post[][]=new String[jarr.length()][20];
                    int[] len=new int[20];

                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject j = jarr.getJSONObject(i);
                        //resp[i]=j.toString();

                        int v=0;
                        int u=0;
                        String nam = j.getString("name");
                        String views=j.getString("views");
                        JSONArray jarr2=new JSONArray(views);
                        for(int f = 0;f<jarr2.length();f++)
                        {   JSONObject k = jarr2.getJSONObject(f);
                            name1[i][v++] = k.getString("name1");
                            post[i][u++]= k.getString("post").replace("@@"," ");
                            Log.e("post is",post[i][v-1]);

                        }
                        stro[i] = nam;
                        len[i]=v;
                        //  Log.e("unnati",post[i][0]);
                        //  Log.e("unn",name1[i][0]);
                        //str[i]=desc;
                        //displayNotification("Extra for A",hub,desc,Suggested.class,i);

                        //resp[i]=j.getString("message");
                    }
                    //Toast toast1 = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                    //toast1.show();
                    String st1[]={"Unnati", "Shivangi","Garima"};
                    String[][] name2={{"abc","xyz"},{"himym","friends"}};
                    String[][] post1={{"hii","hello"},{"waasup","getlost"}};
                    int[] len1={2,2};

                    MyAdapter2 adapter = new MyAdapter2(context, stro, name1,post,len);
                    // MyAdapter2 adapter = new MyAdapter2(context, st1, name2,post1,len1);
                    rv.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

        }
        //set data response to textView
        // mResult.setText(result);

        //cancel progress dialog
        // if (progressDialog != null) {
        //   progressDialog.dismiss();


    }

    public class GetDataTask2 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            // progressDialog = new ProgressDialog(getActivity());
            // progressDialog.setMessage("Loading data...");
            // progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return getData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        private String getData(String urlPath) throws IOException {
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader = null;
            String mo = null;
            String dat=ConvertData();

            try {

                //Initialize and config request, then connect to server
                URL url = new URL(urlPath+"/"+dat);
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

            String s= result.toString();
            return s;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            connectivity = result;
            if (connectivity == null) {
                Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                JSONObject json= null;
                //String str[]=new String[jarr.length()];
                try {
                    JSONArray jarr=new JSONArray(connectivity);
                    String str[]=new String[jarr.length()];
                    String[] st=new String[jarr.length()];
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j =jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                        String nam = j.getString("name") ;

                        st[i]=nam;
                        //str[i]=desc;
                        //displayNotification("Extra for A",hub,desc,Suggested.class,i);

                        //resp[i]=j.getString("message");
                    }
                    //Toast toast1 = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                    //toast1.show();
                    // MyAdapter2 adapter = new MyAdapter2(context,st,str);
                    //rv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

        }
        //set data response to textView
        // mResult.setText(result);

        //cancel progress dialog
        // if (progressDialog != null) {
        //   progressDialog.dismiss();



        public String ConvertData() {
            JSONObject root = new JSONObject();
            try {s2=s2.replace(" ","@@");
                root.put("name", s1);
                root.put("post", s2);
                root.put("enroll",cs);
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public class GetDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            // progressDialog = new ProgressDialog(getActivity());
            //progressDialog.setMessage("Loading data...");
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return getData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        private String getData(String urlPath) throws IOException {
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader = null;
            String mo = null;
            String dat=ConvertData();

            try {

                //Initialize and config request, then connect to server
                URL url = new URL(urlPath+"/"+dat);
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

            String s= result.toString();
            return s;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            connectivity = result;
            if (connectivity == null) {
                Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                String res = "s";
                //String c =connectivity.toString();
                Log.e("ddd", connectivity.compareTo(res) + " ");
                // Toast toast = Toast.makeText(getApplicationContext(), connectivity, Toast.LENGTH_SHORT);
                //toast.show();
                // String res="s";
                if (connectivity.compareTo(res) == 1) {

                    Toast toast = Toast.makeText(getActivity(),"Successfully Added", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

        }
        //set data response to textView
        // mResult.setText(result);

        //cancel progress dialog
        // if (progressDialog != null) {
        //   progressDialog.dismiss();



        public String ConvertData() {
            JSONObject root = new JSONObject();
            try {
                root.put("name", name);
                root.put("enroll", enroll);
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

