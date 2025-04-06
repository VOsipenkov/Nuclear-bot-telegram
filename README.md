# Nuclear-bot-telegram
# Telegram surface
# actual version: 0.2.7

docker login
./gradlew build

# Deploy to k8s
docker build -f dockerfile-k8s -t vladi15151/nuclear-bot-telegram:0.3.7 .
# Deploy to render
docker build -f Dockerfile-render -t vladi15151/nuclear-bot-telegram:0.3.7 .

docker push vladi15151/nuclear-bot-telegram:0.3.7

