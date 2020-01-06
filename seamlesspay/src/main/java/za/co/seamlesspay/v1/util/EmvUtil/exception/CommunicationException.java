package za.co.seamlesspay.v1.util.EmvUtil.exception;

import java.io.IOException;

/**
 * Exception during communication with EMV card
 */
public class CommunicationException extends IOException {

	/**
	 * Generated serial UID
	 */
	private static final long serialVersionUID = -7916924250407562185L;

	/**
	 * Default constructor
	 */
	public CommunicationException() {
		super();
	}

	/**
	 * Default constructor
	 * 
	 * @param pMessage
	 *            Exception message
	 */
	public CommunicationException(final String pMessage) {
		super(pMessage);
	}

}
