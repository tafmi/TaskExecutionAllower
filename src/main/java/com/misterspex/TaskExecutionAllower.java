package com.misterspex;

import com.misterspex.model.ScheduledTask;
import com.misterspex.model.TimeRange;

import java.util.List;

public class TaskExecutionAllower {

    private List<TimeRange> timeRanges;
    private ScheduledTask scheduledTask;

    public TaskExecutionAllower(){};

    public TaskExecutionAllower(List<TimeRange> timeRanges, ScheduledTask scheduledTask){
        this.timeRanges = timeRanges;
        this.scheduledTask = scheduledTask;
    }

    public boolean taskExecutes(){

        boolean taskExecutes = false;

        for(TimeRange timeRange: timeRanges){
            if(scheduledTask.getTaskTime().equals(timeRange.getStartTime()) ||
                    scheduledTask.getTaskTime().equals(timeRange.getEndTime()) ||
                    (scheduledTask.getTaskTime().after(timeRange.getStartTime()) &&
                    scheduledTask.getTaskTime().before(timeRange.getEndTime()))){
                taskExecutes = true;
                break;
            }
        }

        return taskExecutes;
    }

    public List<TimeRange> getTimeRanges() {
        return timeRanges;
    }

    public ScheduledTask getScheduledTask() {
        return scheduledTask;
    }

}
