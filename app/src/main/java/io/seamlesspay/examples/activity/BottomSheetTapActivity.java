package io.seamlesspay.examples.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.seamlesspay.R;
import io.seamlesspay.databinding.ActivityTapBinding;
import za.co.seamlesspay.v1.feature.BottomSheetDialog.BottomSheetEmvReader;
import za.co.seamlesspay.v1.interfaces.EmvCallback;
import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;

public class BottomSheetTapActivity extends AppCompatActivity {

  /**
   * DataBinding for this activity
   */
  private ActivityTapBinding mBinding;

  /**
   * For use if you want to use the inBuilt BottomSheetDialogFragment
   */
  private BottomSheetEmvReader mTap;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tap);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mTap = new BottomSheetEmvReader(this, getSupportFragmentManager());
    mBinding.button.setOnClickListener(aView -> mTap.enableDispatch());
  }

  /**
   * Required
   *
   * @param intent Intent we use to start reading for the credit card
   */
  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    mTap.startReading((aEmvCard, aThrowable) -> setText(aEmvCard, null), intent);
  }

  /**
   * Required
   */
  @Override
  protected void onPause() {
    super.onPause();
    if (mTap != null) {
      mTap.stopReading();
    }
  }

  /**
   * @param aEmvCard   Nullable - EmvCard object
   * @param aThrowable Nullable - Throwable
   */
  public void setText(@Nullable EmvCard aEmvCard, @Nullable Throwable aThrowable) {
    if (aEmvCard != null) {
      mBinding.text.setText(aEmvCard.getCardNumber());
    } else if (aThrowable != null) {
      mBinding.text.setText(aThrowable.getMessage());
    }
  }
}