import com.misterspex.*;
import com.misterspex.model.ScheduledTask;
import com.misterspex.model.TimeRange;
import com.misterspex.util.StringToTimeConverter;
import com.misterspex.validator.TimeInputValidator;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class TaskExecutionAllowerTest {

    @Test
    public void testTaskExecutionAllowerObject(){

        TaskExecutionAllower taskExecutionAllower = new TaskExecutionAllower();
        assertNotNull(taskExecutionAllower);

        List<TimeRange> timeRanges = new ArrayList<TimeRange>();
        TimeRange timeRange = new TimeRange();
        assertNotNull(timeRange);
        timeRanges.add(timeRange);

        ScheduledTask scheduledTask = new ScheduledTask();
        assertNotNull(scheduledTask);

        TaskExecutionAllower taskExecutionAllower1 = new TaskExecutionAllower(timeRanges, scheduledTask);
        assertNotNull(taskExecutionAllower1);
        assertNotNull(taskExecutionAllower1.getScheduledTask());
        assertNotNull(taskExecutionAllower1.getTimeRanges());

    }

    @Test
    public void testStringToTimeConverter() throws ParseException {

        List<TimeRange> timeRanges = StringToTimeConverter.getTimeRanges("08:00:00-08:30:00");
        assertNotNull(timeRanges);

        Date time = StringToTimeConverter.getTime("08:00:00");
        assertNotNull(time);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String dateInString = "08:00:00";
        Date actualTime = sdf.parse(dateInString);
        assertEquals(time, actualTime);

        TimeRange timeRange1 = new TimeRange();
        String dateInString1 = "08:30:00";
        Date actualTime1 = sdf.parse(dateInString1);
        timeRange1.setStartTime(actualTime);
        timeRange1.setEndTime(actualTime1);
        List<TimeRange> timeRanges1 = new ArrayList<TimeRange>();
        timeRanges1.add(timeRange1);
        assertEquals(timeRanges, timeRanges1);

        TimeRange timeRange2 = new TimeRange();
        String dateInString2 = "12:00:00";
        Date actualTime2 = sdf.parse(dateInString2);
        String dateInString3 = "13:00:00";
        Date actualTime3 = sdf.parse(dateInString3);
        timeRange2.setStartTime(actualTime2);
        timeRange2.setEndTime(actualTime3);
        timeRanges1.add(timeRange2);
        assertEquals(timeRanges1, StringToTimeConverter.getTimeRanges("08:00:00-08:30:00;12:00:00-13:00:00"));

    }

    @Test
    public void testTaskExecution() throws ParseException {

        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.setTaskTime(StringToTimeConverter.getTime("12:30:00"));
        List<TimeRange> timeRanges = StringToTimeConverter.getTimeRanges("08:00:00-08:30:00;12:00:00-13:00:00");
        TaskExecutionAllower taskExecutionAllower = new TaskExecutionAllower(timeRanges, scheduledTask);
        assertTrue(taskExecutionAllower.taskExecutes());

        ScheduledTask scheduledTask1 = new ScheduledTask();
        scheduledTask1.setTaskTime(StringToTimeConverter.getTime("08:00:00"));
        List<TimeRange> timeRanges1 = StringToTimeConverter.getTimeRanges("08:00:00-08:30:00;12:00:00-13:00:00");
        TaskExecutionAllower taskExecutionAllower1 = new TaskExecutionAllower(timeRanges1, scheduledTask1);
        assertTrue(taskExecutionAllower1.taskExecutes());

        ScheduledTask scheduledTask2 = new ScheduledTask();
        scheduledTask2.setTaskTime(StringToTimeConverter.getTime("13:00:00"));
        List<TimeRange> timeRanges2 = StringToTimeConverter.getTimeRanges("08:00:00-08:30:00;12:00:00-13:00:00");
        TaskExecutionAllower taskExecutionAllower2 = new TaskExecutionAllower(timeRanges2, scheduledTask2);
        assertTrue(taskExecutionAllower2.taskExecutes());

        ScheduledTask scheduledTask3 = new ScheduledTask();
        scheduledTask3.setTaskTime(StringToTimeConverter.getTime("15:00:00"));
        List<TimeRange> timeRanges3 = StringToTimeConverter.getTimeRanges("08:00:00-08:30:00;12:00:00-13:00:00");
        TaskExecutionAllower taskExecutionAllower3 = new TaskExecutionAllower(timeRanges3, scheduledTask3);
        assertFalse(taskExecutionAllower3.taskExecutes());
    }

    @Test
    public void testTimeInputValidator() throws ParseException {

        TimeInputValidator validator = new TimeInputValidator();

        validator.validateTime("04:00:00");
        assertTrue(validator.getValid());

        validator.validateTime("123");
        assertFalse(validator.getValid());

        validator.validateTime("04:00:1234");
        assertFalse(validator.getValid());

        validator.validateTime("15:33:55");
        assertTrue(validator.getValid());

        validator.validateTime("26:00:55");
        assertFalse(validator.getValid());

        validator.validateTime("23:00:60");
        assertFalse(validator.getValid());

        TimeInputValidator validator1 = new TimeInputValidator();
        validator1.validateTimeRange("08:00:00-08:30:00;12:00:00-13:00:00");
        assertTrue(validator1.getValid());

        validator1.validateTimeRange("08:00:00-08:30:80;12:00:00-13:00:00");
        assertFalse(validator1.getValid());

        validator1.validateTimeRange("08:00:00-08:30:80-08:00:00;12:00:00-13:00:00");
        assertFalse(validator1.getValid());

        validator1.validateTimeRange("123");
        assertFalse(validator1.getValid());

        validator1.validateTimeRange("08:00:00-08:30:00;12:00:00-13:00:00;19:00:00-22:30:00");
        assertTrue(validator1.getValid());

        validator1.validateTimeRange("9:00:00:00-08:30:00");
        assertFalse(validator1.getValid());

        validator1.validateTimeRange("9:00:00:00-08:30:00;12:00:00-13:00:00");
        assertFalse(validator1.getValid());


    }
}
