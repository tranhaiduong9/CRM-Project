package crm_projcet_02.service;

import java.util.ArrayList;
import java.util.List;

import crm_projcet_02.entity.Job;
import crm_projcet_02.entity.Status;
import crm_projcet_02.entity.Users;
import crm_projcet_02.repository.JobRepository;
import crm_projcet_02.repository.StatusRepository;
import crm_projcet_02.repository.UserRepository;

public class ProfileService {

	private JobRepository jobRepository = new JobRepository();
	private JobService jobService = new JobService();
	private UserRepository userRepository = new UserRepository();
	private StatusRepository statusRepository = new StatusRepository();

	public List<Job> getByUserId(int userId) {
		List<Job> listProfiles = new ArrayList<Job>();
		listProfiles = jobRepository.findByUserId(userId);
		for (Job item : listProfiles) {
			item.setStartDate(jobService.dayFormat_yyyymmdd_To_ddmmyyy(item.getStartDate()));
			item.setEndDate(jobService.dayFormat_yyyymmdd_To_ddmmyyy(item.getEndDate()));
		}
		return listProfiles;
	}

	public Users getUserByID(int idUser) {
		return userRepository.findById(idUser);
	}

	public List<Status> getAllStatus() {
		return statusRepository.findAll();
	}

	public boolean updateStatus(int userId, int idJob, int idStatus) {
		return jobRepository.updateStatusByIdJob(userId, idJob, idStatus) > 0;
	}
}
