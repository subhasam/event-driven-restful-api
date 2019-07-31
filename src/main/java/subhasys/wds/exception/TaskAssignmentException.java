/**
 * 
 */
package subhasys.wds.exception;

/**
 * @author subhasis
 *
 */
public class TaskAssignmentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005637728844308987L;

	private String errorMessage;

	/**
	 * @param errorMessage
	 */
	public TaskAssignmentException(String errorMessage, Throwable cause) {
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
