package za.co.seamlesspay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import za.co.seamlesspay.databinding.BottomSheetDialogBinding;
import za.co.seamlesspay.seamlessemv.NFCCardReader;
import za.co.seamlesspay.seamlessemv.model.EmvCard;

public class ScanCardBottomSheetDialog {

  private Context mContext;

  private FragmentManager mFragmentManager;

  private AnimationUtil mAnimationUtil = new AnimationUtil();

  private BottomSheetDialogBinding mBottomSheetDialogBinding;

  private Disposable mDisposable = Disposables.empty();

  public NFCCardReader mNFCCardReader;

  BottomSheetDialogUtil mBottomSheetDialogUtil;

  public ScanCardBottomSheetDialog(Context aContext, FragmentManager aFragmentManager) {
    mContext = aContext;
    mFragmentManager = aFragmentManager;
    mNFCCardReader = new NFCCardReader((Activity) mContext);
  }

  public void createInstance(Intent aIntent) {
    if (mNFCCardReader.isSuitableIntent(aIntent)) {
      mDisposable = mNFCCardReader
          .readCardRx2(aIntent)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              card -> showCardInfo(card),
              this::showError);
    }
  }

  public void dispose() {
    mDisposable.dispose();
  }

  public void initSdk() {
    mBottomSheetDialogBinding = DataBindingUtil.setContentView(((Activity) mContext), R.layout.bottom_sheet_dialog);
    mBottomSheetDialogUtil = new BottomSheetDialogUtil(mContext, mBottomSheetDialogBinding.getRoot());

    mBottomSheetDialogBinding.cancelButton.setOnClickListener(aView -> {
      mDisposable.dispose();
      mNFCCardReader.disableDispatch();
      mBottomSheetDialogUtil.dismiss();
    });

    mAnimationUtil.playAnimation(mBottomSheetDialogBinding.creditCardInclude.rippleAnimation, "circles.json");
    mBottomSheetDialogUtil.show(mFragmentManager, "");
  }

  private void showCardInfo(EmvCard card) {
    mBottomSheetDialogBinding.creditCardInclude.cardNumber.setText(CardUtils.formatCardNumber(card.getCardNumber(), card.getType()));
    mBottomSheetDialogBinding.creditCardInclude.expDate.setText(DateFormat.format("M/y", card.getExpireDate()));
    mDisposable.dispose();
    mNFCCardReader.disableDispatch();
    mBottomSheetDialogUtil.dismiss();
    //CustomDialog customDialog = new CustomDialog(((Activity) mContext));
    //customDialog.show();
  }

  private void showError(Throwable aThrowable) {
    mBottomSheetDialogBinding.message.setText(aThrowable.getMessage());
  }
}
