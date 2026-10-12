const mapSearch = document.getElementById('map-search');
const mapSearchInput = document.getElementById('map-search-input');
const mapFrame = document.getElementById('map-frame');

if (mapSearch && mapSearchInput && mapFrame) {
    mapSearch.addEventListener('submit', function (event) {
        event.preventDefault();
        const query = mapSearchInput.value.trim();
        if (!query) return;
        mapFrame.src = 'https://maps.google.com/maps?q=' + encodeURIComponent(query) + '&output=embed';
    });
}
