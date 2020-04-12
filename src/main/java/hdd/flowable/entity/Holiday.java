package hdd.flowable.entity;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

@Repository
public class Holiday implements Serializable {
    private static final long serialVersionUID = -8213592458420611617L;

    private String id;
    //请假原因
    private String reason;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //请假用户ID
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "id='" + id + '\'' +
                ", reason='" + reason + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", userId='" + userId + '\'' +
                '}';
    }

    public Holiday() {
    }

    public Holiday(String reason, Date startTime, Date endTime, String userId) {
        this.reason = reason;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
    }
}
