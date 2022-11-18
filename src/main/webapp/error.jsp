<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error bro</title>
    <style type="text/css">body { cursor: url('data:image/x-icon;base64,AAACAAEAICAQAAAAAADoAgAAFgAAACgAAAAgAAAAQAAAAAEABAAAAAAAAAIAAAAAAAAAAAAAEAAAAAAAAAAAAAAAM5b/AEDc/wBmZmYAgoF/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADMwQAAAAAAAAAAAAAAAAAMwM0QAAAAAAAAAAAAAAAADAANEQAAAAAAAAAAAAAAAAzAzREQAAAAAAAAAAAAAAAAzNEREAAAAAAAAAAAAAAAEREREQAAAAAAAAAAAAAAAQRREJEAAAAAAAAAAAAAARBEURCQAAAAAAAAAAAAABEERQRREAAAAAAAAAAAAAEQRFBEUQAAAAAAAAAAAADM0QUERQAAAAAAAAAAAAAMwAEQRFAAAAAAAAAAAAAAzAAA0QUQAAAAAAAAAAAAAMAADAERAAAAAAAAAAAAAADAAMAA0AAAAAAAAAAAAAAAAAwAAMAAAAAAAAAAAAAAAADAAAzAAAAAAAAAAAAAAAAAAADMAAAAAAAAAAAAAAAAAADMwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD///////////////////////////////////////////////////P////j////x///4g///8Af///AH///wB///+Af///AP///gD///gB///wAf//4AP//4AP//84H//+eB///vY///7uf///3v///bz///z5///8Y////D////w=='), auto; }</style>
    <link rel="stylesheet" href="style.css">
    <style>
        *{
            background: #292929;
            color: white;
        }
    </style>
</head>
<body>
    <div style="text-align: center;">
        <h1>Hello bro, why did you break this shitty designed application?</h1>
        <h3>Your errors:</h3>
        <div style="padding: 20px; margin: 10px; outline: 2px solid white;">
            <%= request.getAttribute("error")%>
        </div>

        <div>
            <form method="get" action="./return">
                <div class="inputBlock">
                    <input id="submitButt" type="submit" value="Return to main page">
                </div>
            </form>
        </div>

    </div>
</body>
</html>
