<%@page import="com.aloha.spring.dto.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>/model/person</title>
</head>
<body>
	
	<%
		Person test = new Person();
		test.setName("이세진");
		test.setAge(20);
		pageContext.setAttribute("test", test);
	%>

	<h1>이름 : ${ person.name }</h1>
	<h1>나이 : ${ person.age }</h1>
	
	<h1>이름 : ${ test.name }</h1>
	<h1>나이 : ${ test.age }</h1>
	
	<h1>이름 : <%= test.getName() %></h1>
	<h1>나이 : <%= test.getAge() %></h1>
	
	<hr>
	
	<h3>[POST] - /model/person</h3>
	<form action="${ pageContext.request.contextPath }/model/person" method="post">
		<input type="text" name="name" value="${ person.name }" /> <br>
		<input type="text" name="age" value="${ person.age }" />   <br>
		<input type="submit" value="등록" />
	</form>
	
	<hr>
	
	<h3>[POST] - /model/person2</h3>
	<form action="${ pageContext.request.contextPath }/model/person2" method="post">
		<input type="text" name="name" value="${ person.name }" /> <br>
		<input type="text" name="age" value="${ person.age }" />   <br>
		<input type="submit" value="등록" />
	</form>
	
	<hr>
	
	<div style="height:500px;"></div>
</body>
</html>













