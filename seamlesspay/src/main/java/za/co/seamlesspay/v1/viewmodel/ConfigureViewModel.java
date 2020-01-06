package za.co.seamlesspay.v1.viewmodel;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

/**
 * Configure the ViewModel
 */
public class ConfigureViewModel {

  /**
   * Context Context from the calling activity/fragment
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
  public IntentViewModel createViewModel() {
    return ViewModelProviders.of((FragmentActivity) mContext).get(IntentViewModel.class);
  }

}
