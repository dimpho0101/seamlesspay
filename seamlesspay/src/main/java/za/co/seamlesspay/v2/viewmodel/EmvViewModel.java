package za.co.seamlesspay.v2.viewmodel;

import android.content.Intent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;

/**
 *
 */
public class EmvViewModel extends ViewModel {

  /**
   *
   */
  private MutableLiveData<EmvCard> mIntentMutableLiveData;

  /**
   * @return EmvCard object
   */
  public MutableLiveData<EmvCard> getIntentMutableLiveData() {
    if (mIntentMutableLiveData == null) {
      mIntentMutableLiveData = new MutableLiveData<>();
    }
    return mIntentMutableLiveData;
  }

}
