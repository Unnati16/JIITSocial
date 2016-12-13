package com.example.shivangikanchan.navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivangikanchan.navigation.Fragment.YearBook;

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
 * Created by Richa on 30-Nov-16.
 */

public class listview extends AppCompatActivity {
    String[] mobileArray = {"Item: hii","Item: hello","Item: getlost","Item: bye"};
    ArrayList<String> arr=new ArrayList<>();
    TextView t1;
    String connectivity;
    String datast[]=null,datastr[]=null,dataname1[]=null,datapost[]=null;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        // new GetDataTask3().execute("http://192.168.43.144:3000/api/showyearbook");
        Intent i = this.getIntent();
        name = i.getExtras().getString("DATA");

        Bundle bundle=new Bundle();
        //String[][] dataset=(String[][])bundle.getSerializable("names");
        //String dataset1[][]=(String[][])i.getSerializableExtra("names");
        // int p=Integer.parseInt(pos);
        //  String post=dataset[pos][0];
        // String[] views=new String[20];
        Bundle b=getIntent().getExtras();
        String[][] nm=(String[][])b.getSerializable("names");
        // Log.e("un",nm[0][0]);
        String pos = i.getExtras().getString("position");
        Bundle b1=getIntent().getExtras();
        String[][] post=(String[][])b1.getSerializable("posts");
        //String p=Integer.toString(pos);
        int p=Integer.parseInt(pos);
        String pp=Integer.toString(p);
        Log.e("unnati",pp);
        Log.e("fff","unn");
        int k=0;
        //  Log.e("get",dataset[0][0]);
        //for(int j=0;j<dataset[pos].length;j++)
        //{  String s=dataset[pos][j];
        //  Log.e("post",s);
        //arr.add(dataset[pos][j]);
        //}
        String str;
        for(int o = 0; o<nm[p].length; o++)
        {    if(nm[p][o]!=null) {
            str = nm[p][o] + ": " + post[p][o];
            arr.add(str);
        }
        }
        int l=nm.length;
        String len=Integer.toString(l);
        Log.e("len",len);

        int l1=nm[p].length;
        String len1=Integer.toString(l1);
        Log.e("len1",len1);

        t1=(TextView)findViewById(R.id.label);
        t1.setText(name);
        //  t1.setText("Unnati");

        // String p=Integer.toString(pos);
        //Log.e("unnati",p);
        //Log.e("hoi",post);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list1, arr);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

    }



}
