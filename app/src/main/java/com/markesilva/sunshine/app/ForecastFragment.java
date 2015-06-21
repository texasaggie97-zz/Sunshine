package com.markesilva.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_refresh)
        {
            updateWeather();
            return true;
        }
        else if (id == R.id.action_view_location)
        {
            final String VIEW_BASE_URL = "geo:0,0?";
            final String QUERY_PARAM = "q";
            String location = PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getString(getString(R.string.pref_location_key),
                            getString(R.string.pref_location_default));

            Uri geoLocation = Uri.parse(VIEW_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, location)
                    .build();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null)
            {
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mForecastAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, new ArrayList<String>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dayforecast = mForecastAdapter.getItem(position);
                Intent dayDetailIntent = new Intent(getActivity(), DayDetailActivity.class);
                dayDetailIntent.putExtra("forecast", dayforecast);
                startActivity(dayDetailIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        updateWeather();
    }

    private void updateWeather()
    {
        FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity(), mForecastAdapter);
        String location = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_location_key),
                        getString(R.string.pref_location_default));
        weatherTask.execute(location);
    }

    ArrayAdapter<String> mForecastAdapter;

}
