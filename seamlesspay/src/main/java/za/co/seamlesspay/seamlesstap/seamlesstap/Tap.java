package za.co.seamlesspay.seamlesstap.seamlesstap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import za.co.seamlesspay.seamlesstap.SeamlessObserver;
import za.co.seamlesspay.util.seamlessemv.NFCCardReader;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.disposables.Disposables.empty;

/**
 * Returns an EmvCard object. This class has no UI, it will only return the required information when called
 */
public class Tap implements SeamlessObserver.CreateResource {

  /**
   *
   */
  public NFCCardReader mNFCCardReader;

  /**
   *
   */
  private Disposable mDisposable = empty();

  /**
   * @param aContext Context from the calling activity/fragment
   */
  public Tap(Context aContext) {
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
