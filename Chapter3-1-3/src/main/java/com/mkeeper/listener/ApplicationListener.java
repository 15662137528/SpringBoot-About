package com.mkeeper.listener;

import com.mkeeper.exception.ServiceException;
import com.mkeeper.model.ScheduleJob;
import com.mkeeper.service.JobService;
import com.mkeeper.utils.ScheduleUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 *     启动应用时运行定时任务
 *
 * @author mkeeper
 * @create 2018/10/19 10:05
 */
@Slf4j
@Component
public class ApplicationListener implements CommandLineRunner {

    @Resource
    private JobService jobService;

    @Resource
    private Scheduler scheduler;

    @Override
    public void run(String... args) {
        List<ScheduleJob> scheduleJobList = jobService.getAllEnableJob();
        for (ScheduleJob scheduleJob : scheduleJobList) {
            try {
                CronTrigger cronTrigger = ScheduleUtil.getCronTrigger(scheduler, scheduleJob);
                if (cronTrigger == null) {
                    ScheduleUtil.createScheduleJob(scheduler, scheduleJob);
                } else {
                    ScheduleUtil.updateScheduleJob(scheduler, scheduleJob);
                }
                log.info("Startup {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
            } catch (ServiceException e) {
                log.error("Job ERROR", e);
            }
        }
    }
}