<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="w3-container">

    <h2 class="w3-theme-l1 w3-padding">
        <i class="fa fa-user-plus"></i> Subscribe New Client
    </h2>

    <p>Fill in the form below to register a new client for Bicing notifications.</p>

    <div class="w3-card w3-padding w3-round">
        <form id="new-client-form" onsubmit="App.Clients.submit(event)">

            <!-- name -->
            <div class="w3-section">
                <label class="w3-text-theme"><b><i class="fa fa-name"></i> Name</b></label>
                <input class="w3-input w3-border w3-round" type="text" id="name"
                       name="name" placeholder="enter your name" required>
            </div>

            <!-- Phone -->
            <div class="w3-section">
                <label class="w3-text-theme"><b><i class="fa fa-phone"></i> Phone number</b></label>
                <input class="w3-input w3-border w3-round" type="tel" id="phone"
                       name="phone" placeholder="+34600000000" required>
                <span class="w3-small w3-text-grey">Required format: +34612345678 or 612345678 (7-15 digits without spaces, optional '+')</span>
            </div>

            <!-- Telegram Token -->
            <div class="w3-section">
                <label class="w3-text-theme"><b><i class="fa fa-telegram"></i> Telegram Bot Token</b></label>
                <input class="w3-input w3-border w3-round" type="text" id="telegram_token"
                       name="telegram_token" placeholder="token" required>
            </div>

            <!-- Chat ID -->
            <div class="w3-section">
                <label class="w3-text-theme"><b><i class="fa fa-comment"></i> Telegram Chat ID</b></label>
                <input class="w3-input w3-border w3-round" type="number" id="chat_id"
                       name="chat_id" placeholder="123456789" required>
            </div>

            <!-- Station IDs -->
            <div class="w3-section">
                <label class="w3-text-theme"><b><i class="fa fa-map-marker"></i> Station IDs</b></label>
                <input class="w3-input w3-border w3-round" type="text" id="stations_ids"
                       name="stations_ids" placeholder="1, 42, 87 (comma-separated)">
            </div>

            <!-- Submit -->
            <div class="w3-section">
                <button type="submit" class="w3-button w3-theme w3-hover-theme w3-round w3-block">
                    <i class="fa fa-paper-plane"></i> Subscribe
                </button>
            </div>

        </form>

        <!-- Feedback -->
        <div id="client-feedback"></div>
    </div>

</div>

