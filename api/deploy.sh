docker stop churchqr-api || true && docker rm churchqr-api || true
docker build -t acelouie/churchqr-api:latest .
docker run --name churchqr-api -d -p 9001:9001 acelouie/churchqr-api:latest