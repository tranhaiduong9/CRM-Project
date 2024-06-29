$(document).on('click', '.btn-xoa', function(){
		if (confirm("KHI XÓA QUYỀN, HỆ THỐNG SẼ CẬP NHẬT TẤT CẢ NGƯỜI DÙNG THUỘC QUYỀN NÀY THÀNH MEMBER. BẠN CÓ MUỐN XÓA?") == true) {
			var id = $(this).attr('id-role')
			var This = $(this)
			$.ajax({
				method: "GET",
				url: `http://localhost:8080/crm_projcet_02/api/role/delete?id=${id}`
			})
				.done(function(result) {
					if (result.data == true) {
						alert("XÓA THÀNH CÔNG")
						This.closest('tr').remove()
					} else {
						alert("XÓA THẤT BẠI")
					}
					console.log(result.data)
				});
		}
}) 