package io.seamlesspay;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.seamlesspay.databinding.ActivityMainBinding;
import io.seamlesspay.v2.BottomSheetTapActivityV2;

public class MainActivity extends AppCompatActivity {

  /**
   * DataBinding for this Activity
   */
  ActivityMainBinding mBinding;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
  }

  @Override
  protected void onStart() {
    super.onStart();

    // This example shows usage on a Fragment
    mBinding.nouifragment.setOnClickListener(aView -> startActivity(new Intent(this, BottomSheetTapActivityV2.class)));

  }

}
