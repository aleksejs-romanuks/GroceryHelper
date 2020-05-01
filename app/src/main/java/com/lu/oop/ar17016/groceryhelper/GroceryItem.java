package com.lu.oop.ar17016.groceryhelper;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

class GroceryItem {
    Integer id;
    String name;
    String quantity;

    GroceryItem(Integer item_id, String item_name, String item_quantity){
        id=item_id;
        name=item_name;
        quantity=item_quantity;
    }

    TableRow getRaw(Context context){
        TableRow.LayoutParams paramsExample = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,  1.0f);

        TableRow row = new TableRow(context);
        row.setId(id);
        row.setClickable(true);

        row.setLayoutParams(paramsExample);

        TextView item_name = new TextView(context);
        TextView item_quantity = new TextView(context);

        item_name.setText(this.name);
        item_name.setLayoutParams(paramsExample);

        item_quantity.setText(this.quantity);
        item_quantity.setLayoutParams(paramsExample);

        row.addView(item_name);
        row.addView(item_quantity);

        return row;
    }


}
