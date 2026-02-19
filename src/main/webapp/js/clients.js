/**
 * Clients Module
 */
App.Clients = App.Clients || {};

App.Clients.loadAll = function () {
    const $output = $('#clients-output');

    $output.html('<p><i class="fa fa-spinner fa-spin"></i> Loading clients...</p>');
    console.log(`GET ${API_BASE_URL}/clients`);

    $.ajax({
        url: `${API_BASE_URL}/clients`,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            if (!data || data.length === 0) {
                $output.html('<p>No clients registered yet.</p>');
                return;
            }

            let rows = '';
            $.each(data, function (i, client) {
                const stations = client.stationIds && client.stationIds.length
                    ? client.stationIds.join(', ')
                    : '<em>All</em>';
                rows += `
                    <tr>
                        <td>${client.phoneNumber}</td>
                        <td>${client.telegramChatId}</td>
                        <td>${stations}</td>
                    </tr>`;
            });

            $output.html(`
                <p><strong>Total:</strong> ${data.length} client(s).</p>
                <table class="w3-table w3-striped w3-bordered w3-hoverable">
                    <thead>
                        <tr class="w3-theme">
                            <th>Phone</th>
                            <th>Chat ID</th>
                            <th>Stations</th>
                        </tr>
                    </thead>
                    <tbody>${rows}</tbody>
                </table>`);
        },
        error: function (xhr, status, error) {
            console.error('Error:', status, error);
            $output.html(`
                <div class="w3-panel w3-red w3-round">
                    <p><i class="fa fa-times"></i> ${xhr.status} – ${error}</p>
                </div>`);
        }
    });
};

App.Clients.submit = function (event) {
    event.preventDefault();

    const $feedback = $('#client-feedback');
    const $btn = $('#new-client-form button[type="submit"]');

    // Parse station IDs from comma-separated string to array of integers
    const stationsRaw = $('#stations_ids').val().trim();
    const stationsIds = stationsRaw
        ? stationsRaw.split(',').map(s => parseInt(s.trim(), 10)).filter(n => !isNaN(n))
        : [];

    const payload = {
        phone:          $('#phone').val().trim(),
        telegram_token: $('#telegram_token').val().trim(),
        chat_id:        parseInt($('#chat_id').val().trim(), 10),
        stations_ids:   stationsIds
    };

    $feedback.html('<p><i class="fa fa-spinner fa-spin"></i> Submitting...</p>');
    $btn.prop('disabled', true);

    console.log('POST api/clients', payload);

    $.ajax({
        url: `${API_BASE_URL}/clients`,
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(payload),
        success: function (response) {
            console.log('Client added:', response);
            $feedback.html(`
                <div class="w3-panel w3-green w3-round">
                    <p><i class="fa fa-check"></i> ${response.message}</p>
                </div>`);
            $('#new-client-form')[0].reset();
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

