package com.example.shivangikanchan.navigation.Fragment;

/**
 * Created by Shivangi Kanchan on 03-12-2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.shivangikanchan.navigation.R;
import com.example.shivangikanchan.navigation.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.shivangikanchan.navigation.Androidversion;
import com.example.shivangikanchan.navigation.R;
import com.example.shivangikanchan.navigation.RecyclerAdapter;
import com.example.shivangikanchan.navigation.navigation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shivangi Kanchan on 14-11-2016.
 */

public class Subscribed extends Fragment {
    RecyclerView recyclerView;
    Context context;
    String cs=null;
    String connectivity = null,datastr[]=null,datast[]=null;
    int datast1[]=null;
    String re="",resp[]={"","","","","",""};
    String desc[]={"","","","","",""};
    public ImageButton button;
    String android_version_names[]={"","","","","",""};
    //private final int android_image_urls[] = {
    //      R.drawable.i1, R.drawable.i2, R.drawable.i3, R.drawable.i4, R.drawable.i5, R.drawable.i1
    //};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.subscribed_layout,container,false);
        context = container.getContext();
        ScrollView view=(ScrollView)v.findViewById(R.id.scrollView);
        cs=getData();
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);


        return v;
    }
    public void saveData(String s) {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("NewsInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("data3", s);
        editor.apply();
    }

    public String getData1() {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("NewsInfo", Context.MODE_PRIVATE);
        String stat = loginData.getString("data3", "");
        return stat;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(datast==null){}else{
            RecyclerAdapter adapter = new RecyclerAdapter(context,datastr,datast,datast1);
            recyclerView.setAdapter(adapter);

        }
        new GetDataTask().execute("http://192.168.43.31:3000/api/getsub");
    }

    public class GetDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
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
                //   Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                //  toast.show();
            } else {
                // Toast toast = Toast.makeText(getActivity(),connectivity, Toast.LENGTH_SHORT);
                //toast.show();
                JSONObject json= null;
                try {
                    JSONArray jarr=new JSONArray(connectivity);
                    String str[]=new String[jarr.length()];
                    int st1[]=new int[jarr.length()];
                    String st[]=new String[jarr.length()];
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j =jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                        //resp[i]=j.getString("hubname");
                        //resp[i]=j.getString("message");
                        Log.e("hubs",j.getString("hubname"));
                        String hub=j.getString("hubname");
                        if(hub.equals("cice.jiit"))
                        { Log.e("fff"," in cice");
                            str[i]="CICE";
                            st1[i]= R.drawable.cice;
                        }
                        else if(hub.equals("thethespiancircle"))
                        {
                            str[i]="THESPIAN HUB";
                            st1[i]= R.drawable.thespian;
                        }

                        else if(hub.equals("graficasjiit"))
                        {
                            str[i]="GRAFICAS ANIMATION HUB";
                            st1[i]= R.drawable.graficas;
                        }

                        else if(hub.equals("aisleofvogue"))
                        {
                            str[i]="RADIANCE HUB";
                            st1[i]= R.drawable.radiance;
                        }
                        else if(hub.equals("jiitmusichub"))
                        {
                            str[i]="CRESCENDO HUB";
                            st1[i]= R.drawable.crescendo;
                        }
                        else if(hub.equals("JEB.jiit"))
                        {
                            str[i]="JAYPEE ECONOMICS HUB";
                            st1[i]= R.drawable.jeb;
                        }
                        else if(hub.equals("uCr.jiit"))
                        {
                            str[i]="ROBOTICS HUB";
                            st1[i]= R.drawable.robotics;
                        }
                        else if(hub.equals("RIBOSEjiit"))
                        {
                            str[i]="RIBOSE HUB";
                            st1[i]= R.drawable.ribose;
                        }
                        else if(hub.equals("jhankaarjiit"))
                        {
                            str[i]="JHANKAAR HUB";
                            st1[i]= R.drawable.jhankaar;
                        }
                        else if(hub.equals("gdgjiitnoida"))
                        {
                            str[i]="GDG";
                            st1[i]= R.drawable.gdg;
                        }
                        else if(hub.equals("ieeesbjiit"))
                        {
                            str[i]="IEEE";
                            st1[i]= R.drawable.ieee;
                        }
                        else if(hub.equals("Parola.LiteraryHub"))
                        {
                            str[i]="PAROLA LITERARY HUB";
                            st1[i]= R.drawable.parola;
                        }
                        else if(hub.equals("jiit.knuth"))
                        {
                            str[i]="KNUTH PROGRAMMING HUB";
                            st1[i]= R.drawable.knuth;
                        }
                        else{
                            str[i]=hub;
                        }
                        st[i]=j.getString("message");
                    }
                    RecyclerAdapter adapter = new RecyclerAdapter(context,str,st,st1);
                    recyclerView.setAdapter(adapter);

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
            try {
                root.put("roll",cs);
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
        //set data response to textView
        // mResult.setText(result);

        //cancel progress dialog
        // if (progressDialog != null) {
        //   progressDialog.dismiss();


    public String getData() {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String stat = loginData.getString("status", "");
        return stat;
    }

}


