package za.co.seamlesspay.v1.util.EmvUtil.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import za.co.seamlesspay.v1.util.EmvUtil.iso7816emv.EmvTags;
import za.co.seamlesspay.v1.util.EmvUtil.log.Logger;
import za.co.seamlesspay.v1.util.EmvUtil.log.LoggerFactory;
import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;
import za.co.seamlesspay.v1.util.EmvUtil.model.Service;


/**
 * Extract track data
 */
public final class TrackUtils {

	/**
	 * Class logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TrackUtils.class);

	/**
	 * Track 2 pattern
	 */
	private static final Pattern TRACK2_PATTERN = Pattern.compile("([0-9]{1,19})D([0-9]{4})([0-9]{3})?(.*)");

	/**
	 * Extract track 2 data
	 * 
	 * @param pEmvCard
	 *            Object card representation
	 * @param pData
	 *            data to parse
	 * @return true if the extraction succeed false otherwise
	 */
	public static boolean extractTrack2Data(final EmvCard pEmvCard, final byte[] pData) {
		boolean ret = false;
		byte[] track2 = TlvUtil.getValue(pData, EmvTags.TRACK_2_EQV_DATA, EmvTags.TRACK2_DATA);

		if (track2 != null) {
			String data = BytesUtils.bytesToStringNoSpace(track2);
			Matcher m = TRACK2_PATTERN.matcher(data);
			// Check pattern
			if (m.find()) {
				// read card number
				pEmvCard.setCardNumber(m.group(1));
				// Read expire date
				SimpleDateFormat sdf = new SimpleDateFormat("yyMM", Locale.getDefault());
				try {
					pEmvCard.setExpireDate(CommonsUtils.truncate(sdf.parse(m.group(2)), Calendar.MONTH));
				} catch (ParseException e) {
					LOGGER.error("Unparsable expire card date : {}", e);
					return ret;
				}
				// Read service
				pEmvCard.setService(new Service(m.group(3)));
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * Private constructor
	 */
	private TrackUtils() {
	}

}
