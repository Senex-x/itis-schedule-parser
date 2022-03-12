<#import "spring.ftl" as spring />

<!DOCTYPE html>
<html lang="ru" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8"/>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="<@spring.url "/css/style.css"/>"/>
    <script type="text/javascript" src="<@spring.url "js/jquery.min.js"/>"></script>
    <script type="text/javascript" src="<@spring.url "js/script.js"/>"></script>
</head>

<body>
<button id="button">Get group</button>
</body>
</html>