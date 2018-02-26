package challenge.service;

import java.util.List;

import challenge.entity.Message;
import challenge.entity.User;

public interface MessageService {
	
	List<Message> getMessages(User user);

	List<Message> searchMessages(User user, String keyword);

}
