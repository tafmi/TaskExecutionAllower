package com.misterspex;

import com.misterspex.model.ScheduledTask;
import com.misterspex.util.StringToTimeConverter;
import com.misterspex.validator.TimeInputValidator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        boolean validInput = false;

        while(!validInput){

            System.out.println("Please type time ranges and press enter.");
            Scanner scanner = new Scanner(System.in);
            String timeRanges = scanner.nextLine();

            System.out.println("Please type time of task and press enter.");
            Scanner scanner1 = new Scanner(System.in);
            String timeTask = scanner1.nextLine();

            TimeInputValidator timeRangeValidator = new TimeInputValidator();
            timeRangeValidator.validateTimeRange(timeRanges);

            TimeInputValidator timeValidator = new TimeInputValidator();
            timeValidator.validateTime(timeTask);

            if(!timeRangeValidator.getValid()){
                System.out.println(timeRangeValidator.getMessage());
                continue;
            }

            if(!timeValidator.getValid()){
                System.out.println(timeValidator.getMessage());
                continue;
            }

            validInput = true;

            ScheduledTask scheduledTask = new ScheduledTask();
            scheduledTask.setTaskTime(StringToTimeConverter.getTime(timeTask));

            TaskExecutionAllower taskExecutionAllower = new TaskExecutionAllower(StringToTimeConverter.getTimeRanges(timeRanges), scheduledTask);

            System.out.println(taskExecutionAllower.taskExecutes());

        }
    }
}
