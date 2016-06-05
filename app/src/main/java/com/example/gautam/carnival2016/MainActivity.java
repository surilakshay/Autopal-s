package com.example.gautam.carnival2016;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText editflat, editname, editcont,editph;
    Button btnadd,btnview,btnupdate,btndelete,btnupload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelper(this);

        editflat = (EditText)findViewById(R.id.et1);
        editname = (EditText)findViewById(R.id.et2);
        editcont = (EditText)findViewById(R.id.et3);
        editph = (EditText)findViewById(R.id.et4);
        btnadd = (Button)findViewById(R.id.btn1);
        btnview = (Button)findViewById(R.id.btn2);
        btnupdate = (Button)findViewById(R.id.btn3);
        btndelete = (Button)findViewById(R.id.btn4);
        btnupload = (Button)findViewById(R.id.btn5);
        AddData();
        viewAll();
        update();
        deleteData();
        upload();
    }

    public void AddData(){

        btnadd.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean isInserted = mydb.insertData(editflat.getText().toString(),editname.getText().toString(),editcont.getText().toString(),editph.getText().toString());
                        if(isInserted == true){
                            editflat.setText("");
                            editname.setText("");
                            editcont.setText("");
                            editph.setText("");

                            Toast.makeText(MainActivity.this,"Data Inserted Succesfully!!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Data exists already, you may edit this entry!!",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void viewAll(){

        btnview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor res = mydb.getAllData();
                        if(res.getCount() == 0){

                            //show message
                            showmessage("Error","Nothing Found !! ");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()) {

                            buffer.append("Flat Number : "+res.getString(0)+"\n");
                            buffer.append("Name : "+res.getString(1)+"\n");
                            buffer.append("Contribution : "+res.getString(2)+"\n");
                            buffer.append("Mobile Number : "+res.getString(3)+"\n\n\n");
                        }

                        //show all data
                        showmessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showmessage(String title, String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void update(){
        btnupdate.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Volunteer Password ");
                        //builder.setMessage(Message);
                        final EditText input = new EditText(MainActivity.this);
                        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        // LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        //        LinearLayout.LayoutParams.MATCH_PARENT,
                        //        LinearLayout.LayoutParams.MATCH_PARENT);
                        // input.setLayoutParams(lp);
                        builder.setView(input);
                        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do work
                                String pass = input.getText().toString();
                                String x = new String("karma");
                                if(pass.equals(x)){
                                    boolean isupdated=mydb.updateData(editflat.getText().toString(),editname.getText().toString(),editcont.getText().toString(),editph.getText().toString());
                                    if(isupdated==true){
                                        Toast.makeText(MainActivity.this,"Data Updated Succesfully!!",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"!! Data NOT Inserted !!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"!! Authorisation Failed !!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();

                    }
                }
        );
    }

    public void deleteData(){

        btndelete.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Administrator Password ");
                        //builder.setMessage(Message);
                        final EditText input = new EditText(MainActivity.this);
                        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                       // LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        //        LinearLayout.LayoutParams.MATCH_PARENT,
                        //        LinearLayout.LayoutParams.MATCH_PARENT);
                       // input.setLayoutParams(lp);
                        builder.setView(input);
                        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do work
                                String pass = input.getText().toString();
                                String x = new String("bazinga");
                                if(pass.equals(x)){
                                    Integer deletedrows = mydb.deleteData(editflat.getText().toString());
                                    if (deletedrows>0){

                                        Toast.makeText(MainActivity.this,"Data Deleted Succesfully!!",Toast.LENGTH_LONG).show();
                                    }
                                    else{

                                        Toast.makeText(MainActivity.this,"!! Data NOT Deleted !!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"!! Authorisation Failed !!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();

                    }
                }
        );
    }


    public void upload(){


        btnupload.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            uploadDataClass up = new uploadDataClass();
                        up.execute("");
                    }
                }
        );

    }

    class uploadDataClass extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {

            Log.d("state","1");
            try{

                Cursor res = mydb.getAllData();
                while(res.moveToNext()) {
                    String flatnum = res.getString(0);
                    String name = res.getString(1);
                    String cont = res.getString(2);
                    String mobile = res.getString(3);
                    // Create http cliient object to send request to server
                    BufferedReader reader = null;
                    URL url = new URL("http://skacarnival.16mb.com/?flat="+flatnum+"&name="+name+"&cont="+cont+"&ph="+mobile);
                    HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    Log.d("state","2");
                    connection.connect();
                    // Closing the output stream.
                    Log.d("state","3");

                    // Read the input stream into a String
                    InputStream inputStream = connection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    String forecastJsonStr = buffer.toString();
                    Log.d("input stream",forecastJsonStr);

                    if(forecastJsonStr.equals("ok")){
                        Log.d("ok test","i m here");
                        //mydb.deleteData(flatnum);
                    }
                }

            }catch (Exception e){
                Log. d("erroe=",e.getMessage());
            }
            return null;
        }
    }
}
