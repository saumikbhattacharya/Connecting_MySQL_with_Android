package saubhattacharya.stationarymanager.com;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    TextView about_app,about_dev;
    String versionName;
    int versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try
        {
            about_app = (TextView)findViewById(R.id.about_app);
            about_dev = (TextView)findViewById(R.id.about_dev);

            PackageInfo pInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
        }catch(Exception e) {
            e.printStackTrace();
        }

        about_app.setText("Stationery Manager " + versionName + " is an inventory managment app for organizing all your stationery related inventories." + "\n" + "If you're an Admin, upon authentication, it will allow you to add, edit, view an item" + "\n" + "If you're an Employee, again upon authentication, it will allow you to view and select an item of your choice." + "\n" + "Note: You have to be an employee of Deloitte to use this application.");

        about_dev.setText("This application has been built by SAUMIK BHATTACHARYA." + "\n" + "Saumik is new to Android Programming. He has great interest in learning this technology." + "\n" + "He is always on his toes when it comes to developing Android Application." + "\n" + "Contact No.: +91 9741028770" + "\n"+"Email Address: saumikbhattacharya89@gmail.com"+"\n"+"Location: Bangalore.");
    }

}
