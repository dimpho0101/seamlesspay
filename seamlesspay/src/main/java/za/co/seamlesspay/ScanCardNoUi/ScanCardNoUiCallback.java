package za.co.seamlesspay.ScanCardNoUi;

import android.content.Intent;

import za.co.seamlesspay.seamlessemv.model.EmvCard;

public interface ScanCardNoUiCallback {

  interface State {
    void onSuccess(EmvCard aEmvCard);

    void onError(Throwable aThrowable);
  }

  interface Presentor {
    void startReading(State aState, Intent aIntent);
  }

}
