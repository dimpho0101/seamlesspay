package za.co.seamlesspay.v1.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * An Errors object, that can hold more than one error. We only use this class in our business logic
 */
public class Errors {

  /**
   * The array of errors - using Gson to populate in some cases.
   */
  @SerializedName("errors")
  private final List<Error> mErrors = new ArrayList<>();

  /**
   * @return The errors
   */
  public List<Error> getErrors() {
    return mErrors;
  }

  /**
   * Adds a single error to the existing errors array
   *
   * @param aError The error to add to the array of errors.
   */
  public void addError(Error aError) {
    if (aError != null) {
      mErrors.add(aError);
    }
  }

  /**
   * A convenience method to get the first error in the array.
   *
   * @return The first Error in the array, if it exists, otherwise Null
   */
  @Nullable
  public Error getFirstError() {
    return mErrors.isEmpty() ? null : mErrors.get(0);
  }
}
