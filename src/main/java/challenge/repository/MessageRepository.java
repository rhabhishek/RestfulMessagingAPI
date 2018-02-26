package challenge.repository;

import java.util.List;

import challenge.entity.Message;
import challenge.entity.User;

public interface MessageRepository {
	
	public List<Message> getMessages(User user);

	public List<Message> searchMessages(User user, String keyword);

}
