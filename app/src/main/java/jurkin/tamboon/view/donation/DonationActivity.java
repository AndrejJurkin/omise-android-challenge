package jurkin.tamboon.view.donation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import jurkin.tamboon.R;
import jurkin.tamboon.model.Charity;
import jurkin.tamboon.view.BaseActivity;

/**
 * Created by Andrej Jurkin on 12/29/17.
 */

public class DonationActivity extends BaseActivity {

    public static final String EXTRA_CHARITY = "extra_charity";

    @SafeVarargs
    public static void start(Activity context, Charity charity,
                             @Nullable Pair<View, String>... sharedElements) {
        Intent i = new Intent(context, DonationActivity.class);
        i.putExtra(EXTRA_CHARITY, charity);

        @SuppressWarnings("unused")
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context, sharedElements);
        // TODO: Add options bundle to the intent to make shared transition work
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.donate));
        }
    }


}
