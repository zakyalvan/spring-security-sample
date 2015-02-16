<!doctype html>
<html>
	<head>
		<title>Login Page</title>
	</head>
	<body>
		<h1>Login Page</h1>
		<p>Use your username and password to login</p>
		<form action="/authenticate" method="post">
			<table>
				<tr>
					<td><label for="username">Username</label></td>
					<td><input type="text" name="username" id="username" placeholder="Username"></td>
				</tr>
				<tr>
					<td><label for="password">Password</label></td>
					<td><input type="password" name="password" id="password" placeholder="Password"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" name="password" id="password" value="Login"></td>
				</tr>
			</table>
		</form>
	</body>
</html>