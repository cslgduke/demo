package com.example.demo.config;

import com.example.demo.job.DemoQuartzJob;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import static com.alibaba.compileflow.engine.process.preruntime.generator.bean.SpringApplicationContextProvider.applicationContext;

/**
 * @author i565244
 */

@Configuration
public class QuartzConfig {


    @Bean
    public JobFactory jobFactory() {
        return new CustomizeSpringBeanJobFactory();
    }


    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean,JobFactory jobFactory) throws SchedulerException, ClassNotFoundException {
        schedulerFactoryBean.setJobFactory(jobFactory);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.setJobFactory(jobFactory);

        Class<? extends Job> clazz = DemoQuartzJob.class;
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity("DemoQuartzJob", "demo-job").build();
        jobDetail.getJobDataMap().put("applicationContextKey", applicationContext);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("DemoQuartzJob" + "_trigger",
                "demo-job" + "_trigger_group").withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        return scheduler;
    }


}
