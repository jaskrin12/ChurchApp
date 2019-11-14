package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminAssignTeacherClasss extends AppCompatActivity {
    Spinner spinner;
    TextView showname, showClass;
    String Querry;
    DbHandler mydb;
    SQLiteDatabase db ;
    Intent intent;
    Button assignButton;
    String teacherName;
    List<String> arraySpinner;
    List<String> arrayClasses;
//    List<String> arrayTeacherClasses;


    String selecClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_assign_teacher_classs);

        teacherName = getIntent().getStringExtra("Teacher_Name");
        showname=(TextView) findViewById(R.id.showTeacherName);
        showClass=(TextView) findViewById(R.id.showTeacherClass);
        String showNameString="Assign Class to "+teacherName;
        showname.setText(showNameString);

        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        spinner=(Spinner) findViewById(R.id.spinnerid);
        arraySpinner = new ArrayList<String>();
        arrayClasses = new ArrayList<String>();

        showTeacherClass();
        fetchClasses();
//        fetchAvailableClasses();

        arraySpinner.add("No Class");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecClass = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        assignButton=(Button) findViewById(R.id.assignclass);
        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mydb.updateTeacherClass(teacherName, selecClass, db);
                    String showNameString="Teacher is currently assign to :"+selecClass;
                    showClass.setText(showNameString);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showTeacherClass(){
        try{
            Cursor cursor = db.rawQuery("SELECT TNAME ,CNAME FROM TEACHERCLASS WHERE TNAME='"+teacherName+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String cname=cursor.getString(1);
                    String showNameString="Teacher is currently assign to :"+cname;
                    showClass.setText(showNameString);
                } while (cursor.moveToNext());
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
//            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        for (int i=0;i<arrayClasses.size();i++)
//        {
////            Toast.makeText(getApplicationContext(),arrayClasses.get(i),Toast.LENGTH_SHORT).show();
//            arraySpinner.remove(arrayClasses.get(i));
//        }
//    }

    private void fetchClasses(){
        try{
            Cursor cursor = db.rawQuery("SELECT CNAME FROM CLASSES", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {

                    arraySpinner.add(cursor.getString(0));
//                    addClass(tname);
//                    buffer.append("name = " + tname);
                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }
    }
//    private void fetchAvailableClasses(){
//        try{
//            Cursor cursor = db.rawQuery("SELECT CNAME FROM TEACHERCLASS", null);
//            if(cursor!=null) {
//                cursor.moveToFirst();
//                do {
//                    arrayClasses.add(cursor.getString(0));
////                    addClass(tname);
////                    buffer.append("name = " + tname);
//                } while (cursor.moveToNext());
////                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
//            }
//        }
//        catch(Exception e){
//            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//        }
//    }
}
