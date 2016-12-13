package com.example.shivangikanchan.navigation.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
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

/**
 * Created by Shivangi Kanchan on 14-11-2016.
 */

public class NewsFeed5 extends Fragment {
    RecyclerView recyclerView;
    Context context;
    String connectivity = null;
    String re="",resp[]={"","","","","",""};
    String desc[]={"","","","","",""};
    public ImageButton button;
    String android_version_names[]={"","","","","",""};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.news_layout,container,false);
        context = container.getContext();
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        new GetDataTask().execute("http://192.168.43.31:3000/api/feed1");

        return v;
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
            //    Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
           //     toast.show();
            } else {
                //Toast toast = Toast.makeText(getActivity(),connectivity, Toast.LENGTH_SHORT);
                //toast.show();
                JSONObject json= null;
                try {
                    JSONArray jarr=new JSONArray(connectivity);
                    String str[]=new String[jarr.length()];
                    String st[]=new String[jarr.length()];
                    int st1[]=new int[jarr.length()];
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j =jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                        //resp[i]=j.getString("hubname");
                        //resp[i]=j.getString("message");
                        Log.e("hubs",j.getString("hubname"));
                        String hub=j.getString("hubname");
                        if(hub.equals("ieeesbjiit"))
                        {
                            str[i]="OSDC";
                            st1[i]= R.drawable.osdc;
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
                root.put("hub","uCr.jiit");
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast toast = Toast.makeText(getActivity(),"IN RESUME", Toast.LENGTH_LONG);
        //toast.show();
        new GetDataTask().execute("http://192.168.43.31:3000/api/feed1");
    }
}
