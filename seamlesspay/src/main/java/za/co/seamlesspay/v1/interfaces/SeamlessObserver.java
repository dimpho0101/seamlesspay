package za.co.seamlesspay.v1.interfaces;

import android.content.Intent;

import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;

/**
 * Interface
 */
public interface SeamlessObserver {

  /**
   * Tells us the status of the resource. If the user successfully
   * scanned a card, we return onSuccess with an {EmvCard object}
   * <p>
   * If there is an error, such as the user moving the card to fast
   * or the cards NFC being locked, we return an error with a Throwable
   */
  interface ResourceStatus {

    /**
     * @param aEmvCard object which contains information about the credit card
     */
    void onSuccess(EmvCard aEmvCard);

    /**
     * @param aThrowable which contains the error
     */
    void onError(Throwable aThrowable);
  }

  /**
   * Creates a resource to start reading the credit card.
   */
  interface CreateResource {

    /**
     * @param aResourceStatus Status of the resource
     * @param aIntent         Intent from the calling activity
     */
    void startReading(ResourceStatus aResourceStatus, Intent aIntent);
  }

}
