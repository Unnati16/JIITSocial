package com.example.shivangikanchan.navigation.Fragment;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.shivangikanchan.navigation.MyAdapter;
import com.example.shivangikanchan.navigation.MyAdapters;
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

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.shivangikanchan.navigation.MainActivity.getcs;

/**
 * Created by Shivangi Kanchan on 14-11-2016.
 */
public class Suggested extends Fragment {
    Context context;
    int currentuser;
    //UserDbHelper db;;
    //SQLiteDatabase
    FloatingActionButton fab ;
    String datastr[]=null;
    String datast[]=null;
    //String condition = "s";
    EditText e3,e1,e4;
    String cs=null;
    String[] HUBS={"bhbch"};
    AutoCompleteTextView e2;
    RecyclerView rv ;

    String connectivity=null;
    private NotificationManager mNotificationManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.suggested_layout, null);
        context = container.getContext();
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        HUBS = new String[] {
                "Knuth Programming Hub" , "Robotics Hub","GDG","IEEE","CICE","OSDC","Ribose Hub","Parola Literary Hub","Page Turner Society","JCED","Jaypee Economics Hub","Graphicas Animation Hub","Crescendo Hub","Jhankaar Hub","Thespian Hub","Radiance Hub","Jiit Film and Photography","Expressions Painting Hub","JYC","Sports"
        };
//        condition="s";
        String re="s";

        new GetDataTask4().execute("http://192.168.43.31:3000/api/checkcoordinator");
        new GetDataTask2().execute("http://192.168.43.31:3000/api/getnotification2");
        new GetDataTask3().execute("http://192.168.43.31:3000/api/getnotification");

       fab= (FloatingActionButton) v.findViewById(R.id.fab);

        currentuser=getcs();
        cs=Integer.toString(currentuser);
        cs=getData();
        Log.e("ghsas",currentuser+"");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayAdapter<String> adapters = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, HUBS);
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog);

                e2=(AutoCompleteTextView) dialog.findViewById(R.id.editText1);
                e2.setAdapter(adapters);
                e3=(EditText)dialog.findViewById(R.id.editText3);

                Button dialogButton = (Button) dialog.findViewById(R.id.button2);
                Button send=(Button)dialog.findViewById(R.id.button);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new GetDataTask2().execute("http://192.168.43.31:3000/api/getnotification2");
                        new GetDataTask3().execute("http://192.168.43.31:3000/api/getnotification");
                        dialog.dismiss();
                    }
                });
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new GetDataTask().execute("http://192.168.43.31:3000/api/notification");
                        new GetDataTask2().execute("http://192.168.43.31:3000/api/getnotification2");
                        new GetDataTask3().execute("http://192.168.43.31:3000/api/getnotification");
                    }
                });
                dialog.show();

            }
        });
        rv = (RecyclerView)v.findViewById(R.id.rv_recycler_view);
            fab.setVisibility(View.VISIBLE);
  //      }
    //    else{
      //      fab.setVisibility(View.INVISIBLE);
        //}

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);



        return v;
    }

    public void saveData(String s) {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("Suggested", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("data2", s);
        editor.apply();
    }
    public String getData1() {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("Suggested", Context.MODE_PRIVATE);
        String stat = loginData.getString("data2", "");
        return stat;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (datastr!=null)
        {
            MyAdapter adapter = new MyAdapter(context,datastr,datast);
            rv.setAdapter(adapter);
        }

        new GetDataTask2().execute("http://192.168.43.31:3000/api/getnotification2");
        new GetDataTask3().execute("http://192.168.43.31:3000/api/getnotification");
    }

    public String getData() {
        SharedPreferences loginData = this.getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String stat = loginData.getString("status", "");
        return stat;
    }



    private void displayNotification(String extra, String contentTitle, String contentText, Class<?> cls, int id) {
        Notification.Builder builder = new Notification.Builder(getActivity());
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtra("extra", extra);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), id, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);

        Log.e("sdf","on Start1");
        builder.setTicker("this is ticker text");
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        builder.setSmallIcon(R.drawable.messaging);
        builder.setContentIntent(contentIntent);
        builder.setOngoing(true);
        builder.build();
        mNotificationManager.notify(id,builder.getNotification());

    }


    public class GetDataTask4 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMessage("Loading data...");
