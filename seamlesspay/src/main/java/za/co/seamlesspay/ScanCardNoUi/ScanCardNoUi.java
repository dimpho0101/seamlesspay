package za.co.seamlesspay.ScanCardNoUi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import za.co.seamlesspay.seamlessemv.NFCCardReader;
import za.co.seamlesspay.seamlessemv.model.EmvCard;

/**
 * Returns an EmvCard object. This class has no UI, the developer has to provide their own UI.
 */
public class ScanCardNoUi implements ScanCardNoUiCallback.Presentor {

  public NFCCardReader mNFCCardReader;

  private Disposable mDisposable = Disposables.empty();

  private EmvCard mEmvCard;

  private Throwable mThrowable;

  public ScanCardNoUi(Context aContext) {
    mNFCCardReader = new NFCCardReader((Activity) aContext);
  }

  private void createInstance(Intent aIntent, ScanCardNoUiCallback.State aState) {
    if (mNFCCardReader.isSuitableIntent(aIntent)) {
      mDisposable = mNFCCardReader
          .readCardRx2(aIntent)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              emvCard -> {
                aState.onSuccess(emvCard);
                stopReading();
              },
              throwable -> {
                aState.onError(throwable);
                stopReading();
              });
    }
  }

  public void dispose() {
    mDisposable.dispose();
  }

  public void initSdk() {
    mDisposable.dispose();
    mNFCCardReader.disableDispatch();
  }


  public void stopReading() {
    mDisposable.dispose();
    mNFCCardReader.disableDispatch();
  }


  @Override
  public void startReading(ScanCardNoUiCallback.State aState, Intent aIntent) {
    createInstance(aIntent, aState);
  }
}
