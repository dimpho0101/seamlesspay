package za.co.seamlesspay.seamlesstap.seamlesstapbottomsheet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import za.co.seamlesspay.R;
import za.co.seamlesspay.databinding.BottomSheetDialogBinding;
import za.co.seamlesspay.seamlesstap.SeamlessObserver;
import za.co.seamlesspay.util.AnimationUtil;
import za.co.seamlesspay.util.BottomSheetDialogUtil;
import za.co.seamlesspay.util.seamlessemv.NFCCardReader;

import static androidx.databinding.DataBindingUtil.setContentView;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class TapBottomSheet implements SeamlessObserver.CreateResource {

  /**
   * Context
   */
  private Context mContext;

  /**
   * We need this for the bottomsheetdialog
   */
  private FragmentManager mFragmentManager;

  /**
   * To show animations using the lottie library
   */
  private AnimationUtil mAnimationUtil = new AnimationUtil();

  /**
   *
   */
  private Disposable mDisposable = Disposables.empty();

  /**
   *
   */
  public NFCCardReader mNFCCardReader;

  /**
   *
   */
  private BottomSheetDialogUtil mBottomSheetDialogUtil;

  /**
   *
   */
  public TapBottomSheet(Context aContext, FragmentManager aFragmentManager) {
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
    mBottomSheetDialogUtil = new BottomSheetDialogUtil(bottomSheetDialogBinding.getRoot());
    mAnimationUtil.playAnimation(bottomSheetDialogBinding.creditCardInclude.rippleAnimation, "circles.json");
    bottomSheetDialogBinding.cancelButton.setOnClickListener(aView -> stopReading());
    mBottomSheetDialogUtil.show(mFragmentManager, "");
  }

  /**
   * Stops reading for the credit card
   */
  @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
  public void stopReading() {
    mDisposable.dispose();
    mNFCCardReader.disableDispatch();
//    mBottomSheetDialogUtil.dismiss();
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
