package com.misterspex.validator;

import com.misterspex.util.StringToTimeConverter;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeInputValidator {

    private Boolean valid;
    private String message;
    private static final String PATTERN =
            "^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$";

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void validateTimeRange(String timeRangeStr){
        if(timeRangeStr == null){
            setNotValidTimeRange();
            return;
        }
        String[] timeRanges = timeRangeStr.split(";");
        for(String timeRange: timeRanges){

            String[] times = timeRange.split("-");

            if(times.length != 2){
                this.setNotValidTimeRange();
                return;
            }

            for(String time: times){
                this.validateTime(time);
                if(this.valid == false){
                    this.setNotValidTimeRange();
                    return;
                }
            }

            if(!this.startBeforeEndTime(times)){
                this.setNotValidTimeRange();
                return;
            }
        }

    }

    private boolean startBeforeEndTime(String[] times){
        Date startTime = StringToTimeConverter.getTime(times[0]);
        Date endTime =  StringToTimeConverter.getTime(times[1]);
        if(startTime.before(endTime)) return true;
        return false;
    }

    public void validateTime(String time){
        if(time == null){
            setNotValidTime();
            return;
        }
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(time);
        if(matcher.matches()){
            valid = true;
        }else{
            this.setNotValidTime();
        }
    }

    private void setNotValidTime(){
        this.message = "Please enter a valid time(hh:mm:ss)";
        this.valid = false;
    }

    private void setNotValidTimeRange(){
        this.message = "Please enter a valid time range(hh:mm:ss-hh:mm:ss;hh:mm:ss-hh:mm:ss)";
        this.valid = false;
    }

}
