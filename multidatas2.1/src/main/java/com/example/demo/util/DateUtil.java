package com.example.demo.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String DATE_CUT_MONTH = "yyyy-MM";
	public static final String DATE_CUT_DAY = "yyyy-MM-dd";
	public static final String DATE_ON_DAY = "dd";
	public final static String DATE_FULL_DATE = "yyyy-MM-dd HH:mm:ss";

	public static void main(String[] args) {
		/*int i = Integer.parseInt(formatString(new Date(), DATE_ON_DAY));
		if (i > 4) {
			String startDate = DateUtil.formatString(getMonthBefrom(0), DATE_CUT_DAY);
			String endDate = DateUtil.formatString(new Date(), DATE_CUT_DAY);
		} else {
			String startDate = DateUtil.formatString(getMonthBefrom(-1), DATE_CUT_DAY);
			String endDate = DateUtil.formatString(new Date(), DATE_CUT_DAY);
		}*/
		System.out.println("================"+new Date().getTime());
		long currentTime = new Date().getTime() + 30 * 60 * 1000;
		Date date = new Date(currentTime);
		/*DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime="";nowTime= df.format(date);*/
		System.out.println(date.getTime());
	}

	public static String dayBeforeToday(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);// 24小时制，而calendar.set(Calendar.HOUR,0);则是12小时制
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.HOUR, 24 * days);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_CUT_DAY);
		return simpleDateFormat.format(calendar.getTime());
	}

	// 获取某月的第一天
	public static Date getMonthBefrom(int month) {
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, month);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return cale.getTime();
	}

	// 获取前月的最后一天
	public static Date getMonthEnd() {
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return cale.getTime();
	}
	public static Date getMonthStart() {
		return getMonthBefrom(-1);
	}

	public static String getStringBefromMonth(Date dBefore, int key) {
		Date befromMonth = getBefromMonth(dBefore, key);
		return formatString(befromMonth, DATE_CUT_MONTH);
	}

	public static Date getBefromMonth(Date dBefore, int key) {
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dBefore);// 把当前时间赋给日历
		calendar.add(Calendar.MONTH, key); // 设置为前几月
		dBefore = calendar.getTime();
		return dBefore;
	}

	public static String formatString(Date date, String model) {
		SimpleDateFormat format = new SimpleDateFormat(model);
		String format1 = format.format(date);
		return format1;
	}

	/**
	 * 格式化字符串日期<br>
	 * 字符串格式为 yyyyMMddHHmmss
	 * 
	 * @param
	 *
	 * @return Date 实例 <br>
	 *         null 失败
	 */

	public static Date formatDate(String stringTime, String model) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(model);
			Date date = new Date();
			date = format.parse(stringTime);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static long formatStringUnixTime(String stringTime, String formatStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			Date date = new Date();
			date = format.parse(stringTime);
			return date.getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取当前时间的yyyy-MM-dd HH:mm:ss字符串格式
	 * 
	 * @return 成功： yyyy-MM-dd HH:mm:ss 字符串 <br>
	 *         失败： null
	 */
	public static String getCurrentTimeHaveHR() {
		try {
			String stringTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			return stringTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getTime(long date) {
		try {
			String stringTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(date));
			return stringTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getYMD(long date) {
		try {
			String stringTime = new SimpleDateFormat("yyyy/MM/dd").format(new Date(date));
			return stringTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getDateFormat(String format) {
		try {
			String stringTime = new SimpleDateFormat(format).format(new Date());
			return stringTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当前时间的 yyyyMMddHHmmss 字符串格式
	 * 
	 * @return 成功： yyyyMMddHHmmss 字符串 <br>
	 *         失败： null
	 */
	public static String getCurrentTime() {
		try {
			String stringTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			return stringTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Title: getCurrentTimeYMD
	 * @Description: 获取当前时间的yyyy-MM-dd字符串格式
	 * @return
	 */
	public static String getCurrentTimeYMD() {
		try {
			String stringTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			return stringTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date  getDate() {
		Date parse =null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 parse = format.parse(format.format(new Date()));
			return parse;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parse;
	}


	public static long unixTime() {
		return (long) (System.currentTimeMillis() / 1000L);
	}

	public static int getTodayStartTime() {
		Calendar time = Calendar.getInstance();
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		return (int) (time.getTimeInMillis() / 1000L);
	}

	public static int getTodayEndTime() {
		int time = getTodayStartTime();
		return time + (24 * 60 * 60) - 1;
	}

	public static int getMonthStartTime() {
		Calendar time = Calendar.getInstance();
		time.add(Calendar.MONTH, 0);
		time.set(Calendar.DAY_OF_MONTH, 1);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		return (int) (time.getTimeInMillis() / 1000L);
	}

	public static int getMonthEndTime() {
		Calendar time = Calendar.getInstance();
		time.add(Calendar.MONTH, 1);
		time.set(Calendar.DAY_OF_MONTH, 0);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		return (int) (time.getTimeInMillis() / 1000L);
	}

	public static String formatDate(Date date, String df) {
		SimpleDateFormat sdf = new SimpleDateFormat(df);
		return sdf.format(date);
	}

	/************************************************************/
	/**
	 * 获取当前时间前N个月的年月日
	 * 
	 * @return
	 */
	public static Date getDateBeforeNMonths(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -n); // 得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		return calendar.getTime();
	}

	/**
	 * 获取当前时间前N个天的年月日
	 * 
	 * @return
	 */
	public static Date getDateBeforeNDays(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -n); // 得到前一个月
		return calendar.getTime();
	}

	/**
	 * 获取当前时间前N个天的年月日
	 * 
	 * @return
	 */
	public static Date getDateBeforeNDays(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -n);
		return calendar.getTime();
	}

	/**
	 * 获取date所在月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int maxday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, maxday);
		return calendar.getTime();

	}

	/**
	 * 获取date所在月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int minday = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, minday);
		return calendar.getTime();

	}

	/**
	 * 获取当前日期的前N周的日期
	 */
	public static Date getDateBeforeNWeeks(int n) {
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -7 * n);
		// calendar.add(Calendar.MONTH, -n); //得到前一个月
		return calendar.getTime();
	}

	/**
	 * 获取date所在月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		calendar.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = calendar.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
		calendar.add(Calendar.DATE, 6);
		return calendar.getTime();

	}

	/**
	 * 获取date所在月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		calendar.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = calendar.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
		return calendar.getTime();
	}

	public static String timestampToDate(String time) {
		Timestamp ts = new Timestamp(Long.parseLong(time));
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(ts);
	}


	public static String timesToStr(String time) {
		String n = time.substring(0, 4);
		String y = time.substring(4, 6);
		String t = time.substring(6, 8);
		String f = time.substring(8, 10);
		String mm = time.substring(10, 12);
		StringBuffer sb = new StringBuffer();
		sb.append(n);
		sb.append("-");
		sb.append(y);
		sb.append("-");
		sb.append(t);
		sb.append(" ");
		sb.append(f);
		sb.append(":");
		sb.append(mm);
		sb.append("---");
		sb.append(time);
		return sb.toString();
	}


	public static String dateToStrLong(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 返回String格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 23:41:31
	}

	public static String getDateToString(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String dateStr = format.format(date);
		return dateStr;
	}

	public static String getYMDHMS(long date) {
		try {
			String stringTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date));
			return stringTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static long formatTime2Long(String stringTime, String formatStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			Date date = new Date();
			date = format.parse(stringTime);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String formateDate2String(Date date, String formatStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			String dateStr = format.format(date);
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String formateNumber2String(Long time, String formatStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			String dateStr = format.format(new Date(time * 1000));
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String addDateBeginStr(String date) {
		return date + " 00:00:00";
	}

	public static String addDateEndStr(String date) {
		return date + " 23:59:59";
	}

	public static Long getMonthStartTime(String timeStr, String formatStr) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = format.parse(timeStr);
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.add(Calendar.MONTH, 0);
		time.set(Calendar.DAY_OF_MONTH, 1);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		return (Long) (time.getTimeInMillis() / 1000L);
	}

	public static Long getMonthEndTime(String timeStr, String formatStr) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = format.parse(timeStr);
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.add(Calendar.MONTH, 1);
		time.set(Calendar.DAY_OF_MONTH, 0);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		return (Long) (time.getTimeInMillis() / 1000L);
	}

	public static Long getYearStartTime(String timeStr, String formatStr) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = format.parse(timeStr);
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.set(Calendar.MONTH, 1);
		time.set(Calendar.DAY_OF_MONTH, 1);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		return (Long) (time.getTimeInMillis() / 1000L);
	}

	public static Long getEndStartTime(String timeStr, String formatStr) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = format.parse(timeStr);
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.add(Calendar.YEAR, 1);
		time.set(Calendar.MONTH, 0);
		time.set(Calendar.DAY_OF_MONTH, 0);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		return (Long) (time.getTimeInMillis() / 1000L);
	}

	/**
	 * 从当前时间加多少分钟
	 */
	public static String getCurrentStr(String formatStr, int number) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, number);// 添加的天数
		// System.out.println(sdf.format(nowTime.getTime()));
		return sdf.format(nowTime.getTime());
	}

	/**
	 * 从当前时间加多少分钟
	 */
	public static Date getCurrentDate(String formatStr, int number) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, number);// 添加的天数
		// System.out.println(sdf.format(nowTime.getTime()));
		Date date = null;
		try {
			date = sdf.parse(sdf.format(nowTime.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}







}
