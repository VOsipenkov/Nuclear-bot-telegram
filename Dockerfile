FROM openjdk:17
COPY ./build/libs/telegram-0.0.1-SNAPSHOT.jar ./telegram.jar
EXPOSE 8080
#RUN apt-get update
#RUN apt-get install -y gcc
#RUN apt-get install -y curl
ENTRYPOINT java $JAVA_OPTS -jar telegram.jar