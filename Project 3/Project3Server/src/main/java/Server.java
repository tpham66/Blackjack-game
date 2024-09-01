import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

	int count = 1;
	final ConcurrentHashMap<Integer, ClientThread> clients = new ConcurrentHashMap<>();
	final ConcurrentHashMap<String, Integer> clientNames = new ConcurrentHashMap<>();
	//final ConcurrentHashMap<String, Con>
	TheServer server;
	private Consumer<Serializable> callback;


	Server(Consumer<Serializable> call){

		callback = call;
		server = new TheServer();
		server.start();
	}


	public class TheServer extends Thread{

		public void run() {

			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");


		    while(true) {
				Socket socket = mysocket.accept();
				ClientThread c;
				c = new ClientThread(socket, count);
				clients.put(count, c);
				callback.accept("client has connected to server: " + "client #" + count);
				c.start();
				count++;
			}
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}


		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			String userName;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}

			private void checkNewUsername(Message message) {
					if (clientNames.containsKey(message.getSender())) {
						sendToAClient(new Message(Message.MessageType.CREATE_USER, "Server", count + "", "Existing name"));
					} else {
						this.userName = message.getSender();
						clientNames.put(userName, count); // add userName and their count to the hashmap
						callback.accept("client #" + count + ": " + userName + " joined server");
						sendToAClient(new Message(Message.MessageType.CREATE_USER, "Server", userName, "New account"));
						updateClients(new Message(Message.MessageType.USER_JOINED, userName));
					}
			}

			// This function is done
			// send message to all clients
			private void updateClients(Message message) {
				 clients.values().forEach(clientThread -> {
					 synchronized (clientThread.out) {
						 try {
							 clientThread.out.writeObject(message);
							 clientThread.out.flush();
						 } catch (Exception e) {
							 e.printStackTrace();
						 }
					 }
				 });
			}

			// send message to a client, i.e. notify users when they enter username, direct message to a user, message to a group
			private void sendToAClient(Message message) {
				ClientThread r = clients.get(clientNames.get(message.getRecipient()));
				if (r != null) {
					synchronized (r.out) {
						try {
							r.out.writeObject(message);
							r.out.flush();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			// send info of all active clients to the requesting client
			private void requestInfo(Message message) {
				if (message.getType() == Message.MessageType.REQUEST_CLIENT) {
					if (message.getContent().equals("current clients in server")) {
						synchronized (clients) {
							List<String> currentClients = new ArrayList<>(clientNames.keySet());
							sendToAClient(new Message(Message.MessageType.REQUEST_CLIENT, "Server", userName, "All clients", currentClients));
							callback.accept("client #" + count + ": " + userName + " requests to see list of current clients in the server");
						}
					}
				}
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}

				 while(true) {
					    try {
					    	Message data = (Message) in.readObject();
							// handle messages based on types
							switch (data.getType()) {
								case CREATE_USER:
									checkNewUsername(data);
									break;
								case DIRECT_MESSAGE: // handle direct message
									callback.accept("client #" + count + ": " + userName + " to client #" + clientNames.get(data.getRecipient()) + ": " + data.getRecipient() + " sent: " + data.getContent());
									sendToAClient(data);
									break;
                                case REQUEST_CLIENT: // send list of active clients back to requesting client
                                    requestInfo(data);
									break;
								case ALL_CLIENTS_MESSAGE: // send message to all users in the server
									callback.accept("client #" + count + " " + userName + " sent to all clients: " + data.getContent());
									updateClients(data);
									break;
								default:
									break;
							}
						}
					    catch(Exception e) {
							callback.accept("client #" + count + (userName == null ? "" : " " + userName) + " left server");
							updateClients(new Message(Message.MessageType.USER_LEFT, userName));
							if (clientNames.containsValue(count)) {
								clientNames.remove(userName, count);
							}
							clients.remove(count, this);
							break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}


	
	

	
