package jurkin.tamboon.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Base activity class that contains common logic for all activities in the app
 *
 * Created by Andrej Jurkin on 12/21/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
