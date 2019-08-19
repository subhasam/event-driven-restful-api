# Extend vert.x image
#FROM vertx/vertx3

# Install JDK
FROM openjdk:8-jre-alpine

# Deploy API
ENV VERTICLE_NAME subhasys.wds.verticles.WorkDistributionVerticle
ENV VERTICLE_FILE work-distribution-api-1.0-SNAPSHOT-fat.jar

ENV VERTICLE_HOME /usr/verticles

EXPOSE 9090

COPY target/$VERTICLE_FILE $VERTICLE_HOME/                          
# COPY $VERTICLE_NAME $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -cp $VERTICLE_FILE subhasys.wds.verticles.WorkDistributionVerticle"]
# CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
# CMD ["exec java -jar $VERTICLE_FILE"]