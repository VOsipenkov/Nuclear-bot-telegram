# Nuclear-bot-telegram
# Telegram surface

# Deploy to k8s
docker login
./gradlew build
docker build -f Dockerfile.k8s -t vladi15151/nuclear-bot-telegram:0.1.3 .
docker push vladi15151/nuclear-bot-telegram:0.1.3

# Deploy to render 
docker login
./gradlew build
docker build -f Dockerfile.render -t vladi15151/nuclear-bot-telegram:0.1.3 .
docker push vladi15151/nuclear-bot-telegram:0.1.3