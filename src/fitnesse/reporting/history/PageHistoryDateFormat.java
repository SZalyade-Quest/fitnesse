package fitnesse.reporting.history;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.ParseException;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class PageHistoryDateFormat extends DateFormat  {

  public static final String TEST_FILE_FORMAT = "\\A\\d{14}(?:\\d{3})?_\\d+_\\d+_\\d+_\\d+(.xml)*\\Z";

  static final String TEST_RESULT_FILE_DATE_PATTERN = "yyyyMMddHHmmss";
  public static final String TEST_RESULT_FILE_DATE_FORMAT_NEW = "yyyyMMddHHmmssSSS";

  private static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZ";
  private static final String ISO_DATETIME_MILLIS_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";

  final SimpleDateFormat sdf1 = new SimpleDateFormat(TEST_RESULT_FILE_DATE_PATTERN);
  final SimpleDateFormat sdf2 = new SimpleDateFormat(TEST_RESULT_FILE_DATE_FORMAT_NEW);


  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo,
                             FieldPosition pos)
  {
    return sdf2.format(date, toAppendTo, pos);
  }

  @Override
  public Date parse(String source, ParsePosition pos) {
    if (source.length() - pos.getIndex() == TEST_RESULT_FILE_DATE_PATTERN.length())
      return sdf1.parse(source, pos);
    return sdf2.parse(source, pos);
  }


  public String formatISO8601(Date date){
    return DateFormatUtils.format(date, ISO_DATETIME_MILLIS_FORMAT, TimeZone.getDefault(), Locale.US);
  }

  public Date parseISO8601(String dateString) throws ParseException {
    return DateUtils.parseDateStrictly(dateString, new String[]{ ISO_DATETIME_MILLIS_FORMAT, ISO_DATE_FORMAT });
  }

  public static boolean matchesFileFormat(String pageHistoryFileName) {
    return pageHistoryFileName.matches(TEST_FILE_FORMAT);
  }

}