//            progressDialog.show();
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
//                     condition=connectivity;
                String res="s";
  //              if (condition.equals(res)) {

    //                Toast toast = Toast.makeText(getActivity(), connectivity, Toast.LENGTH_SHORT);
      //              toast.show();
                    fab.setVisibility(View.VISIBLE);
          //      }
        //        else{
            //        fab.setVisibility(View.INVISIBLE);
              //  }
                //Toast toast = Toast.makeText(getActivity(),connectivity, Toast.LENGTH_SHORT);
                //toast.show();

                //String str[]=new String[jarr.length()];



            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

        }
        public String ConvertData() {
            JSONObject root = new JSONObject();
            try {
                root.put("enroll",cs);
                String json=root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }}
    public class GetDataTask2 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMessage("Loading data...");
//            progressDialog.show();
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
                String c="";
                c=getData1();


                JSONArray jarr= null;
                try {
                    jarr = new JSONArray(c);

                String str[]=new String[jarr.length()];
                String st[]=new String[jarr.length()];
                for(int i=0;i<jarr.length();i++)
                {
                    JSONObject j =jarr.getJSONObject(i);
                    //resp[i]=j.toString();
                    String hub = j.getString("hubname") ;
                    String desc=j.getString("description");
                    st[i]=hub;
                    str[i]=desc;
                    //displayNotification("Extra for A",hub,desc,Suggested.class,i);

                    //resp[i]=j.getString("message");
                }
                //Toast toast1 = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                //toast1.show();
                datastr=str;
                datast=st;
                MyAdapters adapter = new MyAdapters(context,st,str);
                rv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast toast = Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                //Toast toast = Toast.makeText(getActivity(),connectivity, Toast.LENGTH_SHORT);
                //toast.show();
                connectivity=connectivity.replace("@@"," ");
                saveData(connectivity);
                JSONObject json= null;
                //String str[]=new String[jarr.length()];
                try {
                    JSONArray jarr=new JSONArray(connectivity);
                    String str[]=new String[jarr.length()];
                    String st[]=new String[jarr.length()];
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j =jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                       String hub = j.getString("hubname") ;
                        String desc=j.getString("description");
                        st[i]=hub;
                        str[i]=desc;
                        //displayNotification("Extra for A",hub,desc,Suggested.class,i);

                        //resp[i]=j.getString("message");
                    }
                    //Toast toast1 = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                    //toast1.show();
                    datastr=str;
                    datast=st;
                    MyAdapters adapter = new MyAdapters(context,st,str);
                    rv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

        }
        public String ConvertData() {
            JSONObject root = new JSONObject();
            try {
                root.put("enroll",cs);
                String json=root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }}
    public class GetDataTask3 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

  //          progressDialog = new ProgressDialog(getActivity());
   //         progressDialog.setMessage("Loading data...");
    //        progressDialog.show();
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
               // Toast toast = Toast.makeText(getActivity(),connectivity, Toast.LENGTH_SHORT);
                //toast.show();
                JSONObject json= null;
                //String str[]=new String[jarr.length()];
                try {
                    connectivity=connectivity.replace("@@"," ");
                    JSONArray jarr=new JSONArray(connectivity);
                    String str[]=new String[jarr.length()];
                    for(int i=0;i<jarr.length();i++)
                    {
                        JSONObject j =jarr.getJSONObject(i);
                        //resp[i]=j.toString();
                        String hub = j.getString("hubname") ;
                        String desc=j.getString("description");
                        //str[i]=desc;
                        displayNotification("Extra for A",hub,desc,Suggested.class,i);

                        //resp[i]=j.getString("message");
                    }
                    //Toast toast1 = Toast.makeText(getActivity(), resp[0], Toast.LENGTH_SHORT);
                    //toast1.show();
                    //MyAdapter adapter = new MyAdapter(context,str);
                    //rv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

        }
        public String ConvertData() {
            JSONObject root = new JSONObject();
            try {
                root.put("enroll",cs);
                String json=root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        //set data response to textView
        // mResult.setText(result);

        //cancel progress dialog
        // if (progressDialog != null) {
        //   progressDialog.dismiss();
    }



    public class GetDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

      //      progressDialog = new ProgressDialog(getActivity());
       //     progressDialog.setMessage("Loading data...");
        //    progressDialog.show();
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
            try {
String ee2=e2.getText().toString();
        String ee3=e3.getText().toString();
                ee2=ee2.replace(" ","@@");
                ee3=ee3.replace(" ","@@");
                root.put("hubname",ee2);
                root.put("description",ee3);
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
   
}



