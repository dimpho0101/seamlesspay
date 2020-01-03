package za.co.seamlesspay.v1.feature.NoUi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import io.reactivex.disposables.Disposable;
import za.co.seamlesspay.v1.interfaces.EmvCallback;
import za.co.seamlesspay.v1.interfaces.EmvCallback.CreateResource;
import za.co.seamlesspay.v1.util.EmvUtil.NFCCardReader;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.disposables.Disposables.empty;

/**
 * Returns an EmvCard object. This class has no UI, it will only return the required information when called
 */
public class EmvReader implements CreateResource {

  /**
   * NfcCardReader
   */
  public NFCCardReader mNFCCardReader;

  /**
   * Dispose the resource, the operation should be idempotent.
   */
  private Disposable mDisposable = empty();

  /**
   * @param aContext Context from the calling activity/fragment
   */
  public EmvReader(Context aContext) {
    mNFCCardReader = new NFCCardReader((Activity) aContext);
  }

  /**
   * Start Reading for the credit card
   */
  public void enableDispatch() {
    mNFCCardReader.enableDispatch();
  }

  /**
   * Stops reading for the credit card
   */
  public void stopReading() {
    mDisposable.dispose();
    mNFCCardReader.disableDispatch();
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
                EmvReader.this.stopReading();
              },
              throwable -> {
                aResourceStatus.onSuccess(null, throwable);
                EmvReader.this.stopReading();
              });
    }
  }
}
