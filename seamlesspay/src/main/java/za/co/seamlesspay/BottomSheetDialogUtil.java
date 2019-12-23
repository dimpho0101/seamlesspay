package za.co.seamlesspay;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;

public class BottomSheetDialogUtil extends BottomSheetDialogFragment {

  private Context mContext;

  private View mView;

  private Boolean isFirstTimeShown = true;

  BottomSheetDialogUtil(Context aContext, View aView) {
    mContext = aContext;
    mView = aView;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  @Override
  public void setupDialog(@NonNull Dialog dialog, int style) {
    super.setupDialog(dialog, style);
    if (mView != null) {
      if (isFirstTimeShown) {
        ((ViewGroup) mView.getParent()).removeView(mView);
      }
      dialog.setContentView(mView);
      ((View) mView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
      isFirstTimeShown = false;
    } else {
      dismiss();
    }
  }

  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
  }
}
