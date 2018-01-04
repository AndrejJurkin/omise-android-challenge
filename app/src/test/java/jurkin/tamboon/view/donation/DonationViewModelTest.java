package jurkin.tamboon.view.donation;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.omise.android.TokenRequest;
import co.omise.android.models.Token;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import jurkin.tamboon.api.Repository;
import jurkin.tamboon.model.Charity;
import jurkin.tamboon.view.ViewModelState;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Andrej Jurkin on 1/2/18.
 */

public class DonationViewModelTest {

    private static final String CHARITY_NAME = "mock_charity_name";
    private static final String CHARITY_IMAGE_URL = "mock_charity_image_url";

    @Mock
    private Repository repository;

    @Mock
    private Charity charity;

    private DonationViewModel viewModel;

    private TestObserver<ViewModelState> viewModelStateObserver;
    private TestObserver<Boolean> donateButtonStateObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.viewModel = new DonationViewModel(repository);
        this.viewModel.setCharity(charity);

        when(charity.getName()).thenReturn(CHARITY_NAME);
        when(charity.getLogoUrl()).thenReturn(CHARITY_IMAGE_URL);
    }

    @Test
    public void test_getTitleReturnsCharityName() {
        assertEquals(CHARITY_NAME, viewModel.getTitle());
    }
    
    @Test
    public void test_getMainImageUrlReturnsCharityImage() {
        assertEquals(CHARITY_IMAGE_URL, viewModel.getMainImageUrl());
    }

    @Test
    public void test_createTokenRequest() {
        fillInValidCardForm();
        TokenRequest tokenRequest = this.viewModel.createTokenRequest();
        assertEquals(tokenRequest.number, "1111 1111 1111 1111");
        assertEquals(tokenRequest.name, "John Doe");
        assertEquals(tokenRequest.securityCode, "200");
        assertEquals(tokenRequest.expirationMonth, 10);
        assertEquals(tokenRequest.expirationYear, 17);
    }

    @Test
    public void test_cardFormInvalidState() {
        // The input form is invalid, donate button should be disabled
        invalidateCardForm();
        resetObservers();
        viewModel.validateCreditCardModel();
        donateButtonStateObserver.assertValue(false);
    }

    @Test
    public void test_cardFormValidState() throws InterruptedException {
        // The input form is valid, donate button should be enabled
        fillInValidCardForm();
        resetObservers();
        viewModel.validateCreditCardModel();
        donateButtonStateObserver.assertValue(true);
    }

    @Test
    public void test_donateChangesViewModelStateError() {
        // Simulate failed transaction
        when(repository.getToken(any())).thenReturn(Observable.error(new NullPointerException()));
        fillInValidCardForm();
        resetObservers();
        viewModel.donate();

        // Donate button should be disabled
        donateButtonStateObserver.assertValue(false);

        // View model state should change to loading, then complete
        viewModelStateObserver
                .assertValues(ViewModelState.Loading, ViewModelState.Error);
    }

    @Test
    public void test_donateChangesViewModelStateSuccess() {
        when(repository.getToken(any())).thenReturn(Observable.error(new NullPointerException()));
        when(repository.donate(any())).thenReturn(emptyApiResponse.toObservable());

        fillInValidCardForm();
        resetObservers();
        viewModel.donate();

        // Donate button should be disabled
        donateButtonStateObserver.assertValue(false);

        // View model state should change to loading, then complete
        viewModelStateObserver
                .assertValues(ViewModelState.Loading, ViewModelState.Loaded);
    }

    /*
     * Set correct card form values
     */
    private void fillInValidCardForm() {
        this.viewModel.setCardNumber("1111 1111 1111 1111");
        this.viewModel.setAmount("100");
        this.viewModel.setExpiryDate("10/17");
        this.viewModel.setCardholderName("John Doe");
        this.viewModel.setCvc("200");
    }

    /*
     * Set incorrect card form value so the form is invalid
     */
    private void invalidateCardForm() {
        this.viewModel.setCvc("");
    }

    private void resetObservers() {
        viewModelStateObserver = viewModel.getViewModelState().test();
        donateButtonStateObserver = viewModel.isDonateButtonEnabled().test();
    }

    private Single<Void> emptyApiResponse = new Single<Void>() {
        @Override
        protected void subscribeActual(@NonNull SingleObserver<? super Void> observer) {
            observer.onSuccess(null);
        }
    };
}
