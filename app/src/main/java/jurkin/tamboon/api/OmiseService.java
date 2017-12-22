package jurkin.tamboon.api;

import java.util.List;

import io.reactivex.Observable;
import jurkin.tamboon.model.Charity;

import jurkin.tamboon.model.Donation;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Andrej Jurkin on 12/21/17.
 */

public interface OmiseService {

    @GET("charities")
    Observable<List<Charity>> getCharities();

    @POST("donation")
    Observable<Void> donate(Donation donation);
}
