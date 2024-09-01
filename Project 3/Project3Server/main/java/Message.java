import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    static final long serialVersionUID = 42L;
    public enum MessageType {
        DIRECT_MESSAGE, ALL_CLIENTS_MESSAGE, GROUP_MESSAGE, SYSTEM_MESSAGE, CREATE_USER, CREATE_GROUP, USER_JOINED, USER_LEFT, REQUEST_CLIENT, REQUEST_GROUP
    }
    private MessageType type;
    private String sender;
    private String recipient; // direct messages or group IDs
    private String content;
    private List<String> currentClients;

    // messages to all users
    // direct messages
    // Requesting users
    public Message(MessageType type, String sender, String recipient, String content) {
        this.type = type;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }
    // Constructor for sending info of all active clients
    public Message(MessageType type, String sender, String recipient, String content, List<String> clients) {
        this(type, sender, recipient, content);
        currentClients = clients;
    }
    // Constructor for creating new userID, user joined and user left
    public Message(MessageType type, String userId) {
        this.type = type;
        this.sender = userId;
    }
    public List<String> getActiveClients() {
        return currentClients;
    }
    public MessageType getType(){
        return type;
    }
    public String getSender(){
        return sender;
    }
    public String getRecipient(){
        return recipient;
    }
    public String getContent(){
        return content;
    }
}