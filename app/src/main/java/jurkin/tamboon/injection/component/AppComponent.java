package jurkin.tamboon.injection.component;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import jurkin.tamboon.injection.module.ApiModule;
import jurkin.tamboon.App;
import jurkin.tamboon.injection.module.AppModule;
import jurkin.tamboon.injection.module.UiModule;

/**
 * The AppComponent contains all dependencies that we need to initialize during the app start-up.
 *
 * Created by Andrej Jurkin on 12/22/17.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ApiModule.class,
        UiModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder app(App app);

        Builder apiModule(ApiModule apiModule);

        Builder appModule(AppModule appModule);

        AppComponent build();
    }

    void inject(App app);
}