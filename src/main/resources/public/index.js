const newUrl = "<a href=\"URL\" class=\"list-group-item list-group-item-action\">URL</a>"

function shortenUrl() {
    let originalUrl = document.getElementById('originalUrl');
    if (originalUrl) {
        let dto = {originalUrl: originalUrl.value};
        fetch('/shorten', {
            method: 'POST',
            body: JSON.stringify(dto),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(function (response) {
                if (response.shortenUrl) {
                    document.getElementById('shortenUrls').innerHTML = newUrl.replace(/URL/g, response.shortenUrl);
                }
        });
    }
}
