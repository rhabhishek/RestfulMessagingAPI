package challenge.service;

import java.util.List;
import java.util.Map;

import challenge.entity.User;

public interface UserService {

	public int doFollow(User user, User toFollow);
	
	public int doUnfollow(User user, User toUnFollow);

	public List<User> getUsersIFollow(User user);

	public List<User> getFollowers(User user);

	public User findUser(String name);

	public Map<User, User> getPopularFollower();

	public Boolean checkFollowing(User user, User paramUser);

}
