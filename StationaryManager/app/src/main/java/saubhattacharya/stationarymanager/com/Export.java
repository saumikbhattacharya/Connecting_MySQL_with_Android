package saubhattacharya.stationarymanager.com;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Export extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.export_radio_grp1);
        radioGroup.check(R.id.export_radio1);

        RadioGroup radioGroup1 = (RadioGroup)findViewById(R.id.export_radio_grp2);
        radioGroup1.check(R.id.export_radio3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void OnExportButtonClicked (View view)
    {
        Toast.makeText(getApplicationContext(),"Export function is yet to be available!",Toast.LENGTH_LONG).show();
    }

}
