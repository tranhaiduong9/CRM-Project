// Khi nào file html được load thì thực hiện gì đó
$(document).on('click', '.btn-xoa', function(){
		// Đăng ký sự kiện click: $("tên_thẻ || tên_class || id").click()
		// class => .
		// id => #
		// Đăng ký sự kiện click cho toàn bộ class có tên là "btn-xoa""
			if (confirm("BẠN CÓ MUỐN XÓA NGƯỜI DÙNG NÀY?") == true) {
			//Lấy giá trị của thuộc tính (attr) bên thẻ có class là .btn-xoa
			// $(this): đại diện cho function đang thực thi
			var id = $(this).attr('id-user')
			var This = $(this)
			// ajax (js), RestTemplate (java), HttpClient: Gọi đường dẫn bằng code
			$.ajax({
				method: "GET",
				url: `http://localhost:8080/crm_projcet_02/api/user/delete?id=${id}` // `...` : string literals
				// Tham số data chỉ dành cho phương thức POST
				//data: { name: "John", location: "Boston" }
			})
				.done(function(result) {
					// resutl đại diện cho kết quả từ link url trả về
					// Lấy giá trị kiểu object trong js thì : tenbien.key
					if (result.data == true) {
						alert("XÓA THÀNH CÔNG")
						// .parent: đi ra 1 thẻ cha
						// .closest: đi ra thằng cha được chỉ định
						// .remove: xóa thẻ
						//This.parent().parent().remove()
						This.closest('tr').remove()
					} else {
						alert("XÓA THẤT BẠI")
					}
					console.log(result.data)
				});
			}
}) 