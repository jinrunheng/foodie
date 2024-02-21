import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2024/2/22 上午12:08
 * @Version 1.0
 */
public class DateUtilsTest {

    @Test
    public void testDateUtils() {
        Date date = null;
        try {
            date = DateUtils.parseDate("1900-01-01", "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        System.out.println(new Date());
    }
}
