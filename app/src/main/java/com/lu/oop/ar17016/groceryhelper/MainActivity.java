package com.lu.oop.ar17016.groceryhelper;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtGrocery;
    Spinner dlQuantity;
    Button btnAdd;
    Button btnView;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB  = new DatabaseHelper(this);
        dlQuantity = findViewById(R.id.quantity);
        txtGrocery = findViewById(R.id.newGroceryText);
        txtGrocery.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        btnAdd = findViewById(R.id.addGrocery);
        btnView = findViewById(R.id.viewGroceryList);

        // setting quantity list:
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dlQuantity.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGrocery = txtGrocery.getText().toString();
                String quantity = dlQuantity.getSelectedItem().toString();

                if (newGrocery.length() > 0 ) {
                    AddData(newGrocery, quantity);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a grocery name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GroceriesTableActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String name, String quantity){
        boolean insertData = myDB.addData(name, quantity);
        if (insertData){
            Toast.makeText(MainActivity.this, name + " was added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
