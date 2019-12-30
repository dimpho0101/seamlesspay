package za.co.seamlesspay.v1.feature.BottomSheetDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import za.co.seamlesspay.R;
import za.co.seamlesspay.databinding.BottomSheetDialogBinding;
import za.co.seamlesspay.v1.interfaces.SeamlessObserver;
import za.co.seamlesspay.v1.util.AnimationUtil.AnimationUtil;
import za.co.seamlesspay.v1.util.BottomSheetDialogFragmentUtil.BottomSheetDialogFragmentUtil;
import za.co.seamlesspay.v1.util.EmvUtil.NFCCardReader;

import static androidx.databinding.DataBindingUtil.setContentView;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class SeamlessBottomSheet implements SeamlessObserver.CreateResource {

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
  public NFCCardReader mNFCCardReader;

  /**
   * BottomSheetDialogUtil class for showing BottomSheetDialogFragment
   */
  @SuppressWarnings("FieldCanBeLocal")
  private BottomSheetDialogFragmentUtil mBottomSheetDialogFragmentUtil;

  /**
   * Constructor
   */
  public SeamlessBottomSheet(Context aContext, FragmentManager aFragmentManager) {
    mContext = aContext;
    mFragmentManager = aFragmentManager;
    mNFCCardReader = new NFCCardReader((Activity) mContext);
  }

  /**
   * @param aIntent         Intent from the calling activity/fragment
   * @param aResourceStatus Status of the resource
   */
  private void createInstance(Intent aIntent, SeamlessObserver.ResourceStatus aResourceStatus) {
    if (mNFCCardReader.isSuitableIntent(aIntent)) {
      mDisposable = mNFCCardReader
          .readCardRx2(aIntent)
          .observeOn(mainThread())
          .subscribe(
              emvCard -> {
                aResourceStatus.onSuccess(emvCard);
                stopReading();
              },
              throwable -> {
                aResourceStatus.onError(throwable);
                stopReading();
              });
    }
  }

  /**
   * Configure the bottomsheetdialog which the user will see
   */
  private void configureViews() {
    BottomSheetDialogBinding bottomSheetDialogBinding = setContentView(((Activity) mContext), R.layout.bottom_sheet_dialog);
    mBottomSheetDialogFragmentUtil = new BottomSheetDialogFragmentUtil(bottomSheetDialogBinding.getRoot());
    mAnimationUtil.playAnimation(bottomSheetDialogBinding.creditCardInclude.rippleAnimation, "circles.json");
    bottomSheetDialogBinding.cancelButton.setOnClickListener(aView -> stopReading());
    mBottomSheetDialogFragmentUtil.show(mFragmentManager, "");
  }

  /**
   * Stops reading for the credit card
   */
  @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
  public void stopReading() {
    mDisposable.dispose();
    mNFCCardReader.disableDispatch();
    //mBottomSheetDialogUtil.dismiss();
  }

  /**
   * @param aResourceStatus Status of the resource
   * @param aIntent         Intent from the calling activity
   */
  @Override
  public void startReading(SeamlessObserver.ResourceStatus aResourceStatus, Intent aIntent) {
    configureViews();
    createInstance(aIntent, aResourceStatus);
  }

}
