package saubhattacharya.stationarymanager.com;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.SharedPreferences.*;

public class LoginActivity extends AppCompatActivity {

    JSONParser jsonParser;
    String response_code,jsonresponse,user_id,user_name,user_email,user_pwd,user_category;
    JSONObject jsonObject;
    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
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
        action_logout.setVisible(false);
        MenuItem action_export = menu.findItem(R.id.action_export);
        action_export.setVisible(false);
        MenuItem action_show_cart = menu.findItem(R.id.action_show_cart);
        action_show_cart.setVisible(false);
        MenuItem action_about_us = menu.findItem(R.id.about);
        action_about_us.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about)
        {
            Intent intent = new Intent(getApplicationContext(),AboutUs.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try
            {
                URL url = new URL("http://192.168.0.103:8383//android_connect/user_login.php");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("str_emp_email", mEmail)
                        .appendQueryParameter("str_emp_password", mPassword);
                String data = builder.build().getEncodedQuery();

                jsonParser = new JSONParser();
                jsonresponse = jsonParser.makeHTTPRequest(url, "GET", data);

                jsonObject = new JSONObject(jsonresponse);
                response_code = jsonObject.getString("response_code");

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if ("200".equals(response_code))
            {
                try{
                    JSONArray jsonArray = jsonObject.optJSONArray("userdata");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    user_id = jsonObject1.getString("emp_id");
                    user_name = jsonObject1.getString("emp_name");
                    user_email = jsonObject1.getString("emp_email");
                    user_pwd = jsonObject1.getString("emp_pwd");
                    user_category = jsonObject1.getString("category");

                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("StationeryManagerPref",0);
                    Editor editor = preferences.edit();

                    editor.putString("user_id",user_id);
                    editor.putString("user_name",user_name);
                    editor.putString("user_email",user_email);
                    editor.putString("user_pwd",user_pwd);
                    editor.putString("user_category",user_category);

                    editor.commit();

                    if("ADMIN".equals(user_category))
                    {
                        Intent intent = new Intent(getApplicationContext(),Inventory_List.class);
                        startActivity(intent);
                    }

                    if("EMPLOYEE".equals(user_category))
                    {
                        Intent intent = new Intent(getApplicationContext(),Inventory_List_Emp.class);
                        startActivity(intent);
                    }
                }
                catch (JSONException JE)
                {
                    JE.printStackTrace();
                }
            }
            else
            {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public void OnRegButtonClicked(View view)
    {
        Intent intent = new Intent(getApplicationContext(),New_User_Registration.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEmailView.setText("");
        mPasswordView.setText("");
    }
}

