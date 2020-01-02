package io.seamlesspay.examples.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.seamlesspay.R;
import io.seamlesspay.databinding.ActivityTapBinding;
import za.co.seamlesspay.v1.feature.NoUi.EmvReader;
import za.co.seamlesspay.v1.interfaces.EmvCallback;
import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;

public class TapActivity extends AppCompatActivity {

  /**
   * DataBinding for this activity
   */
  private ActivityTapBinding mBinding;

  /**
   * For use if you want to create your own UI
   */
  private EmvReader mTap;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tap);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mTap = new EmvReader(this);
    mBinding.button.setOnClickListener(aView -> mTap.mNFCCardReader.enableDispatch());
  }

  /**
   * Required
   *
   * @param intent Intent we use to start reading for the credit card
   */
  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    mTap.startReading(new EmvCallback.ResourceStatus() {
      @Override
      public void onSuccess(EmvCard aEmvCard) {
        setText(aEmvCard, null);
      }

      @Override
      public void onError(Throwable aThrowable) {
        setText(null, aThrowable);
      }
    }, intent);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mTap.stopReading();
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