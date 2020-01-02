package za.co.seamlesspay.v1.network;

import androidx.lifecycle.LiveData;
import retrofit2.http.GET;
import za.co.seamlesspay.v1.model.StripePayment;

public interface ApiService {

  @GET("")
  LiveData<ApiResponse<StripePayment>>makeStripePayment();

}
