package io.seamlesspay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cybersource.inappsdk.connectors.inapp.InAppSDKApiClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import za.co.seamlesspay.seamlesstap.SeamlessObserver;
import za.co.seamlesspay.seamlesstap.seamlesstap.Tap;
import za.co.seamlesspay.seamlesstap.seamlesstapbottomsheet.TapBottomSheet;
import za.co.seamlesspay.util.seamlessemv.model.EmvCard;

public class MainActivity extends AppCompatActivity {

  TextView mButton;

  TapBottomSheet mTap;

  private EmvCard mEmvCard;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mTap = new TapBottomSheet(this, getSupportFragmentManager());
    mButton = findViewById(R.id.text);
    mButton.setOnClickListener(aView -> mTap.mNFCCardReader.enableDispatch());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    mTap.startReading(new SeamlessObserver.ResourceStatus() {
      @Override
      public void onSuccess(EmvCard aEmvCard) {
        mEmvCard = aEmvCard;
        setText(aEmvCard.getCardNumber());
      }

      @Override
      public void onError(Throwable aThrowable) {
        setText(aThrowable.getMessage());
      }
    }, intent);

  }

  @Override
  protected void onPause() {
    super.onPause();
    mTap.stopReading();
  }

  public void setText(String aString) {
    mButton.setText(aString);
  }

}