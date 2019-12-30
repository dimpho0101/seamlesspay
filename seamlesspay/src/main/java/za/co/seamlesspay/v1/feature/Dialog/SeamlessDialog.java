package za.co.seamlesspay.v1.feature.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import androidx.databinding.DataBindingUtil;
import za.co.seamlesspay.R;
import za.co.seamlesspay.databinding.SeamlessDialogLayoutBinding;
import za.co.seamlesspay.v1.util.AnimationUtil.AnimationUtil;

public class SeamlessDialog extends Dialog {

  private Activity mActivity;

  private SeamlessDialogLayoutBinding mBinding;

  private AnimationUtil mAnimationUtil = new AnimationUtil();

  public SeamlessDialog(Activity aActivity) {
    super(aActivity);
    this.mActivity = aActivity;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    mBinding = DataBindingUtil.setContentView(mActivity, R.layout.seamless_dialog_layout);
  }

  @Override
  protected void onStart() {
    super.onStart();
    configureViews();
  }

  private void configureViews() {
    mAnimationUtil.playAnimation(mBinding.creditCardInclude.rippleAnimation, "circles.json");
  }

}
