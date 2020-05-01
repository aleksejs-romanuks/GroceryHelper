package com.lu.oop.ar17016.groceryhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GroceryItemActivity extends AppCompatActivity {
    Intent intent;
    EditText txtGrocery;
    Spinner dlQuantity;
    Button btnEdit;
    Button btnDelete;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorcery_interaction);

        myDB = new DatabaseHelper(this);
        intent = getIntent();
        txtGrocery = findViewById(R.id.i_grocery_name);
        dlQuantity = findViewById(R.id.i_spinner);
        btnEdit = findViewById(R.id.i_edit);
        btnDelete = findViewById(R.id.i_delete);

        final Integer id = intent.getIntExtra("id", 0);
        final GroceryItem item = myDB.getById(id);

        // setting item text:
        txtGrocery.setText(item.name);

        // setting quantity list:
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(GroceryItemActivity.this, R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dlQuantity.setAdapter(adapter);
        dlQuantity.setSelection(adapter.getPosition(item.quantity));

        // delete on click:
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData(id);
                Intent intent = new Intent(GroceryItemActivity.this, GroceriesTableActivity.class);
                startActivity(intent);
            }
        });

        // edit on click:
        btnEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGrocery = txtGrocery.getText().toString();
                String newQuantity = dlQuantity.getSelectedItem().toString();

                if (!newGrocery.equals(item.name) || !newQuantity.equals(item.quantity)){
                    UpdateData(item.id, newGrocery, newQuantity);
                    Intent intent = new Intent(GroceryItemActivity.this, GroceriesTableActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(GroceryItemActivity.this, "Nothing to change", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void UpdateData(Integer id, String name, String quantity){
        boolean updateExistingRaw = myDB.updateExistingRaw(id, name, quantity);
        if (updateExistingRaw){
            Toast.makeText(GroceryItemActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(GroceryItemActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteData(Integer id){
        boolean insertData = myDB.deleteById(id);
        if (insertData){
            Toast.makeText(GroceryItemActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(GroceryItemActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
