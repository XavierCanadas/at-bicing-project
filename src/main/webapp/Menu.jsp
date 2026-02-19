<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="w3-bar w3-theme w3-top w3-large w3-padding">

    <span class="w3-bar-item w3-hide-small w3-right">
        <i class="fa fa-bicycle"></i> BAN Project
    </span>

    <a href="#" class="w3-bar-item w3-button w3-hover-white" onclick="App.loadPage('Stations'); return false;">
        <i class="fa fa-map-marker"></i> Stations
    </a>

    <a href="#" class="w3-bar-item w3-button w3-hover-white" onclick="App.loadPage('Clients'); return false;">
        <i class="fa fa-users"></i> Clients
    </a>

    <a href="#" class="w3-bar-item w3-button w3-hover-white" onclick="App.loadPage('Notifier'); return false;">
        <i class="fa fa-bell"></i> Notifier
    </a>

</nav>

