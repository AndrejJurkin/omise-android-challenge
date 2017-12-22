package jurkin.tamboon.injection.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import jurkin.tamboon.MainActivity;
import jurkin.tamboon.view.charitylist.CharityListFragment;

/**
 * Created by Andrej Jurkin on 12/22/17.
 */

@Module
public abstract class UiModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract CharityListFragment contributeCharityListFragment();
}