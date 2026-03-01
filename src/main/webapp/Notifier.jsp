<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="w3-container">

    <h2 class="w3-theme-l1 w3-padding">
        <i class="fa fa-bell"></i> Send Notification
    </h2>

    <p>Enter your phone number to receive a Telegram notification with the available slots for your subscribed stations.</p>

    <div class="w3-card w3-padding w3-round">
        <form id="notifier-form">

            <!-- Phone -->
            <div class="w3-section">
                <label class="w3-text-theme"><b><i class="fa fa-phone"></i> Phone number</b></label>
                <input class="w3-input w3-border w3-round" type="tel" id="notify-phone"
                       name="phone" placeholder="+34612345678"
                       pattern="\+?[0-9]{7,15}" required>
                <span class="w3-small w3-text-grey">The phone number associated with your subscription.</span>
                <span class="w3-small w3-text-grey">Required format: +34612345678 or 612345678 (7-15 digits without spaces, optional '+')</span>

            </div>

            <!-- Submit -->
            <div class="w3-section">
                <button type="submit" class="w3-button w3-theme w3-hover-theme w3-round w3-block">
                    <i class="fa fa-paper-plane"></i> Send Notification
                </button>
            </div>

        </form>

        <!-- Feedback -->
        <div id="notifier-feedback"></div>
    </div>

</div>

