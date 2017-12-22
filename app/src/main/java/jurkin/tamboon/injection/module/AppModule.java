package jurkin.tamboon.injection.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jurkin.tamboon.App;

/**
 * The base app module contains Application dependencies
 *
 * Created by Andrej Jurkin on 12/22/17.
 */

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    App provideApp() {
        return this.app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return this.app.getApplicationContext();
    }
}
