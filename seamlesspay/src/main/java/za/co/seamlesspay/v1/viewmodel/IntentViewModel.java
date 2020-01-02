package za.co.seamlesspay.v1.viewmodel;

import android.content.Intent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 *
 */
public class IntentViewModel extends ViewModel {

  /**
   *
   */
  private MutableLiveData<Intent> mIntentMutableLiveData;

  /**
   * @return
   */
  public MutableLiveData<Intent> getIntentMutableLiveData() {
    if (mIntentMutableLiveData == null) {
      mIntentMutableLiveData = new MutableLiveData<>();
    }
    return mIntentMutableLiveData;
  }

}
