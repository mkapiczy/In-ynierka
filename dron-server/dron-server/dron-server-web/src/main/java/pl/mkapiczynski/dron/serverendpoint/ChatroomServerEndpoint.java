package pl.mkapiczynski.dron.serverendpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import pl.mkapiczynski.dron.decoder.MessageDecoder;
import pl.mkapiczynski.dron.encoder.MessageEncoder;
import pl.mkapiczynski.dron.message.ChatMessage;
import pl.mkapiczynski.dron.message.GeoDataMessage;
import pl.mkapiczynski.dron.message.Message;
import pl.mkapiczynski.dron.message.UsersMessage;

@javax.websocket.server.ServerEndpoint(value = "/chatroom", encoders = { MessageEncoder.class }, decoders = {
		MessageDecoder.class, })
public class ChatroomServerEndpoint {
	public static Set<Session> allSessions = Collections.synchronizedSet(new HashSet<Session>());
	public static Set<Session> gpsTrackerDeviceSessions = Collections.synchronizedSet(new HashSet<Session>());
	public static Set<Session> clientSessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void handleOpen(Session userSession) throws IOException, EncodeException {
		allSessions.add(userSession);
		Iterator<Session> iterator = allSessions.iterator();
		while (iterator.hasNext()) {
			iterator.next().getBasicRemote().sendObject(new UsersMessage(getIds()));
		}
	}
	// asda
	@OnMessage
	public void handleMessage(Message incomingMessage, Session userSession) throws IOException, EncodeException {
		System.out.println(incomingMessage.toString());
		if (incomingMessage instanceof ChatMessage) {
			ChatMessage chatMessage = (ChatMessage) incomingMessage;
			ChatMessage outgoingChatMessage = new ChatMessage();
			Iterator<Session> iterator = allSessions.iterator();
			String username = (String) userSession.getUserProperties().get("username");
			if (username == null) {
				userSession.getUserProperties().put("username", chatMessage.getMessage());
				outgoingChatMessage.setName("System");
				outgoingChatMessage.setLocation("US");
				outgoingChatMessage.setMessage("You are now connected as " + chatMessage.getMessage());
				userSession.getBasicRemote().sendObject(outgoingChatMessage);
				while (iterator.hasNext()) {
					iterator.next().getBasicRemote().sendObject(new UsersMessage(getIds()));
				}			
			} else {
				outgoingChatMessage.setName(username);
				outgoingChatMessage.setLocation(chatMessage.getLocation());
				outgoingChatMessage.setMessage(chatMessage.getMessage());
				while(iterator.hasNext()){
					iterator.next().getBasicRemote().sendObject(outgoingChatMessage);
				}	
			}
		} else if (incomingMessage instanceof GeoDataMessage){
			String username = (String) userSession.getUserProperties().get("username");
			GeoDataMessage geoMessage = (GeoDataMessage) incomingMessage;
			if (username == null) {
				userSession.getUserProperties().put("username", geoMessage.getDeviceId());
			}
			System.out.println(geoMessage.getDeviceId());
			System.out.println(geoMessage.getTimestamp());
			System.out.println(geoMessage.getLatitude());
			System.out.println(geoMessage.getLongitude());
			System.out.println(geoMessage.getAltitude());
		}
		
		Iterator<Session> iterator = allSessions.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getUserProperties().get("username").toString());
		}
	}

	@OnClose
	public void handleClose(Session userSession) throws IOException, EncodeException {
		allSessions.remove(userSession);
		Iterator<Session> iterator = allSessions.iterator();
		while (iterator.hasNext()) {
			iterator.next().getBasicRemote().sendObject(new UsersMessage(getIds()));
		}
	}

	@OnError
	public void handleError(Throwable t) {

	}
	
	private static Set<String> getIds(){
		HashSet<String> resultSet = new HashSet<>();
		Iterator<Session> iterator = allSessions.iterator();
		while(iterator.hasNext()){
			resultSet.add(iterator.next().getUserProperties().get("username").toString());
		}
		return resultSet;
	}

	

}