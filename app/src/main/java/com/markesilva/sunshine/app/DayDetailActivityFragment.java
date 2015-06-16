package com.markesilva.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class DayDetailActivityFragment extends Fragment
{
    private static final String LOG_TAG = DayDetailActivityFragment.class.getSimpleName();

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private String mForecasrStr;
    private ShareActionProvider mShareActionProvider;

    public DayDetailActivityFragment()
    {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        // Add items to actopn bar if present
        inflater.inflate(R.menu.detailfragment, menu);

        // Get the share menu item
        MenuItem item = menu.findItem(R.id.action_menu_share);

        // Get provider and set the share intent
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if (mShareActionProvider != null)
        {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
        else
        {
            Log.d(LOG_TAG, "Share action provider is null?");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_day_detail, container, false);

        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra("forecast"))
        {
            mForecasrStr = intent.getStringExtra("forecast");
            TextView textView = (TextView) rootView.findViewById(R.id.day_detail_forecast);
            textView.setText(mForecasrStr);
        }

        return rootView;
    }

    private Intent createShareForecastIntent()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecasrStr + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }
}

