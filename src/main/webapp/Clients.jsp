<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="w3-container">

    <h2 class="w3-theme-l1 w3-padding">
        <i class="fa fa-users"></i> Clients
    </h2>

    <p>List of all subscribed clients.</p>

    <button class="w3-button w3-theme w3-hover-theme w3-round" onclick="App.Clients.loadAll()">
        <i class="fa fa-refresh"></i> Load Clients
    </button>

    <div id="clients-output" class="w3-margin-top"></div>

</div>

