package com.markesilva.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    final private String LOG_TAG = MainActivity.class.getSimpleName();
    private String mLocation;
    private String FORECASTFRAGMENT_TAG = "FORECASTFRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocation = Utility.getPreferredLocation(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment(), FORECASTFRAGMENT_TAG)
                    .commit();
        }
        Log.v(LOG_TAG, "onCreate");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.v(LOG_TAG, "onPause");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String loc = Utility.getPreferredLocation(this);
        if (loc != mLocation) {
            ForecastFragment ff = (ForecastFragment) getSupportFragmentManager().findFragmentByTag(FORECASTFRAGMENT_TAG);
            if (ff != null) {
                ff.onLocationChnaged();
            }
            mLocation = loc;
        }
        Log.v(LOG_TAG, "onResume");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.v(LOG_TAG, "onStop");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.v(LOG_TAG, "onStart");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.v(LOG_TAG, "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
