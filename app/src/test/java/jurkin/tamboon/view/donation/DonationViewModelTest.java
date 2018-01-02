package jurkin.tamboon.view.donation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.omise.android.TokenRequest;
import jurkin.tamboon.api.Repository;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrej Jurkin on 1/2/18.
 */

public class DonationViewModelTest {

    @Mock
    private Repository repository;

    private DonationViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.viewModel = new DonationViewModel(repository);
        this.viewModel.setCardNumber("1111 1111 1111 1111");
        this.viewModel.setAmount(100);
        this.viewModel.setExpiryDate("10/17");
        this.viewModel.setCardholderName("John Doe");
        this.viewModel.setCvc("200");
    }

    @Test
    public void testCreateTokenRequest() {
        TokenRequest tokenRequest = this.viewModel.createTokenRequest();
        assertEquals(tokenRequest.number, "1111 1111 1111 1111");
        assertEquals(tokenRequest.name, "John Doe");
        assertEquals(tokenRequest.securityCode, "200");
        assertEquals(tokenRequest.expirationMonth, 10);
        assertEquals(tokenRequest.expirationYear, 17);
    }
}
