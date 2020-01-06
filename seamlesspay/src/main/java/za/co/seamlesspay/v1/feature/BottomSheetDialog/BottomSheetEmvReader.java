package za.co.seamlesspay.v1.feature.BottomSheetDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import za.co.seamlesspay.R;
import za.co.seamlesspay.databinding.SeamlessBottomSheetLayoutBinding;
import za.co.seamlesspay.v1.interfaces.EmvCallback;
import za.co.seamlesspay.v1.util.AnimationUtil.AnimationUtil;
import za.co.seamlesspay.v1.util.BottomSheetDialogFragmentUtil.BottomSheetDialogFragmentUtil;
import za.co.seamlesspay.v1.util.EmvUtil.NFCCardReader;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class BottomSheetEmvReader implements EmvCallback.CreateResource {

  /**
   * Context from calling activity
   */
  private Context mContext;

  /**
   * FragmentManager for showing the BottomSheetDialog
   */
  private FragmentManager mFragmentManager;

  /**
   * Util class for showing animations using the lottie library
   */
  private AnimationUtil mAnimationUtil = new AnimationUtil();

  /**
   * Dispose the resource, the operation should be idempotent.
   */
  private Disposable mDisposable = Disposables.empty();

  /**
   * NfcCardReader
   */
  private NFCCardReader mNFCCardReader;

  /**
   * BottomSheetDialogUtil class for showing BottomSheetDialogFragment
   */
  private BottomSheetDialogFragmentUtil mBottomSheetDialogFragmentUtil;

  private static String JSON_ANIMATION = "circles.json";

  /**
   * Constructor
   */
  public BottomSheetEmvReader(Context aContext, FragmentManager aFragmentManager) {
    mContext = aContext;
    mFragmentManager = aFragmentManager;
    mNFCCardReader = new NFCCardReader((Activity) mContext);
  }

  /**
   * Start Reading for the credit card
   */
  public void enableDispatch() {
    mNFCCardReader.enableDispatch();
    configureViews();
  }

  /**
   * Stops reading for the credit card
   */
  public void stopReading() {
    mDisposable.dispose();
    mNFCCardReader.disableDispatch();
    if (mBottomSheetDialogFragmentUtil != null) {
      mBottomSheetDialogFragmentUtil.dismiss();
    }
  }

  /**
   * Configure the bottomsheetdialog which the user will see
   */
  private void configureViews() {
    SeamlessBottomSheetLayoutBinding bottomSheetDialogBinding = DataBindingUtil.inflate(((Activity) mContext).getLayoutInflater(), R.layout.seamless_bottom_sheet_layout,
        null, false);
    mBottomSheetDialogFragmentUtil = new BottomSheetDialogFragmentUtil(bottomSheetDialogBinding.getRoot());
    mAnimationUtil.playAnimation(bottomSheetDialogBinding.creditCardInclude.rippleAnimation, JSON_ANIMATION);
    bottomSheetDialogBinding.cancelButton.setOnClickListener(aView -> stopReading());
    mBottomSheetDialogFragmentUtil.setCancelable(false);
    mBottomSheetDialogFragmentUtil.show(mFragmentManager, "");
  }

  /**
   * @param aResourceStatus Status of the resource
   * @param aIntent         Intent from the calling activity
   */
  @Override
  public void startReading(EmvCallback.ResourceStatus aResourceStatus, Intent aIntent) {
    if (mNFCCardReader.isSuitableIntent(aIntent)) {
      mDisposable = mNFCCardReader
          .readCardRx2(aIntent)
          .observeOn(mainThread())
          .subscribe(
              emvCard -> {
                aResourceStatus.onSuccess(emvCard, null);
                stopReading();
              },
              throwable -> {
                aResourceStatus.onSuccess(null, throwable);
                stopReading();
              });
    }
  }
}
