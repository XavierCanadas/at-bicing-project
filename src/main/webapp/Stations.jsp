<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="w3-container">

    <h2 class="w3-theme-l1 w3-padding">
        <i class="fa fa-map-marker"></i> Station List
    </h2>

    <p>Connect with backend to retrieve the current state of Bicing stations.</p>

    <button class="w3-button w3-theme w3-hover-theme w3-round" onclick="App.Stations.load()">
        <i class="fa fa-refresh"></i> Load Stations
    </button>

    <div id="stations-list" class="w3-margin-top"></div>

</div>

