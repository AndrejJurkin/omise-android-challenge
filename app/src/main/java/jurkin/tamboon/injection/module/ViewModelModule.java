package jurkin.tamboon.injection.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import jurkin.tamboon.injection.ViewModelFactory;
import jurkin.tamboon.view.charitylist.CharityListViewModel;

/**
 * Created by Andrej Jurkin on 12/28/17.
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharityListViewModel.class)
    abstract ViewModel bindRepoViewModel(CharityListViewModel charityListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}

