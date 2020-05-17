const newElement = '<div class="list-group-item list-group-item-action d-flex"><div class="original-url">ORIGINALURL</div><div class="shorten-url"><a href="URL"target="_blank"class="">URL</a></div></div>'

function generateNewElement(originalUrl, shortenUrl) {
    return newElement.replace(/ORIGINALURL/g, originalUrl).replace(/URL/g, shortenUrl);
}

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
                    document.getElementById('shortenUrls').innerHTML = generateNewElement(originalUrl.value, response.shortenUrl) + innerHTML;
                }
        });
    } else {
        document.getElementById('alert').style.display = '';
    }
}
