const newUrl = "<a href=\"URL\" class=\"list-group-item list-group-item-action active\" target=\"_blank\">URL</a>"

function shortenUrl() {
    let originalUrl = document.getElementById('originalUrl');
    if (!!originalUrl.value) {
        document.getElementById('alert').style.display = 'none';
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
                    let innerHTML = document.getElementById('shortenUrls').innerHTML.replace('active', '');
                    document.getElementById('shortenUrls').innerHTML = newUrl.replace(/URL/g, response.shortenUrl) + innerHTML;
                }
        });
    } else {
        document.getElementById('alert').style.display = 'inherit';
    }
}
