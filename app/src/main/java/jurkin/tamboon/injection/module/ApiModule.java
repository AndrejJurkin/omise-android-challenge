package jurkin.tamboon.injection.module;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.GeneralSecurityException;

import javax.inject.Singleton;

import co.omise.android.Client;
import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import jurkin.tamboon.App;
import jurkin.tamboon.api.OmiseService;
import jurkin.tamboon.api.Repository;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The api module contains API related dependencies and remote data sources.
 *
 * Created by Andrej Jurkin on 12/22/17.
 */

@Module
public class ApiModule {
    private static final String TAG = "ApiModule";

    private static final int OK_HTTP_CACHE_SIZE = 10 * 1024 * 1024;

    private String baseUrl;

    private String publicKey;

    /**
     * Construct ApiModule with the base url for all networking requests
     *
     * @param baseUrl The base url for all networking requests used to build retrofit and services
     * @param publicKey The public Omise API key
     */
    public ApiModule(String baseUrl, String publicKey) {
        this.baseUrl = baseUrl;
        this.publicKey = publicKey;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(App app) {
        return new Cache(app.getCacheDir(), OK_HTTP_CACHE_SIZE);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory
                        .createWithScheduler(Schedulers.newThread()))
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    OmiseService provideOmiseService(Retrofit retrofit) {
        return retrofit.create(OmiseService.class);
    }

    @Provides
    @Singleton
    Repository provideRepository(OmiseService omiseService, Client client) {
        return new Repository(omiseService, client);
    }

    @Provides
    @Singleton
    Client provideOmiseClient() {
        try {
            return new Client(publicKey);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Omise client could not be instantiated.", e);
        }
    }
}
