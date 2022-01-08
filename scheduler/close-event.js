const https = require('https')

exports.handler = async (event) => {
    return new Promise((resolve, reject) => {
        // Build request
        const getOptions = { method: 'GET' }
        const getRequest = https.request(
            "https://api.ffaurora.org/api/v1/event/current",
            getOptions,
            getResponse => {
                let currentEvent = ''
                getResponse.on('data', function (chunk) {
                    currentEvent += chunk;
                });

                getResponse.on('end', function () {
                    const putOptions = { method: 'PUT' }
                    const putRequest = https.request(
                        `https://api.ffaurora.org/api/v1/event?id=${JSON.parse(currentEvent).id}`,
                        putOptions,
                        putResponse => {
                            let responseBody = ''
                            putResponse.on('data', function (chunk) {
                                responseBody += chunk;
                            });

                            putResponse.on('end', function () {
                                resolve(responseBody);
                            });
                        }
                    )

                    // Identify what to do on error
                    putRequest.on('error', error => {
                        reject(error)
                    })

                    // Send request data
                    putRequest.end()
                });
            }
        )

        // Identify what to do on error
        getRequest.on('error', error => {
            reject(error)
        })

        // Send request data
        getRequest.end()
    })
};