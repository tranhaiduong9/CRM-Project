package crm_projcet_02.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import crm_projcet_02.entity.Job;
import crm_projcet_02.entity.Project;
import crm_projcet_02.entity.Users;
import crm_projcet_02.repository.JobRepository;
import crm_projcet_02.repository.ProjectRepository;
import crm_projcet_02.repository.UserRepository;

public class JobService {
	
	private ProjectRepository projectRepository = new ProjectRepository();
	private UserRepository userRepository = new UserRepository();
	private JobRepository jobRepository = new JobRepository();
	
	public List<Project> getAllProject () {
		return projectRepository.findAll();
	}
	
	public List<Users> getAllUserFullName () {
		return userRepository.findAll();
	}
	
	public boolean addJob (String jobName, String startDate, String endDate, int idProject, int idUser) {
		startDate = dayFormat_ddmmyyyy_To_yyyymmdd(startDate);
		endDate = dayFormat_ddmmyyyy_To_yyyymmdd(endDate);
		int count = jobRepository.insert(jobName, startDate, endDate, idProject, idUser);
		return count>0;
	}
	
	public List<Job> getAllJob () {
		List<Job> listJobs = new ArrayList<Job>();
		listJobs = jobRepository.findAll();
		for (Job item : listJobs) {
			item.setStartDate(dayFormat_yyyymmdd_To_ddmmyyy(item.getStartDate()));
			item.setEndDate(dayFormat_yyyymmdd_To_ddmmyyy(item.getEndDate()));
		}
		return listJobs;
	}
	
	public boolean deleteById(int id) {
		return jobRepository.deleteById(id) > 0;
	}
	
	public Job getById(int id) {
		Job job = new Job();
		job = jobRepository.findById(id);
		job.setStartDate(dayFormat_yyyymmdd_To_ddmmyyy(job.getStartDate()));
		job.setEndDate(dayFormat_yyyymmdd_To_ddmmyyy(job.getEndDate()));
		return job;
	}
	
	public boolean updateById(String name, String startDate, String endDate, int idStatus, int id) {
		startDate = dayFormat_ddmmyyyy_To_yyyymmdd(startDate);
		endDate = dayFormat_ddmmyyyy_To_yyyymmdd(endDate);
		return jobRepository.updateById(name, startDate, endDate, idStatus, id) > 0 ;
	}
	
	public String dayFormat_ddmmyyyy_To_yyyymmdd (String stringDate) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String convertedDay = "";
		
		try {
			date = formatter.parse(stringDate);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			convertedDay = formatter1.format(date);
		} catch (ParseException e) {
			System.out.println("Lỗi " + e.getLocalizedMessage());
		}
		return convertedDay;
	}
	
	public String dayFormat_yyyymmdd_To_ddmmyyy (String stringDate_yyymmdd) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String convertedDay = "";
		
		try {
			date = formatter.parse(stringDate_yyymmdd);
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
			convertedDay = formatter1.format(date);
		} catch (ParseException e) {
			System.out.println("Lỗi " + e.getLocalizedMessage());
		}
		return convertedDay;
	}
}
