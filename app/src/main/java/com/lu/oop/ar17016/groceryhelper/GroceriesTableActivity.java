package com.lu.oop.ar17016.groceryhelper;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GroceriesTableActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GroceriesTableActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_view);

        myDB = new DatabaseHelper(this);

        // displays the list of items:
        final TableLayout tableLayout = findViewById(R.id.GroceryList);

        Cursor data = myDB.getListContents();

        if(data.getCount() == 0) {
            Toast.makeText(GroceriesTableActivity.this, "No records found", Toast.LENGTH_SHORT).show();
        } else {
            while(data.moveToNext()){
                GroceryItem item = new GroceryItem(
                        data.getInt(0),
                        data.getString(1),
                        data.getString(2)
                );

                TableRow row = item.getRaw(this);

                // setting onclick per each row:
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GroceriesTableActivity.this, GroceryItemActivity.class);
                        intent.putExtra("id", v.getId());
                        startActivity(intent);
                    }
                });

                tableLayout.addView(row);
            }
        }
    }
}
