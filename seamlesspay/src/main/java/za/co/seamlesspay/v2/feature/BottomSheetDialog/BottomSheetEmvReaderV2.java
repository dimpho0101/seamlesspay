package za.co.seamlesspay.v2.feature.BottomSheetDialog;

import android.app.Activity;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.disposables.Disposable;
import za.co.seamlesspay.R;
import za.co.seamlesspay.databinding.SeamlessBottomSheetLayoutBinding;
import za.co.seamlesspay.v1.interfaces.EmvCallback;
import za.co.seamlesspay.v1.util.AnimationUtil.AnimationUtil;
import za.co.seamlesspay.v1.util.BottomSheetDialogFragmentUtil.BottomSheetDialogFragmentUtil;
import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;
import za.co.seamlesspay.v2.util.NFCCardReaderV2;
import za.co.seamlesspay.v2.viewmodel.EmvViewModel;

import static android.nfc.NfcAdapter.FLAG_READER_NFC_A;
import static android.nfc.NfcAdapter.FLAG_READER_NFC_B;
import static android.nfc.NfcAdapter.FLAG_READER_NFC_BARCODE;
import static android.nfc.NfcAdapter.FLAG_READER_NFC_F;
import static android.nfc.NfcAdapter.FLAG_READER_NFC_V;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class BottomSheetEmvReaderV2 implements EmvCallback.CreateResourceV2 {

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
   * NfcCardReader
   */
  private NFCCardReaderV2 mNFCCardReader;

  /**
   * BottomSheetDialogUtil class for showing BottomSheetDialogFragment
   */
  private BottomSheetDialogFragmentUtil mBottomSheetDialogFragmentUtil;

  /**
   *
   */
  @SuppressWarnings("FieldCanBeLocal")
  private static String JSON_ANIMATION = "circles.json";

  /**
   *
   */
  private EmvViewModel mViewModel;

  /**
   * Constructor
   */
  public BottomSheetEmvReaderV2(Context aContext, FragmentManager aFragmentManager) {
    mContext = aContext;
    mFragmentManager = aFragmentManager;
    mNFCCardReader = new NFCCardReaderV2((Activity) mContext);
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void readCreditCard() {
    configureViews();
    mViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(EmvViewModel.class);
    NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(mContext);
    nfcAdapter.enableReaderMode((Activity) mContext, tag -> startReading(this::setText, tag), FLAG_READER_NFC_A | FLAG_READER_NFC_B | FLAG_READER_NFC_F | FLAG_READER_NFC_V | FLAG_READER_NFC_BARCODE, null);
  }

  private void setText(@Nullable EmvCard aEmvCard, @Nullable Throwable aThrowable) {
    mViewModel.getIntentMutableLiveData().setValue(aEmvCard != null ? aEmvCard : new EmvCard());
    stopReading();
  }

  /**
   * Stops reading for the credit card
   */
  public void stopReading() {
    if (mBottomSheetDialogFragmentUtil != null) {
      mBottomSheetDialogFragmentUtil.dismiss();
    }
  }

  /**
   * Configure the bottomsheetdialog which the user will see
   */
  public void configureViews() {
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
   * @param aTag            Intent from the calling activity
   */
  @SuppressWarnings("unused")
  @Override
  public void startReading(EmvCallback.ResourceStatus aResourceStatus, Tag aTag) {
    if (mNFCCardReader.isSuitableaTag(aTag)) {
      Disposable disposable = mNFCCardReader
          .readCardRx2(aTag)
          .observeOn(mainThread())
          .subscribe(
              emvCard -> aResourceStatus.onSuccess(emvCard, null),
              throwable -> aResourceStatus.onSuccess(null, throwable));
    }
  }
}
