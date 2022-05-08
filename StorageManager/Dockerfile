FROM eclipse-temurin:17-alpine
ARG STORAGE_MANAGER_VERSION=1.0

ADD artifacts/StorageManager-$STORAGE_MANAGER_VERSION.jar StorageManager.jar
RUN echo $STORAGE_MANAGER_VERSION > VERSION.txt
EXPOSE 8080

CMD java -jar StorageManager.jar
