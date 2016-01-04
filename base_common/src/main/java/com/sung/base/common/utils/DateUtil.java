package com.sung.base.common.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	
	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-mm-dd hh24:mi:ss";
	
	public static String dataFormatWhole(Date date) {
		return formatw.format(date);
	}

	public static String formatDate(Date date, int style) {
		if (date == null) {
			return "";
		}
		switch (style) {
		case 5:
			return formatmm.format(date);
		case 4:
			return formats.format(date);
		case 3:
			return formatm.format(date);
		case 2:
			return format.format(date);
		default:
			return formatw.format(date);
		}
	}

	public static Date getNowTimeDate() {
		return DateUtil.getNowTime(DateUtil.getTime(5), "yyyy-MM-dd HH:mm:ss");
	}

	public static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat formatw = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat formatmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final DateFormat formatm = new SimpleDateFormat("MM-dd HH:mm");
	public static final DateFormat formats = new SimpleDateFormat("MM-dd");
	public static FilenameFilter DIR_FILE_FILTER = new FilenameFilter() {

		public boolean accept(File dir, String name) {
			if (dir.isDirectory()) {
				return true;
			} else {
				return false;
			}
		}
	};

	public static String DateToStr(Date date, String strFormat) {

		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		String str = format.format(date);
		return str;
	}

	public static Date strToDate(String str, String strFormat) {
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			LogUtils.logError("", e.getMessage());
		}
		return date;
	}

	public static Date getNowTime(String strDate, String strFormat) {
		DateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = format.parse(strDate);
		} catch (Exception e) {
		}
		return date;
	}

	public static String getTime(int type) {
		String t = null;
		SimpleDateFormat format = null;
		Calendar cal = Calendar.getInstance();
		switch (type) {
		case 0:
			format = new SimpleDateFormat("yyyyMMddHHmmss");
			break;
		case 1:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 2:
			format = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
			break;
		case 3:
			format = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
			break;
		case 4:
			format = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 5:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 6:
			format = new SimpleDateFormat("yyyyMMdd");
			break;
		case 7:
			format = new SimpleDateFormat("yyyy-MM");
			break;
		case 8:
			format = new SimpleDateFormat("HH:mm:ss");
			break;
		case 9:
			format = new SimpleDateFormat("yyyy");
			break;
		case 10:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			break;
		default:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		}
		t = format.format(cal.getTime());
		return t;
	}

	public static long getLongTime() {
		return System.currentTimeMillis();
	}

	public static long compareDate(Date iDate, String format) {
		long distime = 0;
		if (iDate != null) {
			try {
				distime = DateUtil.getLongTime()
						- DateFormat.getDateTimeInstance()
								.parse(DateUtil.formatDateTime(iDate, format))
								.getTime();
			} catch (ParseException e) {
				LogUtils.logError("", e.getMessage());
			}
			distime = distime / 1000 / 60 / 60 / 24;
		}
		return distime;
	}

	public static boolean isDateBefore(Date date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(DateUtil.formatDateTime(date2,
					"yyyy-MM-dd HH:mm:ss")));
		} catch (ParseException e) {
			return false;
		}
	}

	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	public static Date strToDate(String dateStr) {
		Date dateTemp = null;
		try {
			dateStr = dateStr.substring(0, 10);
			StringTokenizer token = new StringTokenizer(dateStr, "-");
			int year = Integer.parseInt(token.nextToken());
			int month = Integer.parseInt(token.nextToken()) - 1;
			int day = Integer.parseInt(token.nextToken());
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, day);
			dateTemp = cal.getTime();
		} catch (Exception e) {
			LogUtils.logError("", e.getMessage());
		}
		return dateTemp;
	}

	public static String formatDateTime(Date date, String format) {
		SimpleDateFormat outFormat = new SimpleDateFormat(format);
		return outFormat.format(date);
	}

	public static long getTwoDateDays(Date beginDate, Date endDate) {
		long beginTime = beginDate.getTime();
		long endTime = endDate.getTime();
		long betweenDays = (long) ((endTime - beginTime)
				/ (1000 * 60 * 60 * 24) + 0.5);
		return betweenDays;
	}

	/**
	 * 毫秒日期转换到指定格式
	 * 
	 * @param time
	 *            日期
	 * @param format
	 *            转换格式
	 * @return
	 * @throws Exception
	 */
	public static String secondToStr(long time, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}

	/**
	 * 将long型的时间戳转换为字符串
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String longToString(long timestamp, int style) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		return formatDate(calendar.getTime(), style);
	}

	/**
	 * @描述:根据两个时间戳 和 指定周期，计算是否在该周期之间
	 * @参数名： @param currentTime 当前时间戳
	 * @参数名： @param createTime 比较的时间
	 * @参数名： @param period 周期
	 * @参数名： @return
	 * @返回类型 boolean
	 * @throws
	 */
	public static boolean checkTimeForPeriod(Long currentTime, Long createTime,
			Integer period) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = format.format(new Date(currentTime));
		String createDate = format.format(new Date(createTime));
		long day = (format.parse(currentDate).getTime() - format.parse(createDate).getTime()) / (24*60*60*1000);
		if (day > period) {
			return false;
		}
		//当前时间 减去 比较的时间
		return true;
	}
	
	/**
	 * 根据时间间隔，得到两个时间内的间隔时间点
	* @描述:(这里用一句话描述这个方法的作用) 
	* @参数名： @param startTime
	* @参数名： @param endTime
	* @参数名： @param interval
	* @参数名： @param dateTimeStringList
	* @参数名： @param dateTimeLongList    
	* @返回类型 void   
	* @throws
	 */
	public static void getTwoTimeListByInterval(long startTime, long endTime, long interval, List<String> dateTimeStringList, List<Long> dateTimeLongList){
		//校验
		if(endTime <= startTime || interval >= startTime){
			return;
		}
		if(dateTimeStringList == null){
			dateTimeStringList = new ArrayList<String>();
		}
		if(dateTimeLongList == null){
			dateTimeLongList = new ArrayList<Long>();
		}
		//遍历数据
		boolean isContinue = true;
		while(isContinue){
			dateTimeLongList.add(startTime);
			dateTimeStringList.add(DateUtil.longToString(startTime, 2));
			
			if(startTime + interval >= endTime){
				dateTimeLongList.add(endTime);
				dateTimeStringList.add(DateUtil.longToString(endTime, 2));
				isContinue = false;
			}
			startTime += interval;
		}
	}
	
	public static long getTimeMillisToAfterDaysHour( int days, int hourOfTomorrow ) throws Exception {

		if ( 0 == hourOfTomorrow )
			hourOfTomorrow = 2;

		Calendar calendar = Calendar.getInstance();

		int yearOfToday = calendar.get( Calendar.YEAR );
		int monthOfToday = calendar.get( Calendar.MONTH ) + 1;
		int dayOfToday = calendar.get( Calendar.DAY_OF_MONTH );

		calendar.set( Calendar.DAY_OF_MONTH, dayOfToday + days );
		if ( 31 == dayOfToday && days >=1 ) {
			calendar.set( Calendar.MONTH, monthOfToday + 1 );
		}
		if ( 12 == monthOfToday && 31 == dayOfToday && days>=1 ) {
			calendar.set( Calendar.YEAR, yearOfToday + 1 );
		}

		int dayOfTomorrow  = calendar.get( Calendar.DAY_OF_MONTH );
		int monthOfTomorrow  = calendar.get( Calendar.MONTH );
		int yearOfTomorrow = calendar.get( Calendar.YEAR );

		Calendar calendarOfTomorrow = new GregorianCalendar( yearOfTomorrow, monthOfTomorrow, dayOfTomorrow, hourOfTomorrow, 0, 0 );
		long startTimeMillis = System.currentTimeMillis();
		
		
		long timeMillisToAfterDaysHour = calendarOfTomorrow.getTime().getTime() - startTimeMillis; 
		
		if( 0 > timeMillisToAfterDaysHour )
			throw new Exception( "时间差为负数，可能设置有误" );
		
		return timeMillisToAfterDaysHour;

	}
	
	/**
	 * 获取某天前一天的开始时间
	 * @param time
	 * @return
	 */
	public static Date getYestDayStartTime(Date time){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(time);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 05);
		return calendar.getTime();
	}
	/**
	 * 获取某天前一天的结束时间
	 * @param time
	 * @return
	 */
	public static Date getYestDayEndTime(Date time){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(time);
		calendar.add(Calendar.DATE, 0);
		calendar.set(Calendar.HOUR_OF_DAY,00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 05);
		return calendar.getTime();
	}
	
	/**
	 * 时间戳转Date
	 * @return
	 */
	public static Date timestampToDate(Long longTime){
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date date = null;
		String d = format.format(longTime);
		try {
			date = format.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static String getyyyyMMddHHmmss(Date time){
		return formatw.format(time);
	}
	
	public static String getyyyyMMdd(Date time){
		return format.format(time);
	}
	
	public static void main(String[] args) throws ParseException {
	 
		try {
			//System.out.println(DateUtil.getLongTime());
		} catch (Exception e) {
			LogUtils.logError("", e.getMessage());
		}
		
		//System.out.println(DateUtil.getyyyyMMddHHmmss(getYestDayStartTime(new Date())));
		//System.out.println(DateUtil.getyyyyMMddHHmmss(getYestDayEndTime(new Date())));
//		System.out.println(DateUtil.strToDate("2014-11-12 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime());
//		
//		List<String> dateTimeStringList = new ArrayList<>();
//		List<Long> dateTimeLongList = new ArrayList<>();
//		
//		DateUtil.getTwoTimeListByInterval(1415721600000l, new Date().getTime(), 1000 * 60 * 60 * 24, dateTimeStringList, dateTimeLongList);
//		
//		System.out.println(dateTimeStringList);
//		System.out.println(dateTimeLongList);
//		System.out.println(formatw.format(new Date()));
//		System.out.println(format.format(new Date()));
//		System.out.println(formatm.format(new Date()));
//		System.out.println(formats.format(new Date()));
//		
		System.out.println(formatw.format(getYestDayStartTime(new Date())));
		System.out.println(formatw.format(getYestDayEndTime(new Date())));
	}
}
