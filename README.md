# Nuclear-bot-telegram
# Telegram surface
# actual version: 0.1.6

docker login
./gradlew build

# Deploy to k8s
docker build -f Dockerfile-k8s -t vladi15151/nuclear-bot-telegram:0.1.6 .
# Deploy to render
docker build -f Dockerfile-render -t vladi15151/nuclear-bot-telegram:0.1.6 .

docker push vladi15151/nuclear-bot-telegram:0.1.5

