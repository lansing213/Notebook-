package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Initialize global variables
    private Button creator;
    private LinearLayout linearLayout;
     ListView listView;
      List<ShowList> arrayList = new ArrayList<>();
     //ShowListAdapter ListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.content_list);
        creator = findViewById(R.id.creator);

        arrayList = com.example.myapplication.SharedPreferences.readListViewFromPref(this);

        if(arrayList == null){
            arrayList = new ArrayList<>();
            arrayList.add(new ShowList("ddddddddd"));

        }

        ShowListAdapter arrayAdapter = new ShowListAdapter(this,R.layout.list_item,arrayList);

        listView.setAdapter(arrayAdapter);


        //set up listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowList showList = arrayList.get(i);


                String contextString = showList.getTitle();

                Intent intent = new Intent(MainActivity.this,NotepadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("titleName",contextString);
                bundle.putInt("listNum",i);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });







        //Button to create new listview item
        creator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //arrayAdapter.add(new ShowList(" "));
                //arrayAdapter.notifyDataSetChanged();
                //ShowList showList = arrayList.get(-1);

                Intent intent = new Intent(MainActivity.this,NotepadActivity.class);
                intent.putExtra("double", 2.1);

                intent.putExtra("titleName","");



                startActivityForResult(intent,1);


            }

        });



    }

    //set up the new display of listview item and delete the blank
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setContentView(R.layout.activity_main);
        Button creator = findViewById(R.id.creator);
        ListView listView =  findViewById(R.id.content_list);

        ShowListAdapter arrayAdapter = new ShowListAdapter(this,R.layout.list_item,arrayList);


        listView.setAdapter(arrayAdapter);
        if(resultCode == 1){


                String title = data.getStringExtra("Title");
                int position = data.getIntExtra("ViewNumber",0);
                if(title.isEmpty() == true){
                    arrayList.remove(position);
                    arrayAdapter.notifyDataSetChanged();
                    com.example.myapplication.SharedPreferences.writeListViewInPref(getApplicationContext(),arrayList);




                }
                else {
                    arrayList.set(position, new ShowList(title));
                    arrayAdapter.notifyDataSetChanged();
                    com.example.myapplication.SharedPreferences.writeListViewInPref(getApplicationContext(),arrayList);

                }


        }

        else if(resultCode == 2){
            String title = data.getStringExtra("Title");
            ShowList showList = new ShowList(title);
            arrayAdapter.add(showList);
            arrayAdapter.notifyDataSetChanged();
            com.example.myapplication.SharedPreferences.writeListViewInPref(getApplicationContext(),arrayList);

        }
        else{
            arrayList.add(new ShowList("Something is wrong"));
            arrayAdapter.notifyDataSetChanged();
            com.example.myapplication.SharedPreferences.writeListViewInPref(getApplicationContext(),arrayList);

        }





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowList showList = arrayList.get(i);




                String contextString = showList.getTitle();

                Intent intent = new Intent(MainActivity.this,NotepadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("titleName",contextString);
                bundle.putInt("listNum",i);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        creator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //arrayAdapter.add(new ShowList(" "));
                //arrayAdapter.notifyDataSetChanged();
                //ShowList showList = arrayList.get(-1);

                Intent intent = new Intent(MainActivity.this,NotepadActivity.class);
                intent.putExtra("double", 2.1);

                intent.putExtra("titleName","");



                startActivityForResult(intent,1);

            }

        });

    }

}
