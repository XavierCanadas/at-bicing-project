/**
 * Notifier Module
 */
App.Notifier = App.Notifier || {};


$(document).on('submit', '#notifier-form', function (event) {
    App.Notifier.submit(event);
});

$(document).on('click', '#btn-air', function (event) {
    App.Notifier.submitAir(event);
});

App.Notifier.submit = function (event) {
    event.preventDefault();

    const $feedback = $('#notifier-feedback');
    const $btn = $('#btn-bicing');

    const phone = $('#notify-phone').val().trim();

    if (!phone || !phone.match(/^\+?[0-9]{7,15}$/)) {
        $feedback.html(`
            <div class="w3-panel w3-red w3-round">
                <p><i class="fa fa-times"></i> Phone number must be 7 to 15 digits without spaces, optional '+' at the beginning.</p>
            </div>`);
        return;
    }

    $feedback.html('<p><i class="fa fa-spinner fa-spin"></i> Sending notification...</p>');
    $btn.prop('disabled', true);

    console.log(`GET ${API_BASE_URL}/notify/slots?phone=${phone}`);

    $.ajax({
        url: `${API_BASE_URL}/notify/slots`,
        method: 'GET',
        dataType: 'json',
        data: { phone: phone },
        success: function (response) {
            console.log('Notification sent:', response);
            $feedback.html(`
                <div class="w3-panel w3-green w3-round">
                    <p><i class="fa fa-check"></i> ${response.message}</p>
                </div>`);
            $('#notifier-form')[0].reset();
        },
        error: function (xhr, status, error) {
            console.error('Error:', status, error);
            const msg = xhr.responseJSON ? xhr.responseJSON.message : error;
            $feedback.html(`
                <div class="w3-panel w3-red w3-round">
                    <p><i class="fa fa-times"></i> Error: ${msg}</p>
                </div>`);
        },
        complete: function () {
            $btn.prop('disabled', false);
        }
    });
};

App.Notifier.submitAir = function (event) {
    event.preventDefault();

    const $feedback = $('#notifier-feedback');
    const $btn = $('#btn-air');

    const phone = $('#notify-phone').val().trim();

    if (!phone || !phone.match(/^\+?[0-9]{7,15}$/)) {
        $feedback.html(`
            <div class="w3-panel w3-red w3-round">
                <p><i class="fa fa-times"></i> Phone number must be 7 to 15 digits without spaces, optional '+' at the beginning.</p>
            </div>`);
        return;
    }

    $feedback.html('<p><i class="fa fa-spinner fa-spin"></i> Sending Air Quality notification...</p>');
    $btn.prop('disabled', true);

    console.log(`GET ${API_BASE_URL}/notify/air?phone=${phone}`);

    $.ajax({
        url: `${API_BASE_URL}/notify/air`,
        method: 'GET',
        dataType: 'json',
        data: { phone: phone },
        success: function (response) {
            console.log('Air Quality Notification sent:', response);
            $feedback.html(`
                <div class="w3-panel w3-green w3-round">
                    <p><i class="fa fa-check"></i> ${response.message}</p>
                </div>`);
        },
        error: function (xhr, status, error) {
            console.error('Error:', status, error);
            const msg = xhr.responseJSON ? xhr.responseJSON.message : error;
            $feedback.html(`
                <div class="w3-panel w3-red w3-round">
                    <p><i class="fa fa-times"></i> Error: ${msg}</p>
                </div>`);
        },
        complete: function () {
            $btn.prop('disabled', false);
        }
    });
};

