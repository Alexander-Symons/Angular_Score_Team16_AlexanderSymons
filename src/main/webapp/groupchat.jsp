<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 20/03/2021
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="Overview" />
</jsp:include>
<body id="body">
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Overview" />
</jsp:include>
<p id="p"></p>




<form>
    <label>message:</label><input type="text" id="message"/>
    <input type="button" id="sendmessage" value="send" onclick="send()">
</form>



<script>
    document.getElementById("body").onload = openSocket;

    let webSocket;
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    let t = urlParams.get('groupname')
    document.getElementById("p").innerHTML = t;



    function openSocket(){
        webSocket = new WebSocket("ws://localhost:8080/groupschat");

        webSocket.onopen = function(event){
            writeResponse("Connection opened")
        };

        webSocket.onmessage = function(event){
            writeResponse(event.data);
        };

        webSocket.onclose = function(event){
            writeResponse("Connection closed");
        };
    }

    function closeSocket(){
        webSocket.close();
    }


    function send(){
        //let naam = sessionStorage.getItem("user");
        let comment = document.getElementById("message").value;
        console.log(comment);
        //TODO SEND JSON
        var msg = { chat: t, reply: comment };
        webSocket.send(JSON.stringify(msg));
    }
    function writeResponse(data){
        var msg = JSON.parse(data);
        console.log(msg);
        if(msg.chat === t){
            comment = document.getElementById("p")
            comment.innerHTML += "<br/>" + msg.reply
        }

        // comment = document.getElementById("p");
        // console.log(comment);
        // console.log(document.getElementById("reply").value = "")
        // console.log(document.getElementById("chat").value = "")
        // if(document.getElementById("chat") === t) {
        //     comment.innerHTML += "<br/>" + document.getElementById("reply");
        // }
        // comment.innerHTML += "<br/>" + text ;
    }
</script>
<script src="js/jquery-3.6.0.js"></script>
<script src="./js/effectsAlexanderSymons.js"></script>
</body>
</html>
