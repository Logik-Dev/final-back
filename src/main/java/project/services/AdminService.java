package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.exceptions.UserNotFoundException;
import project.models.entities.User;
import project.repositories.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User update(int userId, boolean locked) {
		User user = userRepository.findById(userId)
				.orElseThrow(UserNotFoundException::new);
		user.setAccountNonLocked(!locked);
		return userRepository.save(user);
	}
}
