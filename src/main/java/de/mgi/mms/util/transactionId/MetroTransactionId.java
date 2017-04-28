package de.mgi.mms.util.transactionId;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Will create a pim like transaction number
 * 
 */
public class MetroTransactionId {

	/** The year to set. */
	private static final int	YEAR			= 2008;

	/** The month to set(January). */
	private static final int	MONTH			= 0;

	/** The day of month(first). */
	private static final int	DAY				= 1;

	/** The hour of day(0). */
	private static final int	HOUR			= 0;

	/** The minute of hour(0). */
	private static final int	MINUTE			= 0;

	/** The second of minute(0). */
	private static final int	SECOND			= 0;

	/** The decimal format. */
	private static final String	DECIMAL_PATTERN	= "#00000000000";

	/**
	 * Constructor.
	 * 
	 * The Id will be generated as a time difference in milliseconds between
	 * current time and 1st January 2008.
	 * 
	 */
	public static BigInteger create() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);
		calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));

		long pimIdLong = System.currentTimeMillis() - calendar.getTimeInMillis();

		DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_PATTERN);

		return new BigInteger(decimalFormat.format(pimIdLong));
	}

}
