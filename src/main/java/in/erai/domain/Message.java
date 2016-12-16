package in.erai.domain;

public class Message {

    String jobId;
    String jobExecutionStatus;
    public Message(String jobId, String jobExecutionStatus) {
        this.jobId = jobId;
        this.jobExecutionStatus = jobExecutionStatus;
    }

    public String getjobId() {
        return jobId;
    }

    public String getjobExecutionStatus() {
        return jobExecutionStatus;
    }

}