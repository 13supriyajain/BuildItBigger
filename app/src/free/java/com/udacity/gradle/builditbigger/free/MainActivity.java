package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokedisplaylib.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.GetJokeAsyncTask;
import com.udacity.gradle.builditbigger.R;

public class MainActivity extends AppCompatActivity {

    private static final String JOKE_TEXT_KEY = "JokeTextKey";

    private ProgressBar mProgressBar;
    private InterstitialAd mInterstitialAd;
    private String mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            mJoke = savedInstanceState.getString(JOKE_TEXT_KEY);

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_test_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        try {
            mJoke = new GetJokeAsyncTask().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
            mJoke = null;
        }

        if (!TextUtils.isEmpty(mJoke))
            displayAd();
        else {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(this, R.string.server_error, Toast.LENGTH_LONG).show();
        }
    }

    private void displayAd() {
        mProgressBar.setVisibility(View.GONE);
        if (mInterstitialAd.isLoaded())
            mInterstitialAd.show();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                displayJoke();
            }
        });
    }

    private void displayJoke() {
        Intent intent = new Intent(this, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.JOKE_TEXT_KEY, mJoke);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(JOKE_TEXT_KEY, mJoke);
    }
}