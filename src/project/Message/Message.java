package project.Message;

public class Message {

    private String sender;
    private String receiver;
    private String timesent;
    private String message;
    private Boolean isRead;

    public Message(People people, Content content) {
        this.sender = people.getSender();
        this.receiver = people.getReceiver();
        this.timesent = content.getTimesent();
        this.isRead = content.getIsMessageRead();
        this.message = content.getMessage();
    }

    public String getMessage() {

        return this.sender + " : " + this.message;
    }

    public String getSender() {
        return this.sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public String getTimesent() {
        return this.timesent;
    }

    public Boolean getIsMessageRead() {
        return this.isRead;
    }
}
