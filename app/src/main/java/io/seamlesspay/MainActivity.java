package io.seamlesspay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import za.co.seamlesspay.CreditCardReaderUtil;

public class MainActivity extends AppCompatActivity {

  TextView mButton;

  CreditCardReaderUtil mCreditCardReaderUtil;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  @Override
  protected void onStart() {
    super.onStart();
    mCreditCardReaderUtil = new CreditCardReaderUtil(this, getSupportFragmentManager());
    mButton = findViewById(R.id.text);
    mButton.setOnClickListener(aView -> mCreditCardReaderUtil.initSdk());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    mCreditCardReaderUtil.createInstance(intent);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mCreditCardReaderUtil.mNFCCardReader.enableDispatch();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mCreditCardReaderUtil.dispose();
    mCreditCardReaderUtil.mNFCCardReader.disableDispatch();
  }
}

