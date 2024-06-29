package crm_projcet_02.service;

import crm_projcet_02.entity.Users;
import crm_projcet_02.repository.UserRepository;

public class LoginService {
	
	private UserRepository userRepository = new UserRepository();
	
	public boolean userLogin (String email, String password) {
		Users user = new Users();
		user = userRepository.findByEmailPassword(email, password);
		return user.getId() != 0;
	}
	
	public Users getUserByEmailAndPassword (String email, String password) {
		Users user = new Users();
		user = userRepository.findByEmailPassword(email, password);
		return user;
	}
}
