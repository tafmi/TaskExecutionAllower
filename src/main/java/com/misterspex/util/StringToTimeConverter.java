package com.misterspex.util;

import com.misterspex.model.TimeRange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public  class StringToTimeConverter {

    public static List<TimeRange> getTimeRanges(String timeRangesString){

        List<TimeRange> timeRanges = new ArrayList<TimeRange>();

        String[] rangesArray = timeRangesString.split(";");

        for(String range: rangesArray){

            TimeRange timeRange = new TimeRange();

            String[] times = range.split("-");
            Date startTime = getTime(times[0]);
            Date endTime = getTime(times[1]);

            timeRange.setStartTime(startTime);
            timeRange.setEndTime(endTime);

            timeRanges.add(timeRange);
        }

        return timeRanges;
    }

    public static Date getTime(String timeString){

        Date time = new Date();

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String dateInString = timeString;
            time = sdf.parse(dateInString);
        }catch (ParseException pe){
            pe.printStackTrace();
        }

        return time;
    }

}
