# Nuclear-bot-telegram
# Telegram surface
# actual version: 0.2.7

docker login
./gradlew build

# Deploy to k8s
docker build -f Dockerfile-k8s -t vladi15151/nuclear-bot-telegram:0.2.9 .
# Deploy to render
docker build -f Dockerfile-render -t vladi15151/nuclear-bot-telegram:0.2.9 .

docker push vladi15151/nuclear-bot-telegram:0.2.9

