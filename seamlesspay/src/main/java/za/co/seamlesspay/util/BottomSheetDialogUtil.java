package za.co.seamlesspay.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;

public class BottomSheetDialogUtil extends BottomSheetDialogFragment {

  /**
   *
   */
  private View mView;

  /**
   *
   */
  private Boolean isFirstTimeShown = true;

  /**
   *
   * @param aView
   */
  public BottomSheetDialogUtil(View aView) {
    mView = aView;
  }

  /**
   *
   * @param savedInstanceState
   * @return
   */
  @SuppressWarnings("NullableProblems")
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  /**
   *
   * @param dialog
   * @param style
   */
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

  /**
   *
   * @param dialog
   */
  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
  }
}
