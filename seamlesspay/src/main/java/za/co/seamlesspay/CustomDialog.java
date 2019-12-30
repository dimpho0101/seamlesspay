package za.co.seamlesspay;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import androidx.databinding.DataBindingUtil;
import za.co.seamlesspay.databinding.CustomDialogProcessingBinding;
import za.co.seamlesspay.util.AnimationUtil;

public class CustomDialog extends Dialog {

  private Activity mActivity;

  private CustomDialogProcessingBinding mBinding;

  private AnimationUtil mAnimationUtil = new AnimationUtil();

  public CustomDialog(Activity aActivity) {
    super(aActivity);
    this.mActivity = aActivity;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    mBinding = DataBindingUtil.setContentView(mActivity, R.layout.custom_dialog_processing);
  }

  @Override
  protected void onStart() {
    super.onStart();
    configureViews();
  }

  private void configureViews() {
    mAnimationUtil.playAnimation(mBinding.loadingAnimation, "refresh.json");
  }

}
