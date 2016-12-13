package com.example.shivangikanchan.navigation;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    android.support.v4.app.FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    int cs=0;
    private static Bitmap Image = null;
    private static Bitmap rotateImage = null;
    private ImageView imageView;
    private static final int GALLERY = 1;
    final int PIC_CROP = 1;
    String cs1="";
    String c1[]={"Knuth Programming Hub","Robotics Hub","IEEE","GDG","CICE","OSDC"};
    String c2[]={"JEB","JCED","Parola Literary Hub"};
    String c3[]={"Thespian Hub","Crescendo Hub","JPEG","Jhankaar Hub","Ribose Hub","Painting Hub","Graphicas Animation Hub"};


    String connectivity="";
    String s[];
    Intent intent;
    EditText userinput,userinput1,userinput2;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);
        //startActivity(new Intent(this,RecyclerViewTest.class));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Intent i=this.getIntent();
        // cs=i.getExtras().getInt("id");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.shitstuff);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        imageView = (ImageView) headerview.findViewById(R.id.imageView);
        //LinearLayout header = (LinearLayout) headerview.findViewById(R.id.header);
        imageView.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             imageView.setImageBitmap(null);
                                             if (Image != null)
                                                 Image.recycle();
                                             intent = new Intent();
                                             intent.setType("image/*");
                                             intent.setAction(Intent.ACTION_GET_CONTENT);
                                             startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
                                         }
                                     }
        );


     cs1=getData();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY && resultCode != 0) {
            Uri mImageUri = data.getData();

            try {
                Image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                if (getOrientation(getApplicationContext(), mImageUri) != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(getOrientation(getApplicationContext(), mImageUri));
                    if (rotateImage != null)
                        rotateImage.recycle();
                    rotateImage = Bitmap.createBitmap(Image, 0, 0, Image.getWidth(), Image.getHeight(), matrix,true);
                    imageView.setImageBitmap(rotateImage);
                } else
                    imageView.setImageBitmap(Image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION },null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
    public void saveData2(String s) {
        SharedPreferences loginData = getSharedPreferences("Suggested", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("data2", s);
        editor.apply();
    }
    public class GetDataTask2 extends AsyncTask<String, Void, String> {

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
                Toast toast = Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
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
                root.put("to",cs1);
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void saveData4(String s) {
        SharedPreferences loginData = getSharedPreferences("NewsInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("data3", s);
        editor.apply();
    }
    public void saveData1(String s) {
        SharedPreferences loginData = getSharedPreferences("Message", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("data1", s);
        editor.apply();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            saveData("logout");
            saveData1("logout");
            saveData2("logout");
            saveData("logout");
            Intent cinemaIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(cinemaIntent);

            return true;
        }
        if (id == R.id.subscribed) {

            Intent cinemaIntent = new Intent(getApplicationContext(), Subscribed1.class);
            startActivity(cinemaIntent);

            return true;
        }
        if(id==R.id.password)
        {

            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.password, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);


             userinput = (EditText) promptsView.findViewById(R.id.old);

             userinput1 = (EditText) promptsView.findViewById(R.id.new1);
             userinput2 = (EditText) promptsView.findViewById(R.id.confirm);
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //dialog.setContentView(R.layout.dialog);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Save",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if(userinput1.getText().toString().equals(userinput2.getText().toString())){
                                        new GetDataTask().execute("http://192.168.43.31:3000/api/changepass");
                                    }else{
                                        Toast toast = Toast.makeText(getApplicationContext(), "new password doesnt match", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }

                                    }
                                }
                                    // get user input and set it to result




                            )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();


                                }
                            });




            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);


            return true;
        }
        if (id == R.id.recommended) {
            String[] y=new String[4];
            // String y[]={"thespian","knuth","ieee"};
            String[] x=new String[3];
            y=getcount();

            x=recommend(y);


            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.reco, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);


            final TextView userInput = (TextView) promptsView
                    .findViewById(R.id.textView1);

            TextView userInput1 = (TextView) promptsView
                    .findViewById(R.id.textView11);
            TextView userInput2 = (TextView) promptsView
                    .findViewById(R.id.textView111);
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //dialog.setContentView(R.layout.dialog);

            userInput.setText(x[0]);
            userInput1.setText(x[1]);
            userInput2.setText(x[2]);

            userInput.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    String text = userInput.getText().toString();
                    if (text.equals("Knuth Programming Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), knuth.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Robotics Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), robotics.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("GDG")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), gdg.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("IEEE")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), ieee.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("CICE")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), cice.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("OSDC")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), osdc.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Ribose Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), ribose.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Parola Literary Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), parola.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Pageturner Society")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), pts.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JCED")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jced.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JEB")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jeb.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Graphicas Animation Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), graphicas.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Crescendo Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), crescendo.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Jhankaar Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jhankaar.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Thespian Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), thespian.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Radiance Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), radiance.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JPEG")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jpeg.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Painting Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), rangoli.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JYC")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jyc.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Sports")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), sports.class);
                        startActivity(cinemaIntent);
                    }

                }
            });
            userInput1.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    String text = userInput.getText().toString();
                    if (text.equals("Knuth Programming Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), knuth.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Robotics Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), robotics.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("GDG")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), gdg.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("IEEE")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), ieee.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("CICE")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), cice.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("OSDC")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), osdc.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Ribose Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), ribose.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Parola Literary Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), parola.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Pageturner Society")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), pts.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JCED")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jced.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JEB")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jeb.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Graphicas Animation Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), graphicas.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Crescendo Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), crescendo.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Jhankaar Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jhankaar.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Thespian Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), thespian.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Radiance Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), radiance.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JPEG")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jpeg.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Painting Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), rangoli.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JYC")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jyc.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Sports")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), sports.class);
                        startActivity(cinemaIntent);
                    }

                }
            });
            userInput2.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    String text = userInput.getText().toString();
                    if (text.equals("Knuth Programming Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), knuth.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Robotics Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), robotics.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("GDG")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), gdg.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("IEEE")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), ieee.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("CICE")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), cice.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("OSDC")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), osdc.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Ribose Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), ribose.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Parola Literary Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), parola.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Pageturner Society")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), pts.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JCED")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jced.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JEB")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jeb.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Graphicas Animation Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), graphicas.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Crescendo Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), crescendo.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Jhankaar Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jhankaar.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Thespian Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), thespian.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Radiance Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), radiance.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JPEG")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jpeg.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Painting Hub")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), rangoli.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("JYC")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), jyc.class);
                        startActivity(cinemaIntent);
                    }
                    if (text.equals("Sports")) {
                        Intent cinemaIntent = new Intent(getApplicationContext(), sports.class);
                        startActivity(cinemaIntent);
                    }

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);


            return true;
        }
        if (id == R.id.aboutus) {

            Intent cinemaIntent = new Intent(getApplicationContext(), aboutus.class);
            startActivity(cinemaIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected String[] recommend(String s[])
    {

        int i,j,f1=-1,f2=-1,f3=-1,t=0;
        String rec[]={"","","","","","","","","","","","","","","","","","","",""};
        for(i=0;i<s.length;i++)
        {
            for(j=0;j<c1.length;j++)
            {
                if(s[i].equals(c1[j]))
                {
                    f1=1;


                    break;
                }
            }
            for(j=0;j<c2.length;j++)
            {
                if(s[i].equals(c2[j]))
                {
                    f2=1;

                    break;
                }
            }
            for(j=0;j<c3.length;j++)
            {
                if(s[i].equals(c3[j]))
                {
                    f3=1;

                    break;
                }
            }
        }
        if(f1!=-1) {

            for (i = 0; i < c1.length; i++)
            {

                rec[t++] = c1[i];
            }
        }
        if(f2!=-1) {

            for (i = 0; i < c2.length; i++)
            {

                rec[t++] = c2[i];
            }
        }
        if(f3!=-1) {

            for (i = 0; i < c3.length; i++)

            {
                rec[t++] = c3[i];


            }
        }
        for(i=0;i<3;i++)
        {

            Log.e("hello",rec[i]);
        }
        return rec;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int[] ar=new int[4];
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_knuth) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter = prefs.getInt("knuth", 0);

            counter++;

            editor.putInt("knuth", counter);
            editor.commit();
            Intent cinemaIntent = new Intent(this, knuth.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_robotics) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter1 = prefs.getInt("robotics", 0);

            counter1++;

            editor.putInt("robotics", counter1);
            editor.commit();
          //  ar = getcount();
            //Log.e("arraycount",Integer.toString(ar[0]));
            Toast toast = Toast.makeText(getApplicationContext(),Integer.toString(ar[0]), Toast.LENGTH_SHORT);
            toast.show();
            Intent cinemaIntent = new Intent(this, robotics.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_gdg) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter2 = prefs.getInt("gdg", 0);

            counter2=counter2+1;

            editor.putInt("gdg", counter2);
            editor.commit();
            Intent cinemaIntent = new Intent(this, gdg.class);
            startActivity(cinemaIntent);


        } else if (id == R.id.nav_ieee) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter3 = prefs.getInt("ieee", 0);

            counter3++;

            editor.putInt("ieee", counter3);
            editor.commit();
            Intent cinemaIntent = new Intent(this, ieee.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_cice) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter4 = prefs.getInt("cice", 0);

            counter4++;

            editor.putInt("cice", counter4);
            editor.commit();
            Intent cinemaIntent = new Intent(this, cice.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_osdc) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter5 = prefs.getInt("osdc", 0);

            counter5++;

            editor.putInt("osdc", counter5);
            editor.commit();
            Intent cinemaIntent = new Intent(this, osdc.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_ribose) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter6 = prefs.getInt("ribose", 0);

            counter6++;

            editor.putInt("ribose", counter6);
            editor.commit();
            Intent cinemaIntent = new Intent(this, ribose.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_parola) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter7 = prefs.getInt("parola", 0);

            counter7++;

            editor.putInt("parola", counter7);
            editor.commit();
            Intent cinemaIntent = new Intent(this, parola.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_pts) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter8 = prefs.getInt("pts", 0);

            counter8++;

            editor.putInt("pts", counter8);
            editor.commit();
            Intent cinemaIntent = new Intent(this, pts.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_jced) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter9 = prefs.getInt("jced", 0);

            counter9++;

            editor.putInt("jced", counter9);
            editor.commit();
            Intent cinemaIntent = new Intent(this, jced.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_jeb) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter10 = prefs.getInt("jeb", 0);

            counter10++;

            editor.putInt("jeb", counter10);
            editor.commit();
            Intent cinemaIntent = new Intent(this, jeb.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_graphicas) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter11 = prefs.getInt("graficas", 0);

            counter11++;

            editor.putInt("graficas", counter11);
            editor.commit();
            Intent cinemaIntent = new Intent(this, graphicas.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_crescendo) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter12 = prefs.getInt("crescendo", 0);

            counter12++;

            editor.putInt("crescendo", counter12);
            editor.commit();
            Intent cinemaIntent = new Intent(this, crescendo.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_dance) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter13 = prefs.getInt("jhankaar", 0);

            counter13++;

            editor.putInt("jhankaar", counter13);
            editor.commit();
            Intent cinemaIntent = new Intent(this, jhankaar.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_thespian) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter14 = prefs.getInt("thespian", 0);

            counter14++;

            editor.putInt("thespian", counter14);
            editor.commit();
            Intent cinemaIntent = new Intent(this, thespian.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_jpeg) {SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter15 = prefs.getInt("jiitph", 0);

            counter15++;

            editor.putInt("jiitph", counter15);
            editor.commit();
            Intent cinemaIntent = new Intent(this, jpeg.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_radiance) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter16 = prefs.getInt("radiance", 0);

            counter16++;

            editor.putInt("radiance", counter16);
            editor.commit();
            Intent cinemaIntent = new Intent(this, radiance.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_rangoli) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter17 = prefs.getInt("painting", 0);

            counter17++;

            editor.putInt("painting", counter17);
            editor.commit();
            Intent cinemaIntent = new Intent(this, rangoli.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_jyc) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter18 = prefs.getInt("jyc", 0);

            counter18++;

            editor.putInt("jyc", counter18);
            editor.commit();
            Intent cinemaIntent = new Intent(this, jyc.class);
            startActivity(cinemaIntent);
        } else if (id == R.id.nav_sports) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int counter19 = prefs.getInt("sports", 0);

            counter19++;

            editor.putInt("sports", counter19);
            editor.commit();
            Intent cinemaIntent = new Intent(this, sports.class);
            startActivity(cinemaIntent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            connectivity = result;
            if (connectivity == null) {

                Toast toast = Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), connectivity, Toast.LENGTH_SHORT);
                toast.show();
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
                root.put("enroll", cs1);
                root.put("old", userinput.getText().toString());
                root.put("newpass", userinput1.getText().toString());
                String json = root.toString();
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


    }
    public String[] getcount() {
        int[] array = new int[20];
        String x[]=new String[4];
        int[] ar = new int[4];
        String[] str = {"Knuth Programming Hub", "Robotics Hub", "GDG", "IEEE", "CICE", "OSDC", "Ribose Hub", "Parola Literary Hub", "Pageturner Society", "JCED", "JEB", "Graphicas Animation Hub", "Crescendo Hub", "Jhankaar Hub", "Thespian Hub", "Radiance Hub", "JPEG", "Painting Hub", "JYC", "Sports"};
        int max = 0, index,k=0, i,l=0;
        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        int counter = prefs.getInt("knuth", 0);
        array[l++]=counter;

        int counter1 = prefs.getInt("robotics", 0);
        //Log.e("shared",Integer.toString(counter1));
        int counter2 = prefs.getInt("gdg", 0);
        int counter3 = prefs.getInt("ieee", 0);
        int counter4 = prefs.getInt("cice", 0);
        int counter5 = prefs.getInt("osdc", 0);
        int counter6 = prefs.getInt("ribose", 0);
        int counter7 = prefs.getInt("parola", 0);
        int counter8 = prefs.getInt("pageturner", 0);
        int counter9 = prefs.getInt("jced", 0);
        int counter10 = prefs.getInt("jeb", 0);
        int counter11 = prefs.getInt("graficas", 0);
        int counter12 = prefs.getInt("crescendo", 0);
        int counter13 = prefs.getInt("jhankaar", 0);
        int counter14 = prefs.getInt("thespian", 0);
        int counter15 = prefs.getInt("radiance", 0);
        int counter16 = prefs.getInt("jiitph", 0);
        int counter17 = prefs.getInt("painting", 0);
        int counter18 = prefs.getInt("jyc", 0);
        int counter19 = prefs.getInt("sports", 0);
        array[l++]=counter1;
        array[l++]=counter2;
        array[l++]=counter3;
        array[l++]=counter4;
        array[l++]=counter5;
        array[l++]=counter6;
        array[l++]=counter7;
        array[l++]=counter8;
        array[l++]=counter9;
        array[l++]=counter10;
        array[l++]=counter11;
        array[l++]=counter12;
        array[l++]=counter13;
        array[l++]=counter14;
        array[l++]=counter15;
        array[l++]=counter16;
        array[l++]=counter17;
        array[l++]=counter18;
        array[l++]=counter19;
        Log.e("array",Integer.toString(array[0]));
        Log.e("array1",Integer.toString(array[1]));
        Log.e("array2",Integer.toString(array[2]));
        for (int j = 0; j < 4; j++) {
            max = array[0];
            index = 0;
            for (i = 1; i < array.length; i++) {
                if (max < array[i]) {
                    max = array[i];
                    index = i;
                }
            }
            ar[j] = index;
            //array[index] = Integer.MIN_VALUE;
            //x[j++]=str[i];
        }
        for (i = 0; i < 4; i++) {
            index = ar[i];
            Log.e("index",Integer.toString(index));
            Log.e("str",str[index]);
            x[k++] = str[index];
        }
        return x;
    }
}
