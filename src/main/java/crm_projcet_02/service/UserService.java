package crm_projcet_02.service;

import java.util.List;

import crm_projcet_02.entity.Job;
import crm_projcet_02.entity.Role;
import crm_projcet_02.entity.Users;
import crm_projcet_02.repository.JobRepository;
import crm_projcet_02.repository.RoleRepository;
import crm_projcet_02.repository.UserRepository;

public class UserService {
	
	private RoleRepository roleRepository = new RoleRepository();
	private UserRepository userRepository = new UserRepository();
	private JobRepository jobRepository = new JobRepository();
	
	public boolean addUser(String fullName, String email, String password, String phone, int idRole) {
		int count = userRepository.insert(fullName, email, password, phone, idRole);
		return count>0;
	}
	
	public List<Role> getAllRole() {
		return roleRepository.findAll();
	}
	
	public List<Users> getAllUsersAndRole() {
		return userRepository.findAllUsersAndRole();
	}
	
	public boolean deleteUser(int id) {
		int count = userRepository.deleteById(id); 
		return count >0;
	}
	
	public Users getUserById(int id) {
		return userRepository.findById(id);
	}
	
	public List<Job> getJobsList (int userId) {
		return jobRepository.findByUserId(userId);
	}

	public boolean updateUser(String fullName, String firstName, String lastName, String userName, String phone, int role, int id) {
		return userRepository.updateById(fullName, firstName, lastName, userName, phone, role, id) >0;
	}
}
