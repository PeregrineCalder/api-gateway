FROM openjdk:17-jre-slim
LABEL authors="peregrine"
ENV PARAMS=""
ADD target/api-gateway-engine-*.jar /api-gateway-engine.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /api-gateway-engine.jar $PARAMS"]
