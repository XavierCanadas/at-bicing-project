/**
 * API CONFIGURATION
 */

// We use a relative path.
// "api" corresponds to the @ApplicationPath("/api") in HelloApplication.java
// "stations" corresponds to the @Path("/stations") in StationsResource.java
const API_BASE_URL = "api";

/**
 * Stations Module
 */
App.Stations = App.Stations || {};

App.Stations.load = function () {
    const $output = $('#stations-list');

    $output.html('<p><i class="fa fa-spinner fa-spin"></i> Loading data...</p>');
    console.log(`Requesting: ${API_BASE_URL}/stations`);

    $.ajax({
        url: `${API_BASE_URL}/stations`,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log("Data received:", data);

            if (!data || data.length === 0) {
                $output.html('<p>No stations found.</p>');
                return;
            }

            let rows = '';
            $.each(data, function (i, station) {
                rows += `
                    <tr>
                        <td>${station.station_id}</td>
                        <td>${station.status}</td>
                        <td>${station.num_bikes_available}</td>
                        <td>${station.num_docks_available}</td>
                        <td>${station.is_charging_station ? 'true' : 'false'}</td>
                    </tr>`;
            });

            const html = `
                <p><strong>Total:</strong> ${data.length} stations found.</p>
                <table class="w3-table w3-striped w3-bordered w3-hoverable">
                    <thead>
                        <tr class="w3-theme">
                            <th>ID</th>
                            <th>Status</th>
                            <th>Bikes</th>
                            <th>Docks</th>
                            <th>Charging</th>
                        </tr>
                    </thead>
                    <tbody>${rows}</tbody>
                </table>`;

            $output.html(html);
        },
        error: function (xhr, status, error) {
            console.error('Error:', status, error);
            $output.html(`
                <div class="w3-panel w3-red w3-round">
                    <h3>Connection Error</h3>
                    <p>${xhr.status} – ${error}</p>
                </div>`);
        }
    });
};
