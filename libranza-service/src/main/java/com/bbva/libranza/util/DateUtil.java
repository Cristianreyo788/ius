package com.bbva.libranza.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private static SimpleDateFormat FEEDBACK_DATE_FORMAT =  new SimpleDateFormat("yyyyMMdd");

	private static SimpleDateFormat FEEDBACK_DATE_TIME_FORMAT =  new SimpleDateFormat("yyyyMMddHHmmss'Z'");

	private static SimpleDateFormat JSON_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");

	public static String getCurrentDateAsString(boolean withTime)
	{
		if(withTime) 
		{
			return DATE_TIME_FORMAT.format(new Date());
		}
		
		return DATE_FORMAT.format(new Date());
	}

    public static String getJSONCurrentDateTime(Date date)
    {
        return JSON_DATE_TIME_FORMAT.format(date != null ? date : new Date());
    }

	public static String getFeedbackCurrentDate() {
		return FEEDBACK_DATE_FORMAT.format(new Date()) + "000000Z";
	}

	public static boolean isTodayDate(Date date) {

		if(date != null) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(new Date());

			return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
					cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
					cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
		}

		return false;
	}

	public static Date parseFeedbackDateTime(String stringDate) throws Exception{
		if(stringDate != null && !stringDate.isEmpty()) {
			return FEEDBACK_DATE_TIME_FORMAT.parse(stringDate);
		}

		return null;
	}
	
	String fileNameProcess() {
		int mesFin = Calendar.getInstance().get(Calendar.MONTH);
		
		return "INF_DIA_DISPO_F"+ Calendar.getInstance().get(Calendar.YEAR) +
								  (mesFin < 10 ? "0" + mesFin : mesFin) +
							      Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +
				".F"+ Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}	
}