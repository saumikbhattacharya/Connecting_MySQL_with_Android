package saubhattacharya.stationarymanager.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class Inventory_List extends AppCompatActivity implements Serializable{

    JSONParser jsonParser;
    String jsonresponse,response_code,str_item_id;
    String[] item_id = new String[30];
    String[] item_name = new String[30];
    String[] item_quantity = new String[30];
    JSONObject jsonObject;
    int i,id_num;
    ArrayList<ListRowItem> listitem = new ArrayList<>();
    ListRowItem lr;
    ListView listView;
    MyBaseAdapter myBaseAdapter;
    public final static String CRRNT_ROW_NUMBER = "saubhattacharya.stationarymanager.com.CRRNT_ROW_NUMBER";
    public final static String LISTITEM = "saubhattacharya.stationarymanager.com.LISTITEM";
    public final static String ID_NUM = "saubhattacharya.stationarymanager.com.ID_NUM";
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory__list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ShowItemsTask show_all_items = new ShowItemsTask();
        show_all_items.execute();
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
        action_save.setVisible(false);
        action_cancel.setVisible(false);
        MenuItem action_logout = menu.findItem(R.id.action_logout);
        action_logout.setVisible(true);
        MenuItem action_export = menu.findItem(R.id.action_export);
        action_export.setVisible(true);
        MenuItem action_show_cart = menu.findItem(R.id.action_show_cart);
        action_show_cart.setVisible(false);
        MenuItem action_about_us = menu.findItem(R.id.about);
        action_about_us.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ((id == R.id.action_logout) || (id == 2131558568))
        {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("StationeryManagerPref",0);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();
            editor.commit();

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        if ((id == R.id.action_export) || (id == 2131558576))
        {
            Intent intent = new Intent(getApplicationContext(),Export.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class ShowItemsTask extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                URL url = new URL("http://192.168.0.103:8383//android_connect/show_all_items.php");

                jsonParser = new JSONParser();
                jsonresponse = jsonParser.makeHTTPRequest(url, "GET", null);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result)
        {
            try
            {
                jsonObject = new JSONObject(jsonresponse);
                response_code = jsonObject.getString("response_code");
                if("999".equals(response_code)){
                    Toast.makeText(Inventory_List.this,
                            "Something went wrong here!", Toast.LENGTH_LONG).show();
                }
                else if("200".equals(response_code)) {
                    JSONArray jsonArray = jsonObject.optJSONArray("itemdata");
                    for (i=0 ; i<jsonArray.length() ; i++)
                    {
                        JSONObject inner_jsonObject = jsonArray.getJSONObject(i);
                        item_id[i] = inner_jsonObject.getString("item_id");
                        str_item_id = inner_jsonObject.getString("item_id");
                        item_name[i] = inner_jsonObject.getString("item_name");
                        item_quantity[i] = inner_jsonObject.getString("item_quantity");

                        lr = new ListRowItem();
                        lr.setItemId(item_id[i]);
                        lr.setItemName(item_name[i]);
                        lr.setItemQty(item_quantity[i]);

                        listitem.add(lr);

                        if(!"".equals(str_item_id))
                        id_num = Integer.valueOf(str_item_id.substring(1));
                    }
                    myBaseAdapter = new MyBaseAdapter(getApplicationContext(),listitem);

                    listView = (ListView) findViewById(R.id.item_list_view);
                    listView.setAdapter(myBaseAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item_position = String.valueOf(position);
                            Intent intent = new Intent(getApplicationContext(), Edit_Item.class);
                            Bundle extras = new Bundle();
                            extras.putString(CRRNT_ROW_NUMBER, item_position);
                            extras.putSerializable(LISTITEM, listitem);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    });

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            id_num = id_num + 1;
                            String str_id_num = String.valueOf(id_num);
                            Intent intent = new Intent(getApplicationContext(),Add_Item.class);
                            intent.putExtra(ID_NUM,str_id_num);
                            startActivity(intent);
                        }
                    });
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        //do nothing on back
    }
}
