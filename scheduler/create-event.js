const https = require('https')

exports.handler = async (event) => {
    return new Promise((resolve, reject) => {
        // Get next Sunday's date & time
        var nextSundayDate = new Date()
        nextSundayDate.setHours(nextSundayDate.getHours() + 10) // because service starts at 10AM
        nextSundayDate.setDate(nextSundayDate.getDate() + 2)

        // Build request data
        const requestBody = {
            "name": "Sunday Service - " + nextSundayDate.getFullYear() + "/" + (nextSundayDate.getMonth() + 1) + "/" + nextSundayDate.getDate(),
            "eventDateTime": nextSundayDate.getTime()
        }
        const data = JSON.stringify(requestBody)

        // Build request
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': data.length
            }
        }

        const req = https.request(
            "https://api.ffaurora.org/api/v1/event",
            options,
            res => {
                let responseBody = ''
                res.on('data', function (chunk) {
                    responseBody += chunk;
                });

                res.on('end', function () {
                    resolve(responseBody);
                });
            }
        )

        // Identify what to do on error
        req.on('error', error => {
            reject(error)
        })

        // Send request data
        req.write(data)
        req.end()
    })
};