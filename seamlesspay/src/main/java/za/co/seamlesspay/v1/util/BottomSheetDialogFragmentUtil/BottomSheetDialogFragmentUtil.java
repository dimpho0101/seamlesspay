package za.co.seamlesspay.v1.util.BottomSheetDialogFragmentUtil;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import za.co.seamlesspay.v1.feature.BottomSheetDialog.SeamlessBottomSheet;

public class BottomSheetDialogFragmentUtil extends BottomSheetDialogFragment {

  /**
   * View to display from {@link SeamlessBottomSheet}
   */
  private View mView;

  /**
   * Boolean used to determine if it's the first time showing the BottomSheetDialog or not
   */
  private Boolean isFirstTimeShown = true;

  /**
   * @param aView View to display from {@link SeamlessBottomSheet}
   */
  public BottomSheetDialogFragmentUtil(View aView) {
    mView = aView;
  }

  /**
   * @param savedInstanceState Bundle
   * @return Dialog
   */
  @SuppressWarnings("NullableProblems")
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  /**
   * @param dialog Dialog to show
   * @param style  style of the dialog
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
   * @param dialog Dialog
   */
  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
  }
}
