package challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import challenge.controller.exception.CustomAPIException;
import challenge.entity.Message;
import challenge.entity.User;
import challenge.service.MessageService;
import challenge.service.UserService;

@RestController
@RequestMapping("app")
@CrossOrigin(origins = "*", maxAge = 36000)
public class MessageController {

	@Autowired
	MessageService service;
	
	@Autowired
	UserService userService;
	

	@RequestMapping(method = RequestMethod.GET, value ="messages")
	@ResponseBody
	public ResponseEntity<?> getMessages(Authentication authentication) {
		if(authentication == null){
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),HttpStatus.UNAUTHORIZED);
		}
		User exists = userService.findUser(authentication.getName());
		if(exists == null){
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),HttpStatus.UNAUTHORIZED);
		}
		List<Message> messages = service.getMessages(exists);
		if(messages.isEmpty()){
			return new ResponseEntity<>(new CustomAPIException("Search resulted in an empty list"),HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Message>>(messages,HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "messages/search={keyword}")
	public ResponseEntity<?> searchMessage(Authentication authentication, @PathVariable String keyword) {
		if(authentication == null){
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),HttpStatus.UNAUTHORIZED);
		}
		User exists = userService.findUser(authentication.getName());
		if(exists == null){
			return new ResponseEntity<>(new CustomAPIException("Bad Credentials. Check authentication"),HttpStatus.UNAUTHORIZED);
		}
		if(keyword.equals(null)){
			getMessages(authentication);
		}
		System.out.println(keyword);
		List<Message> messages = service.searchMessages(exists,keyword);
		if(messages.isEmpty()){
			return new ResponseEntity<>(new CustomAPIException("Search resulted in an empty list"),HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Message>>(messages,HttpStatus.OK);
	}

}
