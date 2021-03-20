docker stop churchqr-register || true && docker rm churchqr-register || true
docker build -t acelouie/churchqr-register:latest .
docker run --name churchqr-register -d -p 9003:80 acelouie/churchqr-register:latest