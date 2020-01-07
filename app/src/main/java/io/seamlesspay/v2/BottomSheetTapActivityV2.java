package io.seamlesspay.v2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import io.seamlesspay.R;
import io.seamlesspay.databinding.ActivityTapBinding;
import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;
import za.co.seamlesspay.v2.feature.BottomSheetDialog.BottomSheetEmvReaderV2;
import za.co.seamlesspay.v2.viewmodel.ConfigureViewModel;
import za.co.seamlesspay.v2.viewmodel.EmvViewModel;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;

public class BottomSheetTapActivityV2 extends AppCompatActivity {

  private ActivityTapBinding mBinding;

  private BottomSheetEmvReaderV2 mEmvReaderV2;

  private EmvViewModel mModelV;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tap);
  }

  @Override
  protected void onStart() {
    super.onStart();
    configureReader();
    // Start reading for your card
    if (SDK_INT >= KITKAT) {
      mBinding.button.setOnClickListener(aView -> startReading());
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    // Stop reading for the card
    mEmvReaderV2.stopReading();
  }

  private void startReading() {
    final Observer<EmvCard> cardObserver = aS -> {
      if (aS != null)
        mBinding.text.setText(aS.getCardNumber());
    };
    mModelV.getIntentMutableLiveData().observe(this, cardObserver);
    if (SDK_INT >= KITKAT) {
      mEmvReaderV2.readCreditCard();
    }
  }

  public void configureReader() {
    mModelV = new ConfigureViewModel(this).createViewModel();
    mEmvReaderV2 = new BottomSheetEmvReaderV2(this, getSupportFragmentManager());
  }

}