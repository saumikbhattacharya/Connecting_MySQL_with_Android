package saubhattacharya.stationarymanager.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class New_User_Registration extends AppCompatActivity {

    Intent intent_to_main;
    String str_emp_id, str_emp_name, str_emp_email, str_emp_service_area, str_emp_service_line, str_emp_password,response_code,jsonresponse;;
    JSONParser jsonParser;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__user__registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent_to_main = new Intent(getApplicationContext(),LoginActivity.class);

        TextView set_empid = (TextView)findViewById(R.id.set_personnel_number);
        set_empid.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView set_emp_name = (TextView)findViewById(R.id.set_employee_name);
        set_emp_name.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView set_emp_email = (TextView)findViewById(R.id.set_employee_email);
        set_emp_email.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView set_pwd = (TextView)findViewById(R.id.set_password);
        set_pwd.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView set_service_area = (TextView)findViewById(R.id.set_service_area);
        set_service_area.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView set_service_line = (TextView)findViewById(R.id.set_service_line);
        set_service_line.setGravity(Gravity.CENTER_HORIZONTAL);

        Spinner enter_service_area = (Spinner) findViewById(R.id.enter_service_area);
        ArrayAdapter adapter_src = ArrayAdapter.createFromResource(this, R.array.service_area, R.layout.spinner_item_layout);
        adapter_src.setDropDownViewResource(R.layout.spinner_item_layout);
        enter_service_area.setAdapter(adapter_src);

        Spinner enter_service_line = (Spinner) findViewById(R.id.enter_service_line);
        adapter_src = ArrayAdapter.createFromResource(this,R.array.service_line, R.layout.spinner_item_layout);
        adapter_src.setDropDownViewResource(R.layout.spinner_item_layout);
        enter_service_line.setAdapter(adapter_src);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action_save = menu.findItem(R.id.action_settings);
        MenuItem action_refresh_list = menu.findItem(R.id.action_refresh_list);
        action_save.setVisible(false);
        action_refresh_list.setVisible(false);
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
        if (id == R.id.action_settings) {
            return true;
        }
        if ((id == R.id.action_save) || (id == 2131493010))
        {
            EditText Emp_id = (EditText)findViewById(R.id.enter_personnel_number);
            str_emp_id = Emp_id.getText().toString();
            EditText Emp_Name = (EditText)findViewById(R.id.enter_employee_name);
            str_emp_name = Emp_Name.getText().toString();
            EditText Emp_Email = (EditText)findViewById(R.id.enter_employee_email);
            str_emp_email = Emp_Email.getText().toString();
            EditText Emp_Pwd = (EditText)findViewById(R.id.enter_password);
            str_emp_password = Emp_Pwd.getText().toString();
            Spinner Emp_Service_Area = (Spinner)findViewById(R.id.enter_service_area);
            str_emp_service_area = Emp_Service_Area.getSelectedItem().toString();
            Spinner Emp_Service_Line = (Spinner)findViewById(R.id.enter_service_line);
            str_emp_service_line = Emp_Service_Line.getSelectedItem().toString();

            if(("".equals(str_emp_id)) || ("".equals(str_emp_name)) || ("".equals(str_emp_email)) || ("".equals(str_emp_password)))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Required fields are missing!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else
            {
                MakeRequestTask user_registration = new MakeRequestTask();
                user_registration.execute();
            }
        }
        if (id == 16908332)
        {
            intent_to_main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent_to_main);
        }
        if ((id == R.id.action_cancel) || (id == 2131493011))
        {
            intent_to_main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent_to_main);
        }

        return super.onOptionsItemSelected(item);
    }

    public class MakeRequestTask extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                URL url = new URL("http://192.168.0.103:8383//android_connect/user_registration.php");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("str_emp_id", str_emp_id)
                        .appendQueryParameter("str_emp_name", str_emp_name)
                        .appendQueryParameter("str_emp_email", str_emp_email)
                        .appendQueryParameter("str_emp_password", str_emp_password)
                        .appendQueryParameter("str_emp_service_area", str_emp_service_area)
                        .appendQueryParameter("str_emp_service_line", str_emp_service_line);
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
                Toast.makeText(New_User_Registration.this,
                        "Something went wrong here!", Toast.LENGTH_LONG).show();
            }
            else if("200".equals(response_code)) {
                Toast.makeText(getApplicationContext(),"User has been successfully registered! Please try to login now.",Toast.LENGTH_LONG).show();
                intent_to_main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent_to_main);
            }
        }
    }
}
