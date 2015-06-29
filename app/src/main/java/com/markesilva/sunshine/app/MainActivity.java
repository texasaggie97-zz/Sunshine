package com.markesilva.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.markesilva.sunshine.app.sync.SunshineSyncAdapter;


public class MainActivity extends ActionBarActivity implements ForecastFragment.Callback {
    final private String LOG_TAG = MainActivity.class.getSimpleName();
    private String mLocation;
    private String DETAILFRAGMENT_TAG  = "DFTAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocation = Utility.getPreferredLocation(this);
        if (findViewById(R.id.weather_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.weather_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG )
                        .commit();
            }
        }
        else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        ForecastFragment ff = ((ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast));
        ff.setUseTodayLayout(!mTwoPane);
        Log.v(LOG_TAG, "onCreate");
        SunshineSyncAdapter.initializeSyncAdapter(this);
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
        if ((loc != null) && !loc.equals(mLocation)) {
            ForecastFragment ff = (ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);
            if (ff != null) {
                ff.onLocationChnaged();
            }
            DetailFragment df = (DetailFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
            if (df != null) {
                df.onLocationChanged(loc);
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

    @Override
    public void onItemSelected(Uri dateUri) {

        if (mTwoPane) {
            // Show or replace detail fragment
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_URI, dateUri);

            DetailFragment f = new DetailFragment();
            f.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.weather_detail_container, f, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .setData(dateUri);
            startActivity(intent);

        }
    }
}
