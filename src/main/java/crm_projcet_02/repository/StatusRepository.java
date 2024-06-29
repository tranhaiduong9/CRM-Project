package crm_projcet_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_projcet_02.config.MysqlConfig;
import crm_projcet_02.entity.Status;

public class StatusRepository {
	public List<Status> findAll () {
		List<Status> listStatus = new ArrayList<Status>();
		String query = "SELECT * FROM Status";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Status status = new Status();
				status.setId(resultSet.getInt("id"));
				status.setName(resultSet.getString("name"));
				
				listStatus.add(status);
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
		return listStatus;
	}
}
