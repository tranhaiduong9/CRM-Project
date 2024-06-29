package crm_projcet_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_projcet_02.config.MysqlConfig;
import crm_projcet_02.entity.Job;
import crm_projcet_02.entity.Project;
import crm_projcet_02.entity.Users;

public class JobRepository {

	public int insert(String jobName, String startDate, String endDate, int idProject, int idUser) {
		String query = "INSERT  INTO Job (name, startDate, endDate, id_project) VALUES (?,?,?,?)";
		String query2 = "SET @last_id_in_Job = LAST_INSERT_ID()";
		String query3 = "INSERT INTO Job_Status_Users VALUES (?, 3, @last_id_in_job, curdate())";
		String query4 = "INSERT INTO Project_Users VALUES (?, ?, curdate())";
		int count1 = 0;
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, jobName);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, idProject);
			count1 = statement.executeUpdate();
			
			PreparedStatement statement2 = connection.prepareStatement(query2);
			statement2.executeUpdate();
			
			PreparedStatement statement3 = connection.prepareStatement(query3);
			statement3.setInt(1, idUser);
			statement3.executeUpdate();
			
			PreparedStatement statement4 = connection.prepareStatement(query4);
			statement4.setInt(1, idUser);
			statement4.setInt(2, idProject);
			statement4.executeUpdate();
			
			System.out.println("Thêm job và project thành công");
		} catch (SQLException e) {
			if (count1 >0) {
				System.out.println("Thêm job thành công");
			} else {
			System.out.println("Lỗi thêm job " + e.getLocalizedMessage());
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count1;
	}

	public List<Job> findAll() {
		List<Job> listJobs = new ArrayList<Job>();
		String query = "SELECT j.id, j.name, p.name as tenDuAn, u.fullName, j.startDate, j.endDate, s.name as status\r\n"
				+ "FROM Job j JOIN Job_Status_Users jsu  ON j.id = jsu.id_job\r\n"
				+ "JOIN Project p ON j.id_project = p.id\r\n" + "JOIN Users u ON jsu.id_user = u.id\r\n"
				+ "JOIN Status s  ON jsu.id_status = s.id";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Job job = new Job();
				job.setId(resultSet.getInt("id"));
				job.setName(resultSet.getString("name"));
				job.setStartDate(resultSet.getString("startDate"));
				job.setEndDate(resultSet.getString("endDate"));
				job.setStatus(resultSet.getString("status"));

				Project project = new Project();
				project.setName(resultSet.getString("tenDuAn"));
				job.setProject(project);

				Users user = new Users();
				user.setFullName(resultSet.getString("fullName"));
				job.setUser(user);

				listJobs.add(job);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi lấy danh sách task " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return listJobs;
	}

	public List<Job> findByUserId(int userId) {
		List<Job> listJobs = new ArrayList<Job>();
		String query = "SELECT j.id, j.name, p.name as tenDuAn, j.startDate, j.endDate, s.name as status\r\n"
				+ "FROM Job j JOIN Job_Status_Users jsu  ON j.id = jsu.id_job\r\n"
				+ "JOIN Project p ON j.id_project = p.id\r\n" 
				+ "JOIN Status s  ON jsu.id_status = s.id \r\n"
				+ "WHERE jsu.id_user  = ? \r\n" + "ORDER BY j.id";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Job job = new Job();
				job.setId(resultSet.getInt("id"));
				job.setName(resultSet.getString("name"));
				job.setStartDate(resultSet.getString("startDate"));
				job.setEndDate(resultSet.getString("endDate"));
				job.setStatus(resultSet.getString("status"));

				Project project = new Project();
				project.setName(resultSet.getString("tenDuAn"));
				job.setProject(project);

				listJobs.add(job);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi lấy danh sách task " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return listJobs;
	}

	public int updateStatusByIdJob(int userId, int idJob, int idStatus) {
		String query = "UPDATE Job_Status_Users SET id_status = ? WHERE id_user = ? AND id_job =?";
		int count = 0;
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idStatus);
			statement.setInt(2, userId);
			statement.setInt(3, idJob);
			count = statement.executeUpdate();
			System.out.println("Cập nhật thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi cập nhật job " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}

	public int deleteById(int id) {
		int count = 0;
		String query1 = "DELETE FROM Job_Status_Users WHERE id_job =?";
		String query2 = "DELETE FROM Job WHERE id = ?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement1 = connection.prepareStatement(query1);
			statement1.setInt(1, id);
			statement1.executeUpdate();
			PreparedStatement statement2 = connection.prepareStatement(query2);
			statement2.setInt(1, id);
			count = statement2.executeUpdate();
			System.out.println("Xóa job thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi xóa job " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}

	public Job findById(int id) {
		Job job = new Job();
		String query = "SELECT j.id, j.name, p.name as tenDuAn, j.startDate, j.endDate, s.name as status\r\n"
				+ "FROM Job j JOIN Job_Status_Users jsu  ON j.id = jsu.id_job\r\n"
				+ "JOIN Project p ON j.id_project = p.id\r\n" + "JOIN Status s  ON jsu.id_status = s.id \r\n"
				+ "WHERE j.id = ?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				job.setId(resultSet.getInt("id"));
				job.setName(resultSet.getString("name"));
				job.setStartDate(resultSet.getString("startDate"));
				job.setEndDate(resultSet.getString("endDate"));
				job.setStatus(resultSet.getString("status"));

				Project project = new Project();
				project.setName(resultSet.getString("tenDuAn"));
				job.setProject(project);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi lấy danh sách task " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return job;
	}

	public int updateById (String name, String startDate, String endDate, int idStatus, int id) {
		String query1 = "UPDATE Job_Status_Users SET id_status = ? WHERE id_job = ?";
		String query2 = "UPDATE Job SET name = ?, startDate = ?, endDate = ? WHERE id = ?";
		int count = 0;
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement1 = connection.prepareStatement(query1);
			statement1.setInt(1, idStatus);
			statement1.setInt(2, id);
			statement1.executeUpdate();
			PreparedStatement statement2 = connection.prepareStatement(query2);
			statement2.setString(1, name);
			statement2.setString(2, startDate);
			statement2.setString(3, endDate);
			statement2.setInt(4, id);
			count = statement2.executeUpdate();
			System.out.println("Cập nhật thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi cập nhật job " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}

	public List<Job> findByProjectId (int idProject) {
		List<Job> listJobs = new ArrayList<Job>();
		String query = "SELECT j.id, j.name, j.startDate , j.endDate , s.name as status, jsu.id_user FROM Job j\r\n"
				+ "JOIN Job_Status_Users jsu ON j.id = jsu.id_job\r\n"
				+ "JOIN Status s ON jsu.id_status = s.id WHERE j.id_project =?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idProject);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Job job = new Job();
				job.setId(resultSet.getInt("id"));
				job.setName(resultSet.getString("name"));
				job.setStartDate(resultSet.getString("startDate"));
				job.setEndDate(resultSet.getString("endDate"));
				job.setStatus(resultSet.getString("status"));

				Users user = new Users();
				user.setId(resultSet.getInt("id_user"));
				job.setUser(user);

				listJobs.add(job);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi lấy danh sách task " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return listJobs;
	}
}
