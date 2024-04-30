# Nuclear-bot-telegram
# Telegram surface
# actual version: 0.2.4

docker login
./gradlew build

# Deploy to k8s
docker build -f Dockerfile-k8s -t vladi15151/nuclear-bot-telegram:0.2.4 .
# Deploy to render
docker build -f Dockerfile-render -t vladi15151/nuclear-bot-telegram:0.2.4 .

docker push vladi15151/nuclear-bot-telegram:0.2.4

