package com.example.shivangikanchan.navigation.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivangikanchan.navigation.MyAdapter;
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

import static com.example.shivangikanchan.navigation.MainActivity.getcs;

/**
 * Created by Shivangi Kanchan on 14-11-2016.
 */

public class Message extends Fragment {
    Context context;
    int currentuser;
    String[] COUNTRIES={"YOYOYOYO"};
    String cs=null;
    RecyclerView rv;
    String connectivity="";
    EditText e1,e2;
    AutoCompleteTextView userInput,userInput2;
    String datast[]=null;
    String datastr[]=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.message_layout,null);context=container.getContext();

        new GetDataTask3().execute("http://192.168.43.31:3000/api/getcoordinator");
    //    new GetDataTask2().execute("http://192.168.43.144:3000/api/getmessage");
        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
        FloatingActionButton fab1 = (FloatingActionButton)v.findViewById(R.id.fab1);
        new GetDataTask2().execute("http://192.168.43.31:3000/api/getmessage");

        currentuser=getcs();
        cs=Integer.toString(currentuser);
        cs=getData();
        Log.e("ghsas",currentuser+"");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.custom, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);
                userInput = (AutoCompleteTextView) promptsView
                            .findViewById(R.id.editTextDialogUserInput1);
                    userInput.setAdapter(adapter);
                e2=(EditText)promptsView.findViewById(R.id.editTextDialogUserInput11);
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //dialog.setContentView(R.layout.dialog);



                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(true)
                            .setPositiveButton("Send",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // get user input and set it to result
                                            new GetDataTask().execute("http://192.168.43.31:3000/api/message");
                                            new GetDataTask2().execute("http://192.168.43.31:3000/api/getmessage");

                                            dialog.cancel();

                                        }
                                    })

                            .setNegativeButton("Important",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            new GetDataTask2().execute("http://192.168.43.31:3000/api/getmessage");

                                            new GetDataTask4().execute("http://192.168.43.31:3000/api/impmessage");
                                            dialog.cancel();


                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);


                }

            });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.call, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);

                userInput2 = (AutoCompleteTextView) promptsView
                        .findViewById(R.id.editTextDialogUserInput1);
                userInput2.setAdapter(adapter);

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //dialog.setContentView(R.layout.dialog);



                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ask",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        new GetDataTask5().execute("http://192.168.43.31:3000/api/askcontact");


                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();


                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);


            }

        });
        rv = (RecyclerView)v.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return v;
    }
    public void saveData(String s) {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("data1", s);
        editor.apply();
    }
    public String getData1() {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
        String stat = loginData.getString("data1", "");
        return stat;
    }
    @Override
    public void onResume() {
        super.onResume();
        if((datastr)!=null){
            MyAdapter adapter = new MyAdapter(context,datastr,datast);
        rv.setAdapter(adapter);}
        new GetDataTask2().execute("http://192.168.43.31:3000/api/getmessage");
    }

    public String getData() {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String stat = loginData.getString("status", "");
        return stat;
    }
    public class GetDataTask4 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            //  progressDialog = new ProgressDialog(getActivity());
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
            String dat = ConvertData();
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


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //String c="";

            connectivity = result;
            if (connectivity == null) {

                //         c=getData1();
                Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                //   saveData(connectivity);
                JSONObject json = null;
                try {

                    JSONArray jarr = new JSONArray(connectivity);
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject j = jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                        //resp[i]=j.getString("hubname");

                    }
                    //Toast toast = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                    //toast.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //card();
                //ArrayList<Androidversion> androidVersions = prepareData();
                //RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), androidVersions);
                //Log.e("DDD", "preparing data");
                //recyclerView.setAdapter(adapter);
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
                String mess = e2.getText().toString();
                String useri = userInput.getText().toString();
                useri.replace(" ", "@@");
                mess = mess.replace(" ", "@@");
                root.put("to", useri);
                root.put("message", mess);
                root.put("from", cs);
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

          //  progressDialog = new ProgressDialog(getActivity());
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
            //String c="";

            connectivity = result;
            if (connectivity == null) {

           //         c=getData1();
                Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
             //   saveData(connectivity);
                JSONObject json= null;
                try {

                    JSONArray jarr=new JSONArray(connectivity);
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j = jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                        //resp[i]=j.getString("hubname");

                    }
                    //Toast toast = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                    //toast.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //card();
                //ArrayList<Androidversion> androidVersions = prepareData();
                //RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), androidVersions);
                //Log.e("DDD", "preparing data");
                //recyclerView.setAdapter(adapter);
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
            try {String mess=e2.getText().toString();
                String useri=userInput.getText().toString();
                useri.replace(" ","@@");
                mess=mess.replace(" ","@@");
                root.put("to",useri);
                root.put("message",mess);
                root.put("from",cs);
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class GetDataTask5 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            //  progressDialog = new ProgressDialog(getActivity());
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
            //String c="";

            connectivity = result;
            if (connectivity == null) {

                //         c=getData1();
                Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                //   saveData(connectivity);
                JSONObject json= null;
                try {

                    JSONArray jarr=new JSONArray(connectivity);
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j = jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                        //resp[i]=j.getString("hubname");

                    }
                    //Toast toast = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                    //toast.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //card();
                //ArrayList<Androidversion> androidVersions = prepareData();
                //RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), androidVersions);
                //Log.e("DDD", "preparing data");
                //recyclerView.setAdapter(adapter);
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
            try {//String mess=e2.getText().toString();
                String useri=userInput2.getText().toString();
                useri.replace(" ","@@");
                //mess=mess.replace(" ","@@");
                root.put("of",useri);
                root.put("from",cs);
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class GetDataTask3 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            //progressDialog = new ProgressDialog(getActivity());
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
            //String dat=ConvertData();
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

            String s= result.toString();
            return s;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            connectivity = result;
            if (connectivity == null) {
               // Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                //toast.show();
            } else {
              //  saveData(connectivity);
                JSONObject json= null;
                try {
                    JSONArray jarr=new JSONArray(connectivity);
                    COUNTRIES = new String[jarr.length()];
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j = jarr.getJSONObject(i);
                        COUNTRIES[i]=j.getString("name");
                        //resp[i]=j.getString("hubname");

                    }
                   // Toast toast = Toast.makeText(getActivity(), COUNTRIES[0], Toast.LENGTH_SHORT);
                    //toast.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //card();
                //ArrayList<Androidversion> androidVersions = prepareData();
                //RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), androidVersions);
                //Log.e("DDD", "preparing data");
                //recyclerView.setAdapter(adapter);
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

          //  progressDialog = new ProgressDialog(getActivity());
          //  progressDialog.setMessage("Loading data...");
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
            if (connectivity == null)
            {
                String c=getData1();

                try {
                    JSONArray jarr=new JSONArray(c);
                    String str[]=new String[jarr.length()];
                    String st[]=new String[jarr.length()];
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j = jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                        //resp[i]=j.getString("hubname");
                        str[i]=j.getString("info");
                        st[i]=j.getString("from");
                    }
                    MyAdapter adapter = new MyAdapter(context,str,st);
                    rv.setAdapter(adapter);
                    datast=st;
                    datastr=str;
                    //Toast toast = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                    //toastn.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                connectivity=connectivity.replace("@@"," ");
                saveData(connectivity);
                JSONObject json= null;
                try {
                    JSONArray jarr=new JSONArray(connectivity);
                    String str[]=new String[jarr.length()];
                    String st[]=new String[jarr.length()];
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j = jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                        //resp[i]=j.getString("hubname");
                         str[i]=j.getString("info");
                        st[i]=j.getString("from");
                    }
                    MyAdapter adapter = new MyAdapter(context,str,st);
                    rv.setAdapter(adapter);
                    datast=st;
                    datastr=str;
                    //Toast toast = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                    //toastn.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //card();
                //ArrayList<Androidversion> androidVersions = prepareData();
                //RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), androidVersions);
                //Log.e("DDD", "preparing data");
                //recyclerView.setAdapter(adapter);
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
                root.put("to",cs);
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

