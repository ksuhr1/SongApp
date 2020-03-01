package com.example.musicplayer;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    DatabaseHelper myDB;
    Button btnAdd, btnView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editText  = (EditText) findViewById(R.id.editText);

        myDB =  new DatabaseHelper(this);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Main3Activity.this, ViewListContents.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                if(editText.length() != 0)
                {
                    AddData(input);
                    editText.setText("");
                }
                else
                {
                    Toast.makeText(Main3Activity.this, "You must add song title", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void AddData(String  input)
    {
        boolean insertData = myDB.addData(input);
        if(insertData == true)
        {
            Toast.makeText(Main3Activity.this, "Successfully added a song!", Toast.LENGTH_SHORT);
        }
        else
        {
            Toast.makeText(Main3Activity.this, "Something went wrong!", Toast.LENGTH_SHORT);
        }
    }
}
