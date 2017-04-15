<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pattern Identification</title>
</head>
<body>

	<form align="center" action="CreateMatrix" action="post">
		<table>
			<tr>
				<td><input type="radio" name="matrix" value="coverage" checked>
					Pattern Coverage<br> <input type="radio" name="matrix"
					value="overlapping"> Pattern Overlapping<br></td>
			</tr>

			<tr>
				<td><input type="submit" value="submit"></td>
			</tr>
		</table>

	</form>

	<%
		if (request.getAttribute("matrix") != null) {
	%>
	<table align="center" border="1">
		<h1 align="center">Pattern Coverage Matrix</h1>
		<tr>
		<td>
		
		</td> 
		<td>Composite
		</td>
		<td>Observer
		</td>
		<td>Strategy
		</td>
		
		</tr>
		<%
		String ans[][] = (String[][]) request.getAttribute("matrix");

			for (int i = 0; i < 7; i++) {
	%>
		<tr>
			<%
				for (int j = 0; j < 4; j++) {
			%>
			
			<td>
			<%=ans[i][j] %>
			</td>
			
			<%
				}
			%>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>
	<%
		if (request.getAttribute("overlappingmatrix") != null) {
	%>
	<table align="center" border="1">
		<h1 align="center">Pattern Overlapping Matrix</h1>
		<%
		String ans[][] = (String[][]) request.getAttribute("overlappingmatrix");

			for (int i = 0; i < 4; i++) {
	%>
		<tr>
			<%
				for (int j = 0; j < 4; j++) {
			%>
			
			<td>
			<%if(ans[i][j]!=null){ %>
			<%=ans[i][j] %>
			<%} %>
			</td>
			
			<%
				}
			%>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>
</body>
</html>