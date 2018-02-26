package challenge.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import challenge.controller.exception.CustomAPIException;
import challenge.entity.User;
import challenge.service.UserService;

@RestController
@RequestMapping(value = "app")
public class UserController {

	@Autowired
	UserService service;

	@RequestMapping(method = RequestMethod.GET, value = "following", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getUsersIFollow(Authentication authentication) {
		if(authentication == null){
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),HttpStatus.UNAUTHORIZED);
		}
		User exists = service.findUser(authentication.getName());
		if (exists == null) {
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),
					HttpStatus.UNAUTHORIZED);
		}
		
		List<User> users = service.getUsersIFollow(exists);
		if (users.isEmpty()) {
			return new ResponseEntity<>(new CustomAPIException("Search resulted in an empty list"),
					HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "followers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getFollowers(Authentication authentication) {
		if(authentication == null){
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),HttpStatus.UNAUTHORIZED);
		}
		User exists = service.findUser(authentication.getName());
		if (exists == null) {
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),
					HttpStatus.UNAUTHORIZED);
		}
		List<User> users = service.getFollowers(exists);
		if (users.isEmpty()) {
			return new ResponseEntity<>(new CustomAPIException("Search resulted in an empty list"),
					HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "follow/{userIdToFollow}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> doFollow(Authentication authentication, @PathVariable String userIdToFollow) {
		if(authentication == null){
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),HttpStatus.UNAUTHORIZED);
		}
		User exists = service.findUser(authentication.getName());
		if (exists == null) {
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),
					HttpStatus.UNAUTHORIZED);
		}
		User userToFollow = service.findUser(userIdToFollow);
		if (userToFollow == null) {
			return new ResponseEntity<>(new CustomAPIException("User with handle '" + userIdToFollow + "' was not found"),
					HttpStatus.NOT_FOUND);
		}
		
		Boolean followFlag = service.checkFollowing(exists,userToFollow);
		System.out.println(followFlag);
		if(!followFlag){		
			if (service.doFollow(exists, userToFollow) == 1) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}else{
			return new ResponseEntity<>(new CustomAPIException("Already following the user with handle '"+userIdToFollow+"'"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new CustomAPIException("Bad Request"), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "unfollow/{userIdToUnfollow}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> doUnfollow(Authentication authentication, @PathVariable String userIdToUnfollow) {
		if(authentication == null){
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),HttpStatus.UNAUTHORIZED);
		}
		User exists = service.findUser(authentication.getName());
		if (exists == null) {
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),
					HttpStatus.UNAUTHORIZED);
		}
		User userToUnfollow = service.findUser(userIdToUnfollow);
		if (userToUnfollow == null) {
			return new ResponseEntity<>(
					new CustomAPIException("User with handle '" + userIdToUnfollow + "' was not found"),
					HttpStatus.NOT_FOUND);
		}
		Boolean followFlag = service.checkFollowing(exists,userToUnfollow);
		System.out.println(followFlag);
		if(followFlag){
			if (service.doUnfollow(exists, userToUnfollow) == 1) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}else{
			return new ResponseEntity<>(new CustomAPIException("Cannot Unfollow a user who is not on followers list"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new CustomAPIException("Bad Request"), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.GET, value = "popularFollower", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getPopularFollower() {

		Map<User, User> userMap = service.getPopularFollower();
		if (userMap.isEmpty()) {
			return new ResponseEntity<>(new CustomAPIException("Search resulted in an empty list"),
					HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Map<User, User>>(userMap, HttpStatus.OK);
	}

}
