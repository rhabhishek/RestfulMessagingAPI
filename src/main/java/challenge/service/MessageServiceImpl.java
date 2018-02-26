package challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.entity.Message;
import challenge.entity.User;
import challenge.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageRepository repository;
	
	@Override
	public List<Message> getMessages(User user) {
		return repository.getMessages(user);

	}

	@Override
	public List<Message> searchMessages(User user, String keyword) {
		return repository.searchMessages(user,keyword);
	}

}
