import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback;
	private String userName;

	Client(Consumer<Serializable> call){
	
		callback = call;
	}
	
	public void run() {
		
		try {
		socketClient= new Socket("127.0.0.1",5555);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {
			callback.accept("Connection failed: " + e.getMessage());
		}
		
		while(true) {
			 
			try {
				Message message = (Message) in.readObject();
				callback.accept(message);
			}
			catch(Exception e) {
				callback.accept("Error reading from server: " + e.getMessage());
				break;
			}
		}
	
    }
	
	public void send(Message data) {
		
		try {
			if (socketClient != null && !socketClient.isClosed()) {
				out.writeObject(data);
			} else {
				callback.accept("Connection is closed. Cannot send message.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			callback.accept("Error sending message: " + e.getMessage());
		}
	}

	// Set username
	public void setUsername(Message message) {
		this.userName = message.getRecipient();
	}
	public String getUserName() {
		return userName;
	}

	// Send a message to an individual user
	public void directMessage(String recipient, String content) {
		send(new Message(Message.MessageType.DIRECT_MESSAGE, userName, recipient, content));
	}

	// Send a message to all users in the server
	public void sendMessageToAll(String content) {
		send(new Message(Message.MessageType.ALL_CLIENTS_MESSAGE, userName, "All users", content));
	}

	// Request information of current clients int the server
	// When client wants to see all current clients int the server
	public void requestClient(String clientName) {
		send(new Message(Message.MessageType.REQUEST_CLIENT, userName, "Server", clientName));
	}

}
