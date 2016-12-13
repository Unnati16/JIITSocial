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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Shivangi Kanchan on 16-10-2016.
 */

public class Register extends AppCompatActivity {
    EditText name,e,c,pass;
    ArrayAdapter<CharSequence> adapter;

    Spinner spinner1,spinner2;
    TextView tv;
    String otpass;
    Button verify;
    RadioButton rb1,rb2,rb3;
    JSONObject root1=new JSONObject();
    CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20;
    ArrayList<CheckBox> checkBoxes= new ArrayList<CheckBox>();
    private Button buttonSend;
    EditText editTextcpass,otp;
    EditText editTextEmail,edname,edroll,edcontact,edpass,edcpass;
    String name2=null,roll=null,pass2=null,cpass=null,ema=null,contact=null,year=null,branch=null;
    String connectivity=null;
    static int x=0;
    UserDbHelper db;
    SQLiteDatabase sqLiteDatabase,db1;
    RadioGroup rg;
    String value,text,Select="nothing";
    View v;
    static String[] list = new String[20];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.editText3);
        e=(EditText)findViewById(R.id.editText4);
        verify=(Button)findViewById(R.id.button21);
        otp=(EditText)findViewById(R.id.editText9);
        //em=(EditText)findViewById(R.id.editText5);
        c=(EditText)findViewById(R.id.editText6);
        pass=(EditText)findViewById(R.id.editText7);

        spinner1=(Spinner)findViewById(R.id.spinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        spinner2=(Spinner)findViewById(R.id.spinner2);
        adapter=ArrayAdapter.createFromResource(this,R.array.branch,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        buttonSend=(Button)findViewById(R.id.button2);
        rb2=(RadioButton)findViewById(R.id.radioCoordinator);
        editTextEmail = (EditText) findViewById(R.id.editText5);
        rg=(RadioGroup)findViewById(R.id.radioGroup);
        editTextcpass=(EditText)findViewById(R.id.editText8);
        db=new UserDbHelper(this,"new12",null,1);
        Log.e("fff","Database created");
        sqLiteDatabase= db.getReadableDatabase();
        db1=db.getWritableDatabase();
        edname = (EditText) findViewById(R.id.editText3);
        edroll = (EditText) findViewById(R.id.editText4);
        editTextEmail = (EditText) findViewById(R.id.editText5);
        edcontact = (EditText) findViewById(R.id.editText6);
        edpass = (EditText) findViewById(R.id.editText7);
        edcpass = (EditText) findViewById(R.id.editText8);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                list=onRadioButtonClicked(v);
                value = ((RadioButton)findViewById(rg.getCheckedRadioButtonId() )).getText().toString();
                Log.e("bdhd",value+"");
                Context context = getApplicationContext();

            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb2.isChecked()){
                    contact=edcontact.getText().toString();
                    new GetDataTask2().execute("http://192.168.43.31:3000/api/sendotp");

                }else{
                    Toast toast1 = Toast.makeText(getApplicationContext(), "only coordinators need to verify", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                /*
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View promptsView = li.inflate(R.layout.otp12, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                otp=(EditText)promptsView.findViewById(R.id.otp);
                final Dialog dialog = new Dialog(getApplicationContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //dialog.setContentView(R.layout.dialog);



                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Verify",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                       // new GetDataTask2().execute("http://192.168.43.31:3000/api/sendotp");
                                        String ot=otp.getText().toString();
                                         if(otpass.equals(ot)){

                                             buttonSend.setVisibility(View.VISIBLE);
                                             dialog.cancel();
                                         }

                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);

*/
            }

        });



       /* String result_ScoreP1 = ("" + Arrays.asList(Total_Score_p1)).
                replaceAll("(^.|.$)", "  ").replace(", ", "  , " );
        Log.e("id",result_ScoreP1+" ");*/
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // sendEmail();
                String display="";
                ema=editTextEmail.getText().toString();
                year=spinner1.getSelectedItem().toString();
                branch=spinner2.getSelectedItem().toString();
                name2=edname.getText().toString();
                pass2=edpass.getText().toString();
                cpass=edcpass.getText().toString();
                contact=edcontact.getText().toString();
                roll=edroll.getText().toString();
                if(name2.length()==0)
                    display="Enter name";
                else if(!name2.matches("[a-zA-Z ]+")) {
                    display = "Name can contain only alphabets";
                }
                else if(roll.length()==0)
                    display="Enter enrollment no";
                else if(roll.length()!=8)
                    display="Enter valid enrollment no";
                    //  else if(no<=12999999||no>=17000000)
                    //    display="Enter valid enrollment no";
                else if(ema.length()==0)
                    display="Enter email id";
                else if( !isValidEmailAddress(ema))
                    display="Enter valid email id";
                else if(contact.length()==0)
                    display="Enter contact no";
                else if(contact.length()<10)
                    display="Enter valid contact no";
                else if(pass2.length()==0)
                    display="Enter password";
                else if(pass2.length()<6)
                    display="Password should be of minimum length 6";
                else if(cpass.length()==0)
                    display="Enter password in confirm password field";
                else if(!pass2.equals( cpass))
                    display="Both the passwords do not match";
                else if (rg.getCheckedRadioButtonId() == -1)
                    display="Choose one Radio Button";
                else
                    if(!otp.getText().toString().equals(otpass)&&rb2.isChecked()){

                        display="wrong otp";
                    }


                else {
                    display = "complete";
                    new GetDataTask().execute("http://192.168.43.31:3000/api/signup");


                }
                Context context = getApplicationContext();
         //       Toast toast1 = Toast.makeText(context,list[1],Toast.LENGTH_LONG);
           //     toast1.show();

                // CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, display, duration);
                toast.show();

                //if(edpass.getText().toString().contentEquals(edcpass.getText().toString()))
                //{
                   //


                //}
                //else
                //{
                  //  Toast toast1 = Toast.makeText(getApplicationContext(), "password doesn't match", Toast.LENGTH_SHORT);
                    //toast1.show();

                //}


            }
        });
    }
    public boolean isValidEmailAddress(String email1) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email1);
        return m.matches();
    }
    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = "Confirmation mail from JIIT Social";
        String message = "Welcome to JIIT Social. You have successfully registered.";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
        new Syncdata().execute("h");
    }

    public String[] onRadioButtonClicked(View v)
    {
        tv=(TextView)findViewById(R.id.textView2);
        rb1=(RadioButton)findViewById(R.id.radioButtonVolunteer);
        rb2=(RadioButton)findViewById(R.id.radioCoordinator);
        rb3=(RadioButton)findViewById(R.id.radioButtonNo) ;
        c1=(CheckBox)findViewById(R.id.checkbox0);
        c2=(CheckBox)findViewById(R.id.checkbox1);
        c3=(CheckBox)findViewById(R.id.checkbox2);
        c4=(CheckBox)findViewById(R.id.checkbox3);
        c5=(CheckBox)findViewById(R.id.checkbox4);
        c6=(CheckBox)findViewById(R.id.checkbox5);
        c7=(CheckBox)findViewById(R.id.checkbox6);
        c8=(CheckBox)findViewById(R.id.checkbox7);
        c9=(CheckBox)findViewById(R.id.checkbox8);
        c10=(CheckBox)findViewById(R.id.checkbox9);
        c11=(CheckBox)findViewById(R.id.checkbox10);
        c12=(CheckBox)findViewById(R.id.checkbox11);
        c13=(CheckBox)findViewById(R.id.checkbox12);
        c14=(CheckBox)findViewById(R.id.checkbox13);
        c15=(CheckBox)findViewById(R.id.checkbox14);
        c16=(CheckBox)findViewById(R.id.checkbox15);
        c17=(CheckBox)findViewById(R.id.checkbox16);
        c18=(CheckBox)findViewById(R.id.checkbox17);
        c19=(CheckBox)findViewById(R.id.checkbox18);
        c20=(CheckBox)findViewById(R.id.checkbox19);
        if(rb3.isChecked()) {
            tv.setVisibility(View.GONE);

            c1.setVisibility(View.GONE);
            c2.setVisibility(View.GONE);
            c3.setVisibility(View.GONE);
            c4.setVisibility(View.GONE);
            c5.setVisibility(View.GONE);
            c6.setVisibility(View.GONE);
            c7.setVisibility(View.GONE);
            c8.setVisibility(View.GONE);
            c9.setVisibility(View.GONE);
            c10.setVisibility(View.GONE);
            c11.setVisibility(View.GONE);
            c12.setVisibility(View.GONE);
            c13.setVisibility(View.GONE);
            c14.setVisibility(View.GONE);
            c15.setVisibility(View.GONE);
            c16.setVisibility(View.GONE);
            c17.setVisibility(View.GONE);
            c18.setVisibility(View.GONE);
            c19.setVisibility(View.GONE);
            c20.setVisibility(View.GONE);
        }
        if(rb2.isChecked()){
            //verify.setVisibility(View.VISIBLE);
           // buttonSend.setVisibility(View.INVISIBLE);
        }
        if(rb1.isChecked()||rb2.isChecked()) {
            tv.setVisibility(View.VISIBLE);
            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);
            c4.setVisibility(View.VISIBLE);
            c5.setVisibility(View.VISIBLE);
            c6.setVisibility(View.VISIBLE);
            c7.setVisibility(View.VISIBLE);
            c8.setVisibility(View.VISIBLE);
            c9.setVisibility(View.VISIBLE);
            c10.setVisibility(View.VISIBLE);
            c11.setVisibility(View.VISIBLE);
            c12.setVisibility(View.VISIBLE);
            c13.setVisibility(View.VISIBLE);
            c14.setVisibility(View.VISIBLE);
            c15.setVisibility(View.VISIBLE);
            c16.setVisibility(View.VISIBLE);
            c17.setVisibility(View.VISIBLE);
            c18.setVisibility(View.VISIBLE);
            c19.setVisibility(View.VISIBLE);
            c20.setVisibility(View.VISIBLE);
            Log.e("befor","qqqq");
        }
        list=oncheckedbox(v);
        return list;
    }
  /*  public static String[] addInt(String [] series, String newInt){
        //create a new array with extra index
        String[] newSeries = new String[series.length + 1];

        //copy the integers from series to newSeries
        for (int i = 0; i < series.length; i++){
            newSeries[i] = series[i];
            //Log.e("addint","addinteger");
        }
//add the new integer to the last index
        Log.e("addint",newInt+"");
        newSeries[newSeries.length - 1] = newInt;
        Log.e("yooo",newSeries[newSeries.length-1]+"");
        return newSeries;

    }*/
    public String[] oncheckedbox(View v){

        c1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c1.getText().toString();
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    text=text.replace(" ","@@");
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    Log.e("knuth",list[0]+"");
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                    // Log.e("tv",e.getText().toString()+" ");
                }
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c2.getText().toString();
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    text=text.replace(" ","@@");

                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c3.getText().toString();
                    Log.e("checkbox", text + "");
                    text=text.replace(" ","@@");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c4.getText().toString();
                    Log.e("checkbox", text + "");
                    text=text.replace(" ","@@");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");

                } }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c5.getText().toString();
                    Log.e("checkbox", text + "");
                    text=text.replace(" ","@@");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c6.getText().toString();
                    text=text.replace(" ","@@");
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }    }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c7.getText().toString();
                    Log.e("checkbox", text + "");
                    text=text.replace(" ","@@");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        });
        c8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c8.getText().toString();
                    Log.e("checkbox", text + "");
                    text=text.replace(" ","@@");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        });
        c9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c9.getText().toString();
                    text=text.replace(" ","@@");
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }    }
        });
        c10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c10.getText().toString();
                    Log.e("checkbox", text + "");
                    text=text.replace(" ","@@");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        }); c11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c11.getText().toString();
                    Log.e("checkbox", text + "");
                    text=text.replace(" ","@@");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }    }
        });
        c12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c12.getText().toString();
                    text=text.replace(" ","@@");
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        }); c13.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c13.getText().toString();
                    text=text.replace(" ","@@");
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        }); c14.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c14.getText().toString();
                    text=text.replace(" ","@@");
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        });
        c15.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c15.getText().toString();
                    text=text.replace(" ","@@");
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        }); c16.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {


                    text = c16.getText().toString();
                    text=text.replace(" ","@@");
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }    }
        });
        c17.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c17.getText().toString();
                    text=text.replace(" ","@@");
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        });
        c18.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c18.getText().toString();
                    Log.e("checkbox", text + "");
                    text=text.replace(" ","@@");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        });
        c19.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c19.getText().toString();
                    text=text.replace(" ","@@");
                    Log.e("checkbox", text + "");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
                }
            }
        });
        c20.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    text = c20.getText().toString();
                    Log.e("checkbox", text + "");
                    text=text.replace(" ","@@");
                    list[x++]=text;
                    try {
                        root1.put("hubs"+x,text);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    db1.execSQL("UPDATE " + Usercontract.Newuserinfo.HUB_Table + " SET " + Usercontract.Newuserinfo.Enroll + " = " + e.getText().toString() + " WHERE " + Usercontract.Newuserinfo.Hub_Name + "='" + text + "'");
         /*Cursor c= sqLiteDatabase.rawQuery("SELECT "+Usercontract.Newuserinfo.Hub_id+" FROM "+Usercontract.Newuserinfo.HUB_Table+" WHERE "+Usercontract.Newuserinfo.Enroll+"='"+e.getText().toString()+"'",null);
            if (c.moveToFirst()){
                Select = c.getString(c.getColumnIndex(Usercontract.Newuserinfo.Hub_id));
            }
            c.close();
            Log.e("hubid",Select+"");
*/
                }            }
        });

return list;
    }


   class Syncdata extends AsyncTask<String, Void, String> {

       @Override
       protected void onPreExecute() {

           super.onPreExecute();

       }

       @Override
       protected String doInBackground(String... params) {
         String data =  adddata();
           return data;
   }


       @Override
       protected void onPostExecute(String result) {
           super.onPostExecute(result);
       }

       public String adddata() {

           db.insertdata(name.getText().toString(),e.getText().toString(),spinner1.getSelectedItem().toString(),spinner2.getSelectedItem().toString(),editTextEmail.getText().toString(),c.getText().toString(),pass.getText().toString(),value);
        //   Toast.makeText(getBaseContext(),"Data inserted", Toast.LENGTH_LONG).show();
           //Log.e("ddd","data inserted");
           Intent cinemaIntent = new Intent(getApplicationContext(), navigation.class);
           startActivity(cinemaIntent);
           return "Hello";
       }
   }

    class GetDataTask1 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            // progressDialog = new ProgressDialog(Register.this);
            //progressDialog.setMessage("Loading data...");
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                // sendEmail();
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
                //Toast toast = Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT);
                //toast.show();
            } else {
                if (connectivity.compareTo("s") == 1) { //sendEmail();
                    saveData(roll);

                  //  Toast toast = Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT);
                    //toast.show();

                }

            }
            //set data response to textView
            // mResult.setText(result);

            //cancel progress dialog
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
        public  String ConvertData(){
            int h=0;
            try {
                root1.put("count",x);
                root1.put("enroll",roll);
                return root1.toString();
            } catch (JSONException e1) {
                e1.printStackTrace();
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
    }
    class GetDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

           // progressDialog = new ProgressDialog(Register.this);
            //progressDialog.setMessage("Loading data...");
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
               // sendEmail();
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
                if (connectivity.compareTo("s") == 1) { //sendEmail();
                    saveData(roll);

                    Toast toast = Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT);
                    toast.show();
                    new GetDataTask1().execute("http://192.168.43.31:3000/api/signuphubs");
                    sendEmail();
                }

            }
            //set data response to textView
            // mResult.setText(result);

            //cancel progress dialog
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
        public  String ConvertData(){
            int h=0;
            Log.e("jason",root1.toString());
            JSONObject root =new JSONObject();
            try {name2=name2.replace(" ","@@");
                root.put("name",name2);
                root.put("roll",roll);
                root.put("email",ema);
                root.put("contact",contact);
                root.put("password",pass2);
                root.put("year",year);
                root.put("branch",branch);
                root.put("relation",value);
                String json=root.toString();
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
    }
    public void saveData(String s){
        SharedPreferences loginData = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("status", s);
        editor.apply();
    }

    public String getData(){
        SharedPreferences loginData = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String stat = loginData.getString("status", "");
        return stat;
    }
    class GetDataTask2 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            // progressDialog = new ProgressDialog(Register.this);
            //progressDialog.setMessage("Loading data...");
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                // sendEmail();
                return getData(params[0]);
            } catch (IOException ex) {
                return null;
            }
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            connectivity=result;
            if(connectivity==null)
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{

                Toast toast = Toast.makeText(getApplicationContext(), connectivity, Toast.LENGTH_SHORT);
                toast.show();

            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
        public  String ConvertData(){
            int h=0;
           // Log.e("jason",root1.toString());
            JSONObject root =new JSONObject();
            String[] ot={"7703","7307","8907","0077"};
            Random r= new Random();

            int ran=r.nextInt(4);

            try {String otpa=ot[ran];
                otpass=otpa;
                root.put("otp",otpa);
                root.put("contact",contact);
                String json=root.toString();
                return json.toString();
            } catch (JSONException e) {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        x=0;
    }
}

