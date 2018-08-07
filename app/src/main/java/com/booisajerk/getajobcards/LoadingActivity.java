package com.booisajerk.getajobcards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Objects;

import static com.booisajerk.getajobcards.Constants.CATEGORY_STATE;

public class LoadingActivity extends Activity {
    private static final String LOG_TAG = Constants.LOG_TAG_NAME + LoadingActivity.class.getSimpleName();
    private Handler mWaitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_cards);

        mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    String categoryName = Objects.requireNonNull(getIntent().getExtras()).getString(CATEGORY_STATE);

                    Intent cardIntent = new Intent(LoadingActivity.this, CardActivity.class);
                    cardIntent.putExtra(Constants.CATEGORY_STATE, categoryName);
                    Log.d(LOG_TAG, categoryName + " category selected.");
                    startActivity(cardIntent);

                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}
