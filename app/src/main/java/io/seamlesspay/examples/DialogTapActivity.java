package io.seamlesspay.examples;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.seamlesspay.R;
import io.seamlesspay.databinding.ActivityTapBinding;
import za.co.seamlesspay.v1.feature.NoUi.SeamlessNoUI;
import za.co.seamlesspay.v1.interfaces.SeamlessObserver;
import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;

public class DialogTapActivity extends AppCompatActivity {

  private ActivityTapBinding mBinding;

  private SeamlessNoUI mTap;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tap);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mTap = new SeamlessNoUI(this);
    mBinding.button.setOnClickListener(aView -> mTap.mNFCCardReader.enableDispatch());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    mTap.startReading(new SeamlessObserver.ResourceStatus() {
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

  public void setText(@Nullable EmvCard aEmvCard, @Nullable Throwable aThrowable) {
    if (aEmvCard != null) {
      mBinding.text.setText(aEmvCard.getCardNumber());
    } else if (aThrowable != null) {
      mBinding.text.setText(aThrowable.getMessage());
    }
  }
}