package crm_projcet_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_projcet_02.config.MysqlConfig;
import crm_projcet_02.entity.Role;

/**
 * 
 * RoleRepository: chứa toàn bộ câu truy vấn liên quan đến bảng ROLE
 *
 */
public class RoleRepository {

	public int insert(String name, String desc) {
		String query = "INSERT INTO Role (name, description) VALUES (?, ?)";
		int count = 0;
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, desc);
			count = statement.executeUpdate();
			System.out.println("Thêm thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi thêm role " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

//	Đối với câu SELECT tên hàm sẽ bắt đầu bằng chữ "find"
//	Nếu có điều kiện WHERE sẽ đại diện bằng chứ "by"
//	Ví dụ: SELECT * FROM Role WHERE name = ... => findByRoleName
	public List<Role> findAll() {
		List<Role> listRole = new ArrayList<Role>();
		String query = "SELECT * FROM Role";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
				listRole.add(role);
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
		return listRole;
	}

//	Khi xóa role sẽ đổi tất cả user có role hiện tại thành MEMBER (id_role=3)
	public int deleteById (int id) {
		int count = 0;
		String query1 = "UPDATE Users SET id_role = 3 WHERE id_role = ?";
		String query2 = "DELETE FROM `Role` WHERE id =?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement1 = connection.prepareStatement(query1);
			statement1.setInt(1, id);
			statement1.executeUpdate();
			
			PreparedStatement statement2 = connection.prepareStatement(query2);
			statement2.setInt(1, id);
			count = statement2.executeUpdate();
			System.out.println("Xóa role thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi xóa role " + e.getLocalizedMessage());
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

	public Role findById(int id) {
		Role role = new Role();
		String query = "SELECT * FROM `Role` WHERE id=?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
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
		return role;
	}

	public int updateById(String name, String desc, int id) {
		int count =0;
		String query = "UPDATE `Role` SET name=?, description=? WHERE id=?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, desc);
			statement.setInt(3, id);
			count = statement.executeUpdate();
			System.out.println("Cập nhật role thành công");
		} catch (SQLException e) {
			System.out.println("Lỗi xóa role " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}
}
