$(document).on('click', '.btn-xoa', function(){
		if (confirm("BẠN CÓ MUỐN XÓA CÔNG VIỆC NÀY?") == true) {
			var id = $(this).attr('id-job')
			var This = $(this)
			$.ajax({
				method: "GET",
				url: `http://localhost:8080/crm_projcet_02/api/job/delete?id=${id}`
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