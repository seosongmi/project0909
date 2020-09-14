<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="board2Insert.do" method="post"
		enctype="multipart/form-data">
		<div>
			<label>제목</label> 
			<input type="text" name="sub" />
		</div>
		<div>
			<label>아이디</label> 
			<input type="text" name="id" />
		</div>
		<div>
			<label>내용</label>
			<textarea name="contents" ></textarea>
		</div>
		<div>
			<button>등록</button>
		</div>
	</form>
</body>
</html>