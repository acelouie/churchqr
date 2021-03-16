docker stop churchqr-scanner || true && docker rm churchqr-scanner || true
docker build -t acelouie/churchqr-scanner:latest .
docker run --name churchqr-scanner -d -p 9002:80 acelouie/churchqr-scanner:latest