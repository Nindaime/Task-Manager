package project.Message;

public class Content {
    private String timesent;
    private String message;
    private Boolean isRead;

    public Content(String message, String timesent, Boolean isRead) {
        this.timesent = timesent;
        this.isRead = isRead;
        this.message = message;
    }

    public String getMessage() {

        return this.message;
    }

    public String getTimesent() {
        return this.timesent;
    }

    public Boolean getIsMessageRead() {
        return this.isRead;
    }

}
