package io.seamlesspay.examples.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.seamlesspay.R;
import io.seamlesspay.databinding.Activity2BottomSheetTapBinding;
import za.co.seamlesspay.v1.feature.ConfigureViewModel;
import za.co.seamlesspay.v1.feature.NoUi.SeamlessNoUI;
import za.co.seamlesspay.v1.viewmodel.SeamlessViewModel;

public class BottomSheetTapActivity2 extends AppCompatActivity {

  Activity2BottomSheetTapBinding mBinding;

  SeamlessViewModel mViewModel;

  private SeamlessNoUI mTap;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity2_bottom_sheet_tap);
    mViewModel = new ConfigureViewModel(this).createViewModel();
  }

  public void startReading() {
    mTap = new SeamlessNoUI(this);
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
