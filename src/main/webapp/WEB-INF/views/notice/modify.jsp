<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes/header.jsp"%>
<div class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-12">
              <div class="card">
                <div class="card-header card-header-primary">
                  <h4 class="card-title">공지사항 등록</h4>
                </div>
                <div class="card-body">
                  <form>
                    <div class="row">
                      <div class="col-md-6">
                        <div class="form-group bmd-form-group">
                          <label class="bmd-label-floating">제목</label>
                          <input type="text" name="title" class="form-control" value="${notice.title}">
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="form-group bmd-form-group">
                          <label class="bmd-label-floating">작성자</label>
                          <input type="text" name="writer" class="form-control" value="${notice.writer}" readonly="readonly">
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="form-group bmd-form-group">
                          <label class="bmd-label-floating">카테고리</label>
                          <input type="text" name="category" class="form-control" value="${notice.category}">
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label>내용</label>
                          <div class="form-group bmd-form-group">
                            <textarea class="form-control" name="content" rows="20"><c:out value="${notice.content}" /></textarea>
                          </div>
                        </div>
                      </div>
                     </div>
                     <div class="row">
                      	<div class="col-md-12">
                      		<input style="height:10vh;" type="file" multiple="multiple" name="files">
                      	</div>
                     </div>
                     <div class="row">
                     <ul class="fileUl">
                     <c:forEach items="${files }" var="file">
                     ${file}
                     ${file.uploadPath}
                     	<c:if test="${!file.image}">
							<li id="li${file.uuid }"><i class='fas fa-file'></i>${file.fileName}<button onclick="delTempImg(event, {'uploadPath':'${file.uploadPath}', 'uuid':'${file.uuid }', 'fileName':'${file.fileName}', 'image':'${file.image}', 'nno':'${file.nno }'})">삭제</button></li>
						</c:if>
						<c:if test="${file.image }">
			            	<li id="li${file.uuid }"><img src='/admin/common/notice/view?link=${file.thumbLink}'/><button onclick="delTempImg(event, {'uploadPath':'${file.uploadPath}', 'uuid':'${file.uuid }', 'fileName':'${file.fileName}', 'image':'${file.image}', 'nno':'${file.nno }'})">삭제</button></li>
			            	</c:if>
                     </c:forEach>
                     </ul>
                      </div>
                       <div class="btnContainer">
						<button class="btn btn-primary btn-round modifyBtn">수정</button>
						<button class="btn btn-primary btn-round listBtn">목록으로</button>
					</div>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-md-4">
              <div class="card card-profile">
              </div>
            </div>
          </div>
        </div>
      </div>


<div class="modal" id="modifyModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">수정 확인</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<p>수정하시겠습니까?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary modalModifyBtn">수정</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>

<div class="modal" id="checkModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">등록 확인</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body checkModalBody">
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary checkBtn">확인</button>
			</div>
		</div>
	</div>
</div>


<form action="/admin/notice/list" class="actionForm">
	<input type="hidden" name="page" value="${pageDTO.page }"> 
	<input type="hidden" name="perSheet" value="${pageDTO.perSheet }"> 
	<input type="hidden" name="type" value="${pageDTO.type }"> 
	<input type="hidden" name="keyword" value="${pageDTO.keyword }">
</form>


<script src="/admin/resources/service.js"></script>
<script>
	const actionForm = document.querySelector(".actionForm")

	document.querySelector(".modifyBtn").addEventListener("click", function(e) {

	e.preventDefault();
		
	 $("#modifyModal").modal("show")

	}, false)
	
	

	
	const arr = []
	
	document.querySelector(".modalModifyBtn").addEventListener("click", function(e) {
		
		const title = document.querySelector("input[name='title']").value
		
		const category = document.querySelector("input[name='category']").value
		
		const writer = document.querySelector("input[name='writer']").value
		
		const content = document.querySelector("textarea[name='content']").value
		
		const obj = {nno:${notice.nno}, title:title, category:category, writer:writer, content:content /*, list:arr */}
		
		service.modify(obj).then(result => document.querySelector(".checkModalBody").innerHTML += "<h3>"+result+"</h3>")
		
		$("#checkModal").modal("show")
		
	}, false)
	
	
	
	document.querySelector(".checkBtn").addEventListener("click", function(e){
		
		location.href="/admin/notice/list"
		
	},false)
	
	
	const fileUl = document.querySelector(".fileUl")
	
	document.querySelector("input[name='files']").addEventListener("change", function(e){
		
		e.preventDefault()
		
		const formdata = new FormData()
		
		const files = document.querySelector("input[name='files']").files
		
		for(var i = 0; i < files.length ; i++){
			
			formdata.append("uploadFile", files[i])
			
		}
		
		service.upload(formdata).then(jsonObj => 
		
		 { console.log(jsonObj)
			for(var i = 0 ; i< jsonObj.length; i++){
			
			var file = jsonObj[i];
			
			arr.push(file)
			
			console.log("arr: "  + arr)
			
			if(!file.image){
				
					console.log(file.link)			
					fileUl.innerHTML += "<li id='li"+file.uuid+"'><a href='/admin/common/notice/download?link="+file.link+"'><i class='fas fa-file'></i></a>"+file.fileName+"<button onclick='delTempImg(event, JSON.stringify("+file+"))'>삭제</button></li>" 
			
			}else{
			fileUl.innerHTML += "<li id='li"+file.uuid+"'>"+file.fileName+"<img src='/admin/common/notice/view?link="+file.thumbLink+"'/><button onclick='delTempImg(event, "+JSON.stringify(file)+")'>삭제</button></li>"

			}	
		}})
		
	}, false)
	
	document.querySelector(".listBtn").addEventListener("click", function(e){
		
		e.preventDefault();
		
		actionForm.submit();
		
	},false)
	
	
		function delTempImg(event, param){
		
		console.log(event)
			
		event.preventDefault()
		
		console.log(param)
		
	 	service.fileDelete(param).then(res => console.log(res))
	 	
	 	fileUl.querySelector("#li"+param.uuid).remove();

	} 
	

	
/* 	const fileDelBtn = document.querySelector(".fileDelBtn")
	
	fileDelBtn.addEventListener("click", function(e){
		
		e.preventDefault();
		
		
		
	},false) */
	
	
	fileUl.addEventListener("click", function(){
		
	
		
		
		
	},false)
	
	
</script>

<%@ include file="../includes/footer.jsp"%>