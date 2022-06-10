<#import "spring.ftl" as spring />

<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8"/>
    <title>Sign In</title>
    <link rel="stylesheet" type="text/css" href="<@spring.url "/css/style.css"/>"/>
    <script type="text/javascript" src="<@spring.url "js/jquery.min.js"/>"></script>
</head>
<body>
<form action="/login" method="post">
    <label>Nickname
        <input name="username" type="text"/>
    </label>
    <label>Password
        <input name="password" type="password"/>
    </label>
    <input type="submit"/>
</form>
</body>
</html>