<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<body>
	<h1>File Upload </h1>
	<form action="WritePano" method="POST" enctype="multipart/form-data">
			
		<input name="uid" value="admin"/> 
		<div align="left">
			<pre>浏览:<input type="file" size="40" name="upl-file"> </BR>
			<input type="submit" value="submit"> <input type="reset" value="reset"></pre>
		</div>

	</form>
</body>
</html>