package za.co.seamlesspay.v1.feature;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import za.co.seamlesspay.v1.viewmodel.SeamlessViewModel;

/**
 * Returns an EmvCard object. This class has no UI, it will only return the required information when called
 */
public class ConfigureViewModel {

  /**
   * Dispose the resource, the operation should be idempotent.
   */
  private Context mContext;

  /**
   * @param aContext Context from the calling activity/fragment
   */
  public ConfigureViewModel(Context aContext) {
    mContext = aContext;
  }

  /**
   * @return ViewModel
   */
  public SeamlessViewModel createViewModel() {
    return ViewModelProviders.of((FragmentActivity) mContext).get(SeamlessViewModel.class);
  }

}
