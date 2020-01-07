package za.co.seamlesspay.v2.viewmodel;

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
  public EmvViewModel createViewModel() {
    return ViewModelProviders.of((FragmentActivity) mContext).get(EmvViewModel.class);
  }

}
