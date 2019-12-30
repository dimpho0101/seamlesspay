package io.seamlesspay;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.seamlesspay.databinding.ActivityMainBinding;
import io.seamlesspay.examples.BottomSheetTapActivity;
import io.seamlesspay.examples.DialogTapActivity;
import io.seamlesspay.examples.TapActivity;

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

    // This example does not use any UI. OnSuccess it provides you with the necessary information
    mBinding.noui.setOnClickListener(aView -> startActivity(new Intent(this, TapActivity.class)));

    // This example uses a Dialog. OnSuccess it provides you with the necessary information
    mBinding.dialogui.setOnClickListener(aView -> startActivity(new Intent(this, DialogTapActivity.class)));

    // This example uses a BottomSheetDialogFragment. OnSuccess it provides you with the necessary information
    mBinding.bottomsheetui.setOnClickListener(aView -> startActivity(new Intent(this, BottomSheetTapActivity.class)));

  }
}
