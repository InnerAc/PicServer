<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PicServer测试</title>
</head>
<body>
<h1>读取</h1>
<img src="http://localhost:8080/PicServer/ReadImage?image=43433.jpg">
<br/>
<h1>缩放</h1>
<br/>
<img src="http://localhost:8080/PicServer/ScaleImage?image=43433.jpg&width=100&height=100">
<br/>
<h1>裁剪</h1>
<br/>
<img src="http://localhost:8080/PicServer/CropImage?image=43433.jpg&width=100&height=100&offsetX=300&offsetY=400">
<br/>
<h1>水印（图片）</h1>
<br/>
<img src="http://localhost:8080/PicServer/WaterMask?type=image&image=43433.jpg&logo=1.jpg&width=100&height=100&offsetX=100&offsetY=200">
<br/>
<h1>水印（文字）</h1>
<br/>
<img src="http://localhost:8080/PicServer/WaterMask?type=text&image=43433.jpg&text=Jet-Muffin&fontsize=40&offsetX=100&offsetY=200">
</body>
</html>