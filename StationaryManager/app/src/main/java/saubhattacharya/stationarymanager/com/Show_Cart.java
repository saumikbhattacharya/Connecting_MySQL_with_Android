package saubhattacharya.stationarymanager.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class Show_Cart extends AppCompatActivity {

    JSONParser jsonParser;
    String jsonresponse,response_code;
    String[] item_id = new String[30];
    String[] item_name = new String[30];
    String[] item_quantity = new String[30];
    JSONObject jsonObject;
    int i;
    ArrayList<ListRowItem> listitem = new ArrayList<>();
    ListRowItem lr;
    ListView listView;
    MyCartBaseAdapter myBaseAdapter;
    String str_emp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("StationeryManagerPref", 0);
        str_emp_id = preferences.getString("user_id",null);

        ShowCartTask show_all_items = new ShowCartTask();
        show_all_items.execute();
    }

    public class ShowCartTask extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                URL url = new URL("http://192.168.0.103:8383//android_connect/show_cart.php");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("str_emp_id", str_emp_id);
                String data = builder.build().getEncodedQuery();

                jsonParser = new JSONParser();
                jsonresponse = jsonParser.makeHTTPRequest(url, "GET", data);
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
                    Toast.makeText(Show_Cart.this,
                            "Cart empty as you've not selected any item!", Toast.LENGTH_LONG).show();
                }
                else if("200".equals(response_code)) {
                    JSONArray jsonArray = jsonObject.optJSONArray("itemdata");
                    for (i=0 ; i<jsonArray.length() ; i++)
                    {
                        JSONObject inner_jsonObject = jsonArray.getJSONObject(i);
                        item_id[i] = inner_jsonObject.getString("item_id");
                        item_name[i] = inner_jsonObject.getString("item_name");
                        item_quantity[i] = inner_jsonObject.getString("item_quantity");

                        lr = new ListRowItem();
                        lr.setItemId(item_id[i]);
                        lr.setItemName(item_name[i]);
                        lr.setItemQty(item_quantity[i]);

                        listitem.add(lr);
                    }
                    myBaseAdapter = new MyCartBaseAdapter(getApplicationContext(),listitem);

                    listView = (ListView) findViewById(R.id.list_view);
                    listView.setAdapter(myBaseAdapter);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void OnExitButtonClicked (View view)
    {
        MakeRequestTask delete_cart = new MakeRequestTask();
        delete_cart.execute();
    }

    public class MakeRequestTask extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                URL url = new URL("http://192.168.0.103:8383//android_connect/delete_cart.php");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("str_emp_id", str_emp_id);
                String data = builder.build().getEncodedQuery();

                jsonParser = new JSONParser();
                jsonresponse = jsonParser.makeHTTPRequest(url, "GET", data);
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
                    Toast.makeText(Show_Cart.this,
                            "Something went wrong here!", Toast.LENGTH_LONG).show();
                }
                else if("200".equals(response_code)) {

                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("StationeryManagerPref",0);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.clear();
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
