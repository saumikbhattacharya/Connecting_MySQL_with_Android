package saubhattacharya.stationarymanager.com;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class Edit_Item extends AppCompatActivity implements Serializable{

    String item_position,item_id,item_name,item_qty;
    int position,item_quantity;
    ArrayList<ListRowItem> listitem = new ArrayList<>();
    ListRowItem currentlistitem;
    EditText edit_item_qty_val;
    JSONParser jsonParser;
    String jsonresponse,response_code;
    JSONObject jsonObject;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        item_position = extras.getString(Inventory_List.CRRNT_ROW_NUMBER);
        listitem = (ArrayList<ListRowItem>)extras.getSerializable(Inventory_List.LISTITEM);

        position = Integer.parseInt(item_position);
        currentlistitem = listitem.get(position);

        item_id = currentlistitem.getItemId();
        item_name = currentlistitem.getItemName();
        item_qty = currentlistitem.getItemQty();

        item_quantity = Integer.parseInt(item_qty);

        TextView edit_item_id = (TextView)findViewById(R.id.edit_item_id);
        edit_item_id.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView edit_item_name = (TextView)findViewById(R.id.edit_item_name);
        edit_item_name.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView edit_item_qty = (TextView)findViewById(R.id.edit_item_qty);
        edit_item_qty.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView edit_item_id_val = (TextView)findViewById(R.id.edit_item_id_val);
        edit_item_id_val.setGravity(Gravity.CENTER_HORIZONTAL);
        edit_item_id_val.setText(item_id);
        TextView edit_item_name_val = (TextView)findViewById(R.id.edit_item_name_val);
        edit_item_name_val.setGravity(Gravity.CENTER_HORIZONTAL);
        edit_item_name_val.setText(item_name);
        edit_item_qty_val = (EditText)findViewById(R.id.edit_item_qty_val);
        edit_item_qty_val.setText(item_qty);
        edit_item_qty_val.setGravity(Gravity.CENTER);
        final Button num_dec = (Button)findViewById(R.id.NumDec);
        num_dec.setGravity(Gravity.CENTER);
        num_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_quantity = Integer.parseInt(edit_item_qty_val.getText().toString());
                if (item_quantity == 0) {
                    num_dec.setClickable(false);
                } else {
                    item_quantity = item_quantity - 1;
                    item_qty = String.valueOf(item_quantity);
                    edit_item_qty_val.setText(item_qty);
                }
            }
        });
        final Button num_inc = (Button)findViewById(R.id.NumInc);
        num_inc.setGravity(Gravity.CENTER);
        num_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_quantity = Integer.parseInt(edit_item_qty_val.getText().toString());
                if (item_quantity == 100) {
                    num_inc.setClickable(false);
                } else {
                    item_quantity = item_quantity + 1;
                    item_qty = String.valueOf(item_quantity);
                    edit_item_qty_val.setText(item_qty);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action_settings = menu.findItem(R.id.action_settings);
        MenuItem action_refresh_list = menu.findItem(R.id.action_refresh_list);
        MenuItem action_save = menu.findItem(R.id.action_save);
        MenuItem action_cancel = menu.findItem(R.id.action_cancel);
        action_settings.setVisible(false);
        action_refresh_list.setVisible(false);
        action_save.setVisible(true);
        action_cancel.setVisible(true);
        MenuItem action_logout = menu.findItem(R.id.action_logout);
        action_logout.setVisible(false);
        MenuItem action_export = menu.findItem(R.id.action_export);
        action_export.setVisible(false);
        MenuItem action_show_cart = menu.findItem(R.id.action_show_cart);
        action_show_cart.setVisible(false);
        MenuItem action_about_us = menu.findItem(R.id.about);
        action_about_us.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        intent = new Intent(getApplicationContext(),Inventory_List.class);

        if ((id == R.id.action_save)||(id == 2131558566)) {
            item_qty = edit_item_qty_val.getText().toString();
            MakeRequestTask edit_item = new MakeRequestTask();
            edit_item.execute();
        }
        if ((id == R.id.action_cancel)||(id == 2131558567)) {
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class MakeRequestTask extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected Void doInBackground(Void... params) {

            try
            {
                URL url = new URL("http://192.168.0.103:8383//android_connect/edit_item.php");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("item_id", item_id)
                        .appendQueryParameter("item_qty", item_qty);
                String data = builder.build().getEncodedQuery();

                jsonParser = new JSONParser();
                jsonresponse = jsonParser.makeHTTPRequest(url,"POST",data);

                jsonObject = new JSONObject(jsonresponse);
                response_code = jsonObject.getString("response_code");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result)
        {
            if("999".equals(response_code)){
                Toast.makeText(Edit_Item.this,
                        "Something went wrong here!", Toast.LENGTH_LONG).show();
            }
            else if("200".equals(response_code)) {
                Toast.makeText(getApplicationContext(),"Item has been successfully updated.", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        }
    }
}
