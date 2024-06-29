package crm_projcet_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_projcet_02.config.MysqlConfig;
import crm_projcet_02.entity.Role;
import crm_projcet_02.entity.Users;

public class UserRepository {
	public int insert(String fullName, String email, String password, String phone, int idRole) {
		
		String query = "INSERT INTO Users (fullName, email, pwd, phone, id_role)\r\n"
				+ "VALUES (?, ?, ?, ?, ?)";
		int count = 0;
		
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, fullName);
			statement.setString(2, email);
			statement.setString(3, password);
			statement.setString(4, phone);
			statement.setInt(5, idRole);
			
			count = statement.executeUpdate();
			System.out.println("Thêm thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi thêm user " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}
	
	public List<Users> findAllUsersAndRole() {
		List<Users> list = new ArrayList<Users>();
		String query = "SELECT * FROM Users u JOIN Role r ON u.id_role = r.id";
		
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Users user = new Users();
				user.setId(resultSet.getInt("id"));
				user.setFirstName(resultSet.getString("firstName"));
				user.setLastName(resultSet.getString("lastName"));
				user.setFullName(resultSet.getString("fullName"));
				user.setUserName(resultSet.getString("userName"));
				
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				
				user.setRole(role);
				
				list.add(user);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi lấy danh sách user " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return list;
	}

	public int deleteById(int id) {
		int count = 0;
		String query1 = "DELETE FROM Project_Users WHERE id_user = ?";
		String query2 = "DELETE FROM Job_Status_Users WHERE id_user = ?";
		String query3 = "DELETE FROM Users u WHERE u.id = ?";
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
			count = statement3.executeUpdate();
			System.out.println("Xóa user thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi xóa user " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}
	
	public List<Users> findAll() {
		List<Users> listUsers = new ArrayList<Users>();
		String query = "SELECT * FROM Users";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Users user = new Users();
				user.setId(resultSet.getInt("id"));
				user.setFullName(resultSet.getString("fullName"));
				user.setEmail(resultSet.getString("email"));
				user.setUserName(resultSet.getString("userName"));
				user.setPhone(resultSet.getString("phone"));
				
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				
				user.setRole(role);
				
				listUsers.add(user);
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
		return listUsers;
	}

	public Users findByEmailPassword(String email, String password) {
		Users user = new Users();
		String query = "SELECT * FROM Users u JOIN `Role` r ON u.id_role = r.id WHERE u.email = ? AND u.pwd = ?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet =  statement.executeQuery();
			while(resultSet.next()) {
				user.setEmail(resultSet.getString("email"));
				user.setId(resultSet.getInt("id"));
				user.setFullName(resultSet.getString("fullName"));
				
				Role role = new Role();
				role.setId(resultSet.getInt("id_role"));
				role.setName(resultSet.getString("name"));
				user.setRole(role);
			}
		} catch (SQLException e) {
			System.out.println("lỗi truy vấn " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return user;
	}
	
	public Users findById (int idUser) {
		Users user = new Users();
		String query = "SELECT * FROM Users u WHERE u.id = ?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idUser);
			ResultSet resultSet =  statement.executeQuery();
			while(resultSet.next()) {
				user.setId(resultSet.getInt("id"));
				user.setFullName(resultSet.getString("fullName"));
				user.setEmail(resultSet.getString("email"));
				user.setFirstName(resultSet.getString("firstName"));
				user.setLastName(resultSet.getString("lastName"));
				user.setPhone(resultSet.getString("phone"));
				user.setUserName(resultSet.getString("userName"));
				
				Role role = new Role();
				role.setId(resultSet.getInt("id_role"));
				user.setRole(role);
			}
		} catch (SQLException e) {
			System.out.println("lỗi truy vấn " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return user;
	}

	public int updateById(String fullName, String firstName, String lastName, String userName, String phone, int role, int id ) {
		int count = 0;
		String query = "UPDATE Users SET fullName =?, \r\n"
				+ "firstName =?, \r\n"
				+ "lastName =?, \r\n"
				+ "userName =?, \r\n"
				+ "phone =?, \r\n"
				+ "id_role =? \r\n"
				+ "WHERE id=?;";
		Connection connection = MysqlConfig.getConnect();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, fullName);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setString(4, userName);
			statement.setString(5, phone);
			statement.setInt(6, role);
			statement.setInt(7, id);
			count = statement.executeUpdate();
			System.out.println("Cập nhật user thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi cập nhật user " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}

	public List<Users> findByProjectId(int idProject) {
		List<Users> listUser = new ArrayList<>();
		String query = "SELECT u.id, u.fullName FROM Users u JOIN Project_Users pu ON u.id = pu.id_user  WHERE id_project =?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idProject);
			ResultSet resultSet =  statement.executeQuery();
			while(resultSet.next()) {
				Users user = new Users();
				user.setId(resultSet.getInt("id"));
				user.setFullName(resultSet.getString("fullName"));
				
				listUser.add(user);
			}
		} catch (SQLException e) {
			System.out.println("lỗi truy vấn " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return listUser;
	}
}


