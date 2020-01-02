package za.co.seamlesspay.v1.network;


import androidx.annotation.Nullable;
import okhttp3.Headers;
import retrofit2.Response;

/**
 * Common class used by API responses.
 */
public class ApiResponse<T> {

  /**
   * The HTTP status code for the response
   */
  private final int mCode;

  /**
   * The deserialized body of the response
   */
  private  T mBody;

  /**
   * The HTTP Headers of the response
   */
  private Headers mHeaders;

  /**
   * The errors (if any) that a network request may have had
   */
  private Errors mErrors;

  /**
   * Constructor that takes a response as its only argument
   *
   * @param aResponse a Retrofit response
   */
  public ApiResponse(Response<T> aResponse) {

    mCode = aResponse.code();
    mHeaders = aResponse.headers();
    mBody = aResponse.body();
  }

  public ApiResponse(int aErrorCode){
    mCode = aErrorCode;
  }

  /**
   * Determines if the response was successful or not
   *
   * @return a boolean
   */
  public boolean isSuccessful() {
    return mCode >= HttpStatus.OK && mCode < HttpStatus.MULTI_CHOICE;
  }

  /**
   * Gets the HTTP Headers for the response
   *
   * @return The Headers
   */
  public Headers getHeaders() {
    return mHeaders;
  }

  /**
   * Gets the errors (if any) for the retrofit response
   *
   * @return the Errors
   */
  public Errors getErrors() {
    return mErrors;
  }

  /**
   * Sets the errors
   *
   * @param aErrors Errors
   */
  public void setErrors(Errors aErrors) {
    mErrors = aErrors;
  }

  /**
   * Gets the HTTP code of the retrofit response
   *
   * @return a int
   */
  public int getCode() {
    return mCode;
  }

  /**
   * The deserialized body of the response
   *
   * @return a deserialized body of the response
   */
  @Nullable
  public T getBody() {
    return mBody;
  }
}