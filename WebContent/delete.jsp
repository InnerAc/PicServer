<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<h1>File Download </h1>
	<form action="DeleteFileServlet" method="POST" >
		<div align="left">
			<pre>
请输入要删除的hdfs文件路径：<input type="text" size="40" name="hdfsPath"> </BR>
			<input type="submit" value="submit"> <input type="reset" value="reset"></pre>
		</div>
	</form>
</body>
</html>