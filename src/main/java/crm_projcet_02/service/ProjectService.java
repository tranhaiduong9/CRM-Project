package crm_projcet_02.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import crm_projcet_02.entity.Job;
import crm_projcet_02.entity.Project;
import crm_projcet_02.entity.Users;
import crm_projcet_02.repository.JobRepository;
import crm_projcet_02.repository.ProjectRepository;
import crm_projcet_02.repository.UserRepository;

public class ProjectService {

	private ProjectRepository projectRepository = new ProjectRepository();
	private JobRepository jobRepository = new JobRepository();
	private UserRepository userRepository = new UserRepository();

	public boolean addProject(String name, String startDate, String endDate) {
		startDate = dayFormat_ddmmyyyy_To_yyyymmdd(startDate);
		endDate = dayFormat_ddmmyyyy_To_yyyymmdd(endDate);
		int count = projectRepository.insert(name, startDate, endDate);
		return count > 0;
	}

	public List<Project> getAllProject() {
		List<Project> listProjects = projectRepository.findAll();
		for (Project item : listProjects) {
			item.setStartDate(dayFormat_yyyymmdd_To_ddmmyyy(item.getStartDate()));
			item.setEndDate(dayFormat_yyyymmdd_To_ddmmyyy(item.getEndDate()));
		}
		return listProjects;
	}

	public boolean deleteProject(int id) {
		return projectRepository.deleteById(id) > 0;
	}

	public Project getById(int id) {
		Project project = new Project();
		project = projectRepository.findById(id);
		project.setStartDate(dayFormat_yyyymmdd_To_ddmmyyy(project.getStartDate()));
		project.setEndDate(dayFormat_yyyymmdd_To_ddmmyyy(project.getEndDate()));
		return project;
	}

	public boolean updateById(String name, String startDate, String endDate, int id) {
		startDate = dayFormat_ddmmyyyy_To_yyyymmdd(startDate);
		endDate = dayFormat_ddmmyyyy_To_yyyymmdd(endDate);
		return projectRepository.updateById(name, startDate, endDate, id) > 0;
	}

	public List<Users> getUserByProjectId(int idProject) {
		return userRepository.findByProjectId(idProject);
	}
	
	public List<Job> getJobByProjectId (int idProject) {
		return jobRepository.findByProjectId(idProject);
	}

	public List<Project> getByUserId(int idUser) {
		List<Project> listProjects = projectRepository.findByUserId(idUser);
		for (Project item : listProjects) {
			item.setStartDate(dayFormat_yyyymmdd_To_ddmmyyy(item.getStartDate()));
			item.setEndDate(dayFormat_yyyymmdd_To_ddmmyyy(item.getEndDate()));
		}
		return listProjects;
	}
	
	public String dayFormat_ddmmyyyy_To_yyyymmdd(String stringDate) {
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

	public String dayFormat_yyyymmdd_To_ddmmyyy(String stringDate_yyymmdd) {
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
