FROM eclipse-temurin:17-alpine
ARG CHECKERS_CORE_VERSION=1.0

ADD artifacts/Checkers-$CHECKERS_CORE_VERSION.jar Checkers.jar
RUN echo $CHECKERS_CORE_VERSION > VERSION.txt
EXPOSE 8080

CMD java -jar Checkers.jar
