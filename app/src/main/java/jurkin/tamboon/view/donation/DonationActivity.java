package jurkin.tamboon.view.donation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jurkin.tamboon.R;
import jurkin.tamboon.model.Charity;
import jurkin.tamboon.view.BaseActivity;

/**
 * Created by Andrej Jurkin on 12/29/17.
 */

public class DonationActivity extends BaseActivity {

    public static final String EXTRA_CHARITY = "extra_charity";

    public static void start(Context context, Charity charity) {
        Intent i = new Intent(context, DonationActivity.class);
        i.putExtra(EXTRA_CHARITY, charity);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
    }
}
