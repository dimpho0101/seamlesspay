package io.seamlesspay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import za.co.seamlesspay.ScanCardNoUi.ScanCardNoUi;
import za.co.seamlesspay.ScanCardNoUi.ScanCardNoUiCallback;
import za.co.seamlesspay.seamlessemv.model.EmvCard;

public class MainActivity extends AppCompatActivity {

  TextView mButton;

  ScanCardNoUi mScanCardNoUi;


  private EmvCard mEmvCard;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mScanCardNoUi = new ScanCardNoUi(this);
    mButton = findViewById(R.id.text);
    mButton.setOnClickListener(aView -> mScanCardNoUi.mNFCCardReader.enableDispatch());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    mScanCardNoUi.startReading(new ScanCardNoUiCallback.State() {
      @Override
      public void onSuccess(EmvCard aEmvCard) {
        mEmvCard = aEmvCard;
        setText();
      }

      @Override
      public void onError(Throwable aThrowable) {

      }
    },intent);

  }

  @Override
  protected void onPause() {
    super.onPause();
    mScanCardNoUi.stopReading();
  }

  public void setText() {
    mButton.setText(mEmvCard.getCardNumber());
  }

}