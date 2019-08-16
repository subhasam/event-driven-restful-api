/**
 * 
 */
package subhasys.wds.exception;

/**
 * @author subhasis
 *
 */
public class WdsApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005637728844308987L;

	private final String errorMessage;

	/**
	 * @param errorMessage
	 */
	public WdsApiException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
