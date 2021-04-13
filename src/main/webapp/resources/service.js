var service = (function() {
	
	function deleteNotice(nno, csrfTokenValue, writer){
		
		return fetch("/admin/notice/delete", {
				method : 'post',
				headers : {'Content-Type' : 'application/x-www-form-urlencoded',
							'X-CSRF-TOKEN': csrfTokenValue},
				body : "nno="+nno+"&writer="+writer+""
		}).then(res => res.text())
	}
	
	function register(obj, csrfTokenValue){
	
		return fetch("/admin/notice/register",{
				method : 'post',
				headers : {'Content-Type' : 'application/json',
							'X-CSRF-TOKEN': csrfTokenValue},
				body : JSON.stringify(obj)
		}).then(res => res.text())
	
	}
	
	function upload(formdata,csrfTokenValue){
	
		return fetch("/admin/common/notice/upload",{
				headers : {'X-CSRF-TOKEN': csrfTokenValue},
				method : 'post',
				body : formdata
		}).then(res => res.json())
	}
	
	
	function modify(obj,csrfTokenValue){
		
		return fetch("/admin/notice/modify",{
				method : 'post',
				headers : {'Content-Type' : 'application/json',
							'X-CSRF-TOKEN': csrfTokenValue},
				body : JSON.stringify(obj)
		}).then(res => res.text())
	}
	
	function fileDelete(param,csrfTokenValue){
	
		return fetch("/admin/common/notice/delete",{
			method : 'post',
			headers : {'Content-Type' : 'application/json',
						'X-CSRF-TOKEN': csrfTokenValue},
			body : JSON.stringify(param)
		})
	}
	
	function getFiles(nno){
	
		return fetch("/admin/common/notice/getFiles?nno="+nno,{
			method : 'get'
		})
	}
	
	

     function sendUpload(fd,csrfTokenValue){
      return fetch("/admin/common/manager/doc/upload",{
         method : 'post',
         headers : {'X-CSRF-TOKEN': csrfTokenValue},
         body : fd
      }).then(res => res.json())
   }
   
   function sendUploadThumb(fd,csrfTokenValue){
   
   	sendUpload(fd).then(result => {
      for (var i = 0; i < result.length; i++) {
         let file = result[i]
         console.log(file.link)
      document.querySelector(".fileThumb").innerHTML = "<img src='/admin/common/manager/view?link="+file.thumbLink+"' style = 'width: 90px; height: 90px' />" +
                                           "<button onclick=sendRemove("+JSON.stringify(file)+")'>DEL</button>"
         
      }
   })
   }
       
    function sendRegister(obj){
		return fetch("/admin/manager/register" , {
			method : 'post',
			headers : {"Content-Type":"application/json",
					'X-CSRF-TOKEN': csrfTokenValue},
			body : JSON.stringify(obj)
		}).then(res => res.text())
	}   
       
       
        return {deleteNotice:deleteNotice, register:register, upload:upload , modify:modify, fileDelete:fileDelete, getFiles:getFiles,sendUpload:sendUpload, sendUploadThumb:sendUploadThumb , sendRegister:sendRegister}

    }())