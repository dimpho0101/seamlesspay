package za.co.seamlesspay.v1.util.BottomSheetDialogFragmentUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import za.co.seamlesspay.v1.feature.BottomSheetDialog.BottomSheetEmvReader;

import static android.R.color.transparent;

public class BottomSheetDialogFragmentUtil extends BottomSheetDialogFragment {

  /**
   * View to display from {@link BottomSheetEmvReader}
   */
  private View mView;

  /**
   * @param aView View to display from {@link BottomSheetEmvReader}
   */
  public BottomSheetDialogFragmentUtil(View aView) {
    mView = aView;
  }

  /**
   * @param dialog Dialog to show
   * @param style  style of the dialog
   */
  @SuppressLint("RestrictedApi")
  @Override
  public void setupDialog(@NonNull Dialog dialog, int style) {
    super.setupDialog(dialog, style);
    if (mView != null) {
      dialog.setContentView(mView);
      ((View) mView.getParent()).setBackgroundColor(getResources().getColor(transparent));
    } else {
      dismiss();
    }
  }

}
