package com.trafengineproject.owner.traff_engine;

/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class SearchRoute extends AppCompatActivity {
    private EditText sourcetext, destinationtext;
    private Button searchbuton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_route);


        sourcetext = (EditText) findViewById(R.id.srctxt);


        destinationtext = (EditText) findViewById(R.id.destxt);



        searchbuton = (Button) findViewById(R.id.searchbttn);

        searchbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_text = sourcetext.getText().toString();

                String d_text= destinationtext.getText().toString();

                Intent i = new Intent(SearchRoute.this, AlternativeRouteMaps.class);
                String texttosend_1 = s_text;
                String texttosend_2 = d_text;
                //i.putExtra("add_key", true);

                i.putExtra("ad_txt_1", texttosend_1);
                i.putExtra("ad_txt_2", texttosend_2);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(" Are you sure you want to exit? ")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent i = new Intent(SearchRoute.this, Homepage.class);
                                startActivity(i);
                            }
                        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}
