<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.vdk.lab2demo.bean.Hits" %>
<%@ page import="com.vdk.lab2demo.bean.HitResult" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="hits" scope="session" class="com.vdk.lab2demo.bean.Hits"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="style.css">
    <style type="text/css">body { cursor: url('data:image/x-icon;base64,AAACAAEAICAQAAAAAADoAgAAFgAAACgAAAAgAAAAQAAAAAEABAAAAAAAAAIAAAAAAAAAAAAAEAAAAAAAAAAAAAAAM5b/AEDc/wBmZmYAgoF/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADMwQAAAAAAAAAAAAAAAAAMwM0QAAAAAAAAAAAAAAAADAANEQAAAAAAAAAAAAAAAAzAzREQAAAAAAAAAAAAAAAAzNEREAAAAAAAAAAAAAAAEREREQAAAAAAAAAAAAAAAQRREJEAAAAAAAAAAAAAARBEURCQAAAAAAAAAAAAABEERQRREAAAAAAAAAAAAAEQRFBEUQAAAAAAAAAAAADM0QUERQAAAAAAAAAAAAAMwAEQRFAAAAAAAAAAAAAAzAAA0QUQAAAAAAAAAAAAAMAADAERAAAAAAAAAAAAAADAAMAA0AAAAAAAAAAAAAAAAAwAAMAAAAAAAAAAAAAAAADAAAzAAAAAAAAAAAAAAAAAAADMAAAAAAAAAAAAAAAAAADMwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD///////////////////////////////////////////////////P////j////x///4g///8Af///AH///wB///+Af///AP///gD///gB///wAf//4AP//4AP//84H//+eB///vY///7uf///3v///bz///z5///8Y////D////w=='), auto; }</style>
    <title>VxDxK web 2</title>
</head>

<body>
    <header>
        <h1 class="nameHeader">Vadim Ponomarev P32311</h1>
        <h1 class="variantHeader">Variant: 3015</h1>
    </header>
    <div class="frame">
        <img src="areas-batman.png" alt="">
    </div>
    <form action="./controller" method="get">
            <div class="input">
                <input id="x-field" type="hidden" name="x" value="0">
                <div class="xandfield">
                    <span>X</span><br>
                    <input id="butt-0" type="button" value="-5">
                    <input id="butt-1" type="button" value="-4">
                    <input id="butt-2" type="button" value="-3">
                    <input id="butt-3" type="button" value="-2">
                    <input id="butt-4" type="button" value="-1">
                    <input id="butt-5" type="button" value="0" disabled>
                    <input id="butt-6" type="button" value="1">
                    <input id="butt-7" type="button" value="2">
                    <input id="butt-8" type="button" value="3">
                </div>
                <div class="yandfield">
                    <span>Y</span>
                    <input  class="yfield" id="y-field" onchange="fieldYValidator" type="text" name="y" value="0" size="3" required><br>
                </div>
                <span id="y-warning" class="warning"></span>
                <div class="rDiv">
                    <span>R</span>
                    <div class="checkboxes">
                        <input type="radio" id="r-1" name="r" value="1" checked>
                        <label for="r-1">1</label><br>

                        <input type="radio" id="r-2" name="r" value="2">
                        <label for="r-2">2</label><br>

                        <input type="radio" id="r-3" name="r" value="3">
                        <label for="r-3">3</label><br>

                        <input type="radio" id="r-4" name="r" value="4">
                        <label for="r-4">4</label><br>

                        <input type="radio" id="r-5" name="r" value="5">
                        <label for="r-5">5</label><br>
                    </div>

                </div>
                <div class="inputBlock">
                    <input id="submitButt" type="submit" value="Send">
                </div>
            </div>
    </form>
<%--    <% ((Hits)request.getSession().getAttribute("hits")).getHits().size();%>--%>
<%--    <div>${hits.hits.size()}</div>--%>
    <div>
        <table>
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Hit</th>
                <th>Time</th>
                <th>Processing time</th>
            </tr>

<%--            <c:out value="table"/>--%>
            <div>${table}</div>
<%--            <c:forEach var = "hit" items="${hits.hits}">--%>
<%--                <tr>--%>
<%--                    <td>${hit.x}</td>--%>
<%--                    <td>${hit.y}</td>--%>
<%--                    <td>${hit.r}</td>--%>
<%--                    <td>${hit.hit ? "<div class=\"hit\">Hit</div>" : "<div class=\"miss\">Miss</div>"}</td>--%>
<%--                    <td>${hit.time}</td>--%>
<%--                    <td>${hit.processingTime}</td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
        </table>
    </div>

    <%--    <a href="hello-servlet">Hello Servlet</a>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="script.js"></script>
</body>
</html>