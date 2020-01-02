package io.seamlesspay.examples.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.seamlesspay.R;
import io.seamlesspay.databinding.Activity2BottomSheetTapBinding;
import za.co.seamlesspay.v1.viewmodel.ConfigureViewModel;
import za.co.seamlesspay.v1.feature.NoUi.EmvReader;
import za.co.seamlesspay.v1.viewmodel.IntentViewModel;

public class BottomSheetTapActivity2 extends AppCompatActivity {

  Activity2BottomSheetTapBinding mBinding;

  IntentViewModel mViewModel;

  private EmvReader mTap;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity2_bottom_sheet_tap);
    mViewModel = new ConfigureViewModel(this).createViewModel();
  }

  public void startReading() {
    mTap = new EmvReader(this);
    mTap.mNFCCardReader.enableDispatch();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    mViewModel.getIntentMutableLiveData().setValue(intent);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mTap.stopReading();
  }
}
