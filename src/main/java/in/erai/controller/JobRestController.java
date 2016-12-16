package in.erai.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.erai.batch.jobs.BatchConfiguration;
import in.erai.domain.Message;

@RestController
public class JobRestController {
    @Autowired
    private ApplicationContext appContext;

    @RequestMapping("/")
    public String welcome() {//Welcome page, non-rest
        return "Welcome to RestTemplate Example.";
    }

    @RequestMapping("/jobs/{jobName}")
    public Message message(@PathVariable String jobName) {//REST Endpoint.
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext();
        ctx.register(BatchConfiguration.class);
        ctx.refresh();
        JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
        //Job job = (Job) ctx.getBean("importUserJob");

        Job job = (Job) ctx.getBean(jobName);
        JobParameter jobParameter = new JobParameter(System.currentTimeMillis());
        Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
        jobParametersMap.put("time", jobParameter);
        Message msg = null;

        try {

            JobExecution execution = jobLauncher.run(job,new JobParameters(jobParametersMap));
            System.out.println("Exit Status : " + execution.getStatus());
            System.out.println("Job Id : " + execution.getJobId());
            msg = new Message(execution.getJobId().toString(), execution.getStatus().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done");

        return msg;

    }
}