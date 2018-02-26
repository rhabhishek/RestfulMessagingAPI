package challenge.repository;

import java.util.List;

import challenge.entity.User;

public interface UserRepository {

	List<User> findUser(String name);

	List<User> getUsersIFollow(User user);

	List<User> getFollowers(User user);
	
	User getPopularFollower(User user);
	
	List<User> getAllUsers();
	
	int doFollow(User user, User toFollow);
	
	int doUnfollow(User user, User toUnFollow);

	List<Integer> checkFollow(User user, User paramUser);

}
