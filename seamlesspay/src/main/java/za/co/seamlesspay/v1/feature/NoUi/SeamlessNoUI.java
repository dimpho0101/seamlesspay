package za.co.seamlesspay.v1.feature.NoUi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import io.reactivex.disposables.Disposable;
import za.co.seamlesspay.v1.interfaces.SeamlessObserver;
import za.co.seamlesspay.v1.interfaces.SeamlessObserver.CreateResource;
import za.co.seamlesspay.v1.util.EmvUtil.NFCCardReader;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.disposables.Disposables.empty;

/**
 * Returns an EmvCard object. This class has no UI, it will only return the required information when called
 */
public class SeamlessNoUI implements CreateResource {

  /**
   * NfcCardReader
   */
  @SuppressWarnings("WeakerAccess")
  public NFCCardReader mNFCCardReader;

  /**
   * Dispose the resource, the operation should be idempotent.
   */
  private Disposable mDisposable = empty();

  /**
   * @param aContext Context from the calling activity/fragment
   */
  public SeamlessNoUI(Context aContext) {
    mNFCCardReader = new NFCCardReader((Activity) aContext);
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
   * Stops reading for the credit card
   */
  @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
  public void stopReading() {
    mDisposable.dispose();
    mNFCCardReader.disableDispatch();
  }

  /**
   * @param aResourceStatus Status of the resource
   * @param aIntent         Intent from the calling activity
   */
  @Override
  public void startReading(SeamlessObserver.ResourceStatus aResourceStatus, Intent aIntent) {
    createInstance(aIntent, aResourceStatus);
  }
}
