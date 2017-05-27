package cn.tom.web.util.mq.util;

public class RabbitQueue {
    private String queue_key;
    private String queue_name;
    private String available;

    public void setQueue_key(String queue_key) {
        this.queue_key = queue_key;
    }

    public String getQueue_key() {
        return queue_key;
    }

    public void setQueue_name(String queue_name) {
        this.queue_name = queue_name;
    }

    public String getQueue_name() {
        return queue_name;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getAvailable() {
        return available;
    }
}
