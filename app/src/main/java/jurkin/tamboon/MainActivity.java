package jurkin.tamboon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jurkin.tamboon.view.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
