package crm_projcet_02.service;

import java.util.ArrayList;
import java.util.List;

import crm_projcet_02.entity.Role;
import crm_projcet_02.repository.RoleRepository;

/**
 * 
 * RoleService: chứa những class chuyên đi xử lý logic cho controller
 * Cách đặt tên: giống với controller
 * Ví dụ: RoleController => RoleService
 *
 * Cách đặt tên hàm: đặt tên hàm ứng với lại chức năng sẽ làm trên giao diện/bên controller
 */
public class RoleService {
	
	private RoleRepository roleRepository = new RoleRepository();
	
	public boolean addRole (String name, String desc) {
		int count = roleRepository.insert(name, desc);
		return count>0;
	}

//	Khi xóa role sẽ đổi tất cả user có role hiện tại thành MEMBER (id_role=3)
	public boolean deleteRole (int id) {
		return roleRepository.deleteById(id) > 0;
	}

	public List<Role> getAllRole() {
		List<Role> list = new ArrayList<>(); 
		list = roleRepository.findAll();
		return list;
	}

	public Role getById(int id) {
		return roleRepository.findById(id);
	}

	public boolean updateById(String name, String desc, int id) {
		return roleRepository.updateById(name, desc, id) >0;
	}
}
