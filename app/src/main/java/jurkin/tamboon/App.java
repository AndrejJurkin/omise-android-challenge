package jurkin.tamboon;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import jurkin.tamboon.injection.component.DaggerAppComponent;
import jurkin.tamboon.injection.module.ApiModule;
import jurkin.tamboon.injection.module.AppModule;

/**
 * Application class that contains code which we need to run when the app is created
 *
 * Created by Andrej Jurkin on 12/21/17.
 */

public class App extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    private static final String BASE_URL = "https://private-cc1812-andrejjurkin.apiary-mock.com";

    // Only for the test purposes, otherwise we would be getting the key from elsewhere
    private static final String TEST_OMISE_API_KEY = "test_api_key";

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(BASE_URL, TEST_OMISE_API_KEY))
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }
}
