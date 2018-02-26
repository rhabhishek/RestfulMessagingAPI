package challenge.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.entity.User;
import challenge.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	UserRepository repository;
	
	@Override
	public int doFollow(User user, User toFollow) {
		return repository.doFollow(user, toFollow);
	}

	@Override
	public int doUnfollow(User user, User toUnFollow) {
		return repository.doUnfollow(user, toUnFollow);
	}

	@Override
	public List<User> getUsersIFollow(User user) {
		return repository.getUsersIFollow(user);
	}

	@Override
	public List<User> getFollowers(User user) {
		return repository.getFollowers(user);
	}

	@Override
	public User findUser(String name) {
		
		List<User> list = repository.findUser(name);
		
		return (list.size() == 1)? list.get(0) : null;
	}

	@Override
	public Map<User,User> getPopularFollower() {
		List<User> userList = repository.getAllUsers();
		Map<User,User> map = new HashMap<User, User>();
		for (User user : userList) {
			map.put(user, repository.getPopularFollower(user));
		}
		return map;
	}

	@Override
	public Boolean checkFollowing(User user, User paramUser) {
		List<Integer> rows = repository.checkFollow(user,paramUser);
		return (rows.isEmpty())? false : true;
	}

}
