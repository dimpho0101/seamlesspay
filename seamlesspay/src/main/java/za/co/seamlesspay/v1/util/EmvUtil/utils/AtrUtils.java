package za.co.seamlesspay.v1.util.EmvUtil.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import za.co.seamlesspay.v1.util.EmvUtil.log.Logger;
import za.co.seamlesspay.v1.util.EmvUtil.log.LoggerFactory;

import static za.co.seamlesspay.v1.util.EmvUtil.utils.CommonsUtils.UTF_8;
import static za.co.seamlesspay.v1.util.EmvUtil.utils.CommonsUtils.deleteWhitespace;


/**
 * Class used to find ATR description
 */
public final class AtrUtils {

	/**
	 * Class logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AtrUtils.class);

	/**
	 * MultiMap containing ATR
	 */
	private static final Multimap<String, String> MAP = Multimap.create();

	static {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			is = AtrUtils.class.getResourceAsStream("/smartcard_list.txt");
			isr = new InputStreamReader(is, UTF_8);
			br = new BufferedReader(isr);

			int lineNumber = 0;
			String line;
			String currentATR = null;
			while ((line = br.readLine()) != null) {
				++lineNumber;
				if (line.startsWith("#") || line.trim().length() == 0) { // comment ^#/ empty line ^$/
					continue;
				} else if (line.startsWith("\t") && currentATR != null) {
					MAP.putElement(currentATR, line.replace("\t", "").trim());
				} else if (line.startsWith("3")) { // ATR hex
					currentATR = deleteWhitespace(line.toUpperCase());
				} else {
					LOGGER.error("Encountered unexpected line in atr list: currentATR=" + currentATR + " Line(" + lineNumber
							+ ") = " + line);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			CommonsUtils.closeQuietly(br);
			CommonsUtils.closeQuietly(isr);
			CommonsUtils.closeQuietly(is);
		}
	}

	/**
	 * Method used to find description from ATR
	 * 
	 * @param pAtr
	 *            Card ATR
	 * @return list of description
	 */
	@SuppressWarnings("unchecked")
	public static final Collection<String> getDescription(final String pAtr) {
		Collection<String> ret = null;
		if (CommonsUtils.isNotBlank(pAtr)) {
			String val = deleteWhitespace(pAtr);
			for (String key : MAP.keySet()) {
				if (val.matches("^" + key + "$")) {
					ret = (Collection<String>) MAP.get(key);
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * Method used to find ATR description from ATS (Answer to select)
	 * 
	 * @param pAts
	 *            EMV card ATS
	 * @return card description
	 */
	@SuppressWarnings("unchecked")
	public static final Collection<String> getDescriptionFromAts(final String pAts) {
		Collection<String> ret = null;
		if (CommonsUtils.isNotBlank(pAts)) {
			String val = deleteWhitespace(pAts);
			for (String key : MAP.keySet()) {
				if (key.contains(val)) { // TODO Fix this
					ret = (Collection<String>) MAP.get(key);
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * Private constructor
	 */
	private AtrUtils() {
	}
}
