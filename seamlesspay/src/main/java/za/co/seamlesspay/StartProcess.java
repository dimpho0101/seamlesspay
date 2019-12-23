package za.co.seamlesspay;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import za.co.seamlesspay.databinding.BottomSheetDialogBinding;

public class StartProcess extends AppCompatActivity {

  BottomSheetDialogUtil mBottomSheetDialogUtil;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onStart() {
    super.onStart();
    BottomSheetDialogBinding bottomSheetDialogBinding = DataBindingUtil.setContentView(this, R.layout.bottom_sheet_dialog);
    mBottomSheetDialogUtil = new BottomSheetDialogUtil(this, bottomSheetDialogBinding.getRoot());
    mBottomSheetDialogUtil.show(getSupportFragmentManager(), "");
  }
}
