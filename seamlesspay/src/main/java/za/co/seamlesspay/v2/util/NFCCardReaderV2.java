package za.co.seamlesspay.v2.util;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;

import java.io.IOException;
import java.util.Collection;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import za.co.seamlesspay.v1.util.EmvUtil.AndroidNfcProvider;
import za.co.seamlesspay.v1.util.EmvUtil.NFCUtils;
import za.co.seamlesspay.v1.util.EmvUtil.log.Logger;
import za.co.seamlesspay.v1.util.EmvUtil.log.LoggerFactory;
import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;
import za.co.seamlesspay.v1.util.EmvUtil.parser.EmvParser;
import za.co.seamlesspay.v1.util.EmvUtil.utils.AtrUtils;
import za.co.seamlesspay.v1.util.EmvUtil.utils.BytesUtils;


@SuppressWarnings({"WeakerAccess", "unused"})
public class NFCCardReaderV2 {
  private NFCUtils nfcUtils;
  private AndroidNfcProvider provider;
  private Logger logger;

  public NFCCardReaderV2(Activity activity) {
    nfcUtils = new NFCUtils(activity);
    provider = new AndroidNfcProvider();
    logger = LoggerFactory.getLogger(NFCCardReaderV2.class);
  }

  /**
   * Begin waiting for bank card been tapped to NFC module of phone
   */
  public void enableDispatch() {
    nfcUtils.enableDispatch();
  }

  /**
   * Stop waiting for bank card
   */
  public void disableDispatch() {
    nfcUtils.disableDispatch();
  }

  /**
   * Checks that aTag is suitable for NFC card reading
   *
   * @param aTag aTag for check
   * @return true - aTag is good. false - this is not the aTag you're looking for
   */
  public boolean isSuitableaTag(Tag aTag) {
    if (aTag == null) {
      logger.debug("No TAG in aTag");
      return false;
    }

    IsoDep tagComm = IsoDep.get(aTag);
    if (tagComm == null) {
      logger.debug("IsoDep was not enumerated in getTechList()");
      return false;
    }

    return true;
  }

  /**
   * Read card data from given aTag.
   * <p>Note that this method is blocking. You should not use it as is.
   * Instead - wrap it into some async framework: RxJava, AsyncTask, etc</p>
   * <p>
   * <p>aTag by itself does not contain all data. It contains metadata of NFC card.
   * To read card data, library will open NFC connection and transfer some bytes.</p>
   * <p>
   * <p>You should check that this aTag contain right data with {@link #isSuitableaTag(Tag)}
   * before calling this method</p>
   *
   * @param aTag aTag with initial card information.
   * @return Ready for use card data
   * @throws IOException          may be thrown during NFC data transfer
   * @throws WrongaTagException thrown if aTag does not contain {@link NfcAdapter#EXTRA_TAG}
   * @throws WrongTagTech         thrown when this NFC tech is not supported:
   *                              not enumerated in {@link Tag#getTechList}.
   */
  public EmvCard readCardBlocking(Tag aTag)
      throws IOException, WrongaTagException, WrongTagTech {
    if (aTag == null) {
      throw new WrongaTagException("No TAG in aTag");
    }

    IsoDep tagComm = IsoDep.get(aTag);
    if (tagComm == null) {
      throw new WrongTagTech();
    }
    try {
      tagComm.connect();
      provider.setmTagCom(tagComm);
      EmvParser parser = new EmvParser(provider, true);
      return parser.readEmvCard();
    } finally {
      tagComm.close();
    }
  }

  /**
   * Get ATS from isoDep and find matching description
   */
  private Collection<String> extractAtsDescription(final IsoDep pIso) {
    byte[] pAts = null;
    if (pIso.isConnected()) {
      // Extract ATS from NFC-A
      pAts = pIso.getHistoricalBytes();
      if (pAts == null) {
        // Extract ATS from NFC-B
        pAts = pIso.getHiLayerResponse();
      }
    }
    return AtrUtils.getDescriptionFromAts(BytesUtils.bytesToString(pAts));
  }

  /**
   * Read card data from given aTag.
   * <p>aTag by itself does not contain all data. It contains metadata of NFC card.
   * To read card data, library will open NFC connection and transfer some bytes.</p>
   * <p>You should check that this aTag contain right data with {@link #isSuitableaTag(Tag)}
   * before calling this method</p>
   * <p>Operates on IO scheduler</p>
   *
   * @param aTag aTag with initial card information.
   */
  public Single<EmvCard> readCardRx1(final Tag aTag) {
    return readCardRx1(aTag, Schedulers.io());
  }

  /**
   * Read card data from given aTag.
   * <p>aTag by itself does not contain all data. It contains metadata of NFC card.
   * To read card data, library will open NFC connection and transfer some bytes.</p>
   * <p>You should check that this aTag contain right data with {@link #isSuitableaTag(Tag)}
   * before calling this method</p>
   * <p>Operates on given scheduler</p>
   *
   * @param aTag    aTag with initial card information.
   * @param scheduler scheduler for operating
   */
  public Single<EmvCard> readCardRx1(final Tag aTag, Scheduler scheduler) {
    return Single
        .fromCallable(() -> readCardBlocking(aTag))
        .onErrorResumeNext(Single::error)
        .subscribeOn(scheduler);
  }

  /**
   * Read card data from given aTag.
   * <p>aTag by itself does not contain all data. It contains metadata of NFC card.
   * To read card data, library will open NFC connection and transfer some bytes.</p>
   * <p>You should check that this aTag contain right data with {@link #isSuitableaTag(Tag)}
   * before calling this method</p>
   * <p>Operates on IO scheduler</p>
   *
   * @param aTag aTag with initial card information.
   */
  public Single<EmvCard> readCardRx2(final Tag aTag) {
    return readCardRx2(aTag, Schedulers.io());
  }

  /**
   * Read card data from given aTag.
   * <p>aTag by itself does not contain all data. It contains metadata of NFC card.
   * To read card data, library will open NFC connection and transfer some bytes.</p>
   * <p>You should check that this aTag contain right data with {@link #isSuitableaTag(Tag)}
   * before calling this method</p>
   * <p>Operates on given scheduler</p>
   *
   * @param aTag    aTag with initial card information.
   * @param scheduler scheduler for operating
   */
  public Single<EmvCard> readCardRx2(final Tag aTag, Scheduler scheduler) {
    return Single.
        fromCallable(() -> readCardBlocking(aTag))
        .subscribeOn(scheduler);
  }

  public static class WrongaTagException extends Exception {
    WrongaTagException(String detailMessage) {
      super(detailMessage);
    }
  }

  public static class WrongTagTech extends Exception {
    WrongTagTech() {
      super("IsoDep was not enumerated in getTechList()");
    }
  }
}
