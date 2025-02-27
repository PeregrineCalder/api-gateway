FROM openjdk:17-jre-slim
LABEL authors="peregrine"
ENV PARAMS=""
ADD target/api-gateway-center-*.jar /api-gateway-center.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /api-gateway-center.jar $PARAMS"]