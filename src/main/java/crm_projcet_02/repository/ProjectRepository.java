package crm_projcet_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_projcet_02.config.MysqlConfig;
import crm_projcet_02.entity.Project;
import crm_projcet_02.entity.Users;

public class ProjectRepository {

	public int insert(String name, String startDate, String endDate) {
		String query = "INSERT INTO Project (name, startDate, endDate) VALUES (?, ?, ?)";
		int count = 0;
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			count = statement.executeUpdate();
			System.out.println("Thêm thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi thêm project " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}

	public List<Project> findAll() {
		List<Project> listProjects = new ArrayList<Project>();
		String query = "SELECT * FROM Project";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Project project = new Project();
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setStartDate(resultSet.getString("startDate"));
				project.setEndDate(resultSet.getString("endDate"));
				listProjects.add(project);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi truy vấn " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return listProjects;
	}

	public int deleteById(int id) {
		int count = 0;
		String query1 = "DELETE FROM Project_Users WHERE id_project = ?";
		String query2 = "DELETE FROM jst USING Job j JOIN Job_Status_Users jst WHERE jst.id_job = j.id AND id_project = ?";
		String query3 = "DELETE FROM Job WHERE id_project = ?";
		String query4 = "DELETE FROM Project WHERE id = ?";

		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement1 = connection.prepareStatement(query1);
			statement1.setInt(1, id);
			statement1.executeUpdate();
			PreparedStatement statement2 = connection.prepareStatement(query2);
			statement2.setInt(1, id);
			statement2.executeUpdate();
			PreparedStatement statement3 = connection.prepareStatement(query3);
			statement3.setInt(1, id);
			statement3.executeUpdate();
			PreparedStatement statement4 = connection.prepareStatement(query4);
			statement4.setInt(1, id);
			count = statement4.executeUpdate();
			System.out.println("Xóa job thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi xóa job " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}

	public Project findById(int id) {
		Project project = new Project();
		String query = "SELECT * FROM Project WHERE id = ?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setStartDate(resultSet.getString("startDate"));
				project.setEndDate(resultSet.getString("endDate"));
			}
		} catch (SQLException e) {
			System.out.println("Lỗi truy vấn " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return project;
	}

	public int updateById(String name, String startDate, String endDate, int id) {
		int count = 0;
		String query = "UPDATE Project SET name = ?, startDate = ?, endDate = ? WHERE id = ?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, id);
			count = statement.executeUpdate();
			System.out.println("Cập nhật project thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi cập nhật project " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}

	public List<Project> findByUserId(int idUser) {
		List<Project> listProjects = new ArrayList<>();
		String query = "SELECT * FROM Project p JOIN Project_Users pu ON p.id = pu.id_project WHERE pu.id_user = ?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idUser);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Project project = new Project();
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setStartDate(resultSet.getString("startDate"));
				project.setEndDate(resultSet.getString("endDate"));
				
				Users user = new Users();
				user.setId(resultSet.getInt("id_user"));
				project.setUser(user);
				
				listProjects.add(project);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi truy vấn " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return listProjects;
	}
}
