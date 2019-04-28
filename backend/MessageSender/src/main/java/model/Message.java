package model;

public class Message {

    private String name;
    private String content;
    private String startTime;

    public Message(String name, String content, String startTime) {
        this.name = name;
        this.content = content;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
