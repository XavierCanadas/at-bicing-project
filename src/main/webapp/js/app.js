/**
 * API CONFIGURATION
 */

// We use a relative path. 
// "api" corresponds to the @ApplicationPath("/api") in HelloApplication.java
// "stations" corresponds to the @Path("/stations") in StationsResource.java
const API_BASE_URL = "api"; 

/**
 * Main Function: Load Stations
 */
function loadStations() {
    const outputDiv = document.getElementById('stations-output');
    
    if (!outputDiv) {
        console.error("Error: Div 'stations-output' not found.");
        return;
    }

    outputDiv.innerHTML = "<p>Loading data...</p>";
    console.log(`Requesting: ${API_BASE_URL}/stations`);

    fetch(`${API_BASE_URL}/stations`)
        .then(response => {
            if (!response.ok) {
                // If it fails, throw error with status and URL
                throw new Error(`Error ${response.status} connecting to: ${response.url}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("Data received:", data);

            if (!data || data.length === 0) {
                outputDiv.innerHTML = "<p>No stations found.</p>";
                return;
            }

            let html = `
                <p><strong>Total:</strong> ${data.length} stations found.</p>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Status</th>
                            <th>Bikes</th>
                            <th>Docks</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            data.forEach(station => {
                html += `
                    <tr>
                        <td>${station.station_id}</td>
                        <td>${station.status}</td>
                        <td style="font-weight:bold">${station.num_bikes_available}</td>
                        <td>${station.num_docks_available}</td>
                    </tr>
                `;
            });

            html += `</tbody></table>`;
            outputDiv.innerHTML = html;
        })
        .catch(error => {
            console.error('Error:', error);
            outputDiv.innerHTML = `
                <div class="error">
                    <p>❌ Connection Error.</p>
                    <p><small>${error.message}</small></p>
                </div>
            `;
        });
}