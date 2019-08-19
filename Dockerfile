# Extend vert.x image
# FROM vertx/vertx3

FROM openjdk:8-jre-alpine

ENV VERTICLE_NAME subhasys.wds.verticles.WorkDistributionVerticle
ENV VERTICLE_FILE work-distribution-api-1.0-SNAPSHOT-fat.jar

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 9090

# Copy verticle to the container
COPY target/$VERTICLE_FILE $VERTICLE_HOME/                          
# COPY $VERTICLE_NAME $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
# CMD ["exec java -cp $VERTICLE_FILE subhasys.wds.verticles.WorkDistributionVerticle"]
# CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
CMD ["exec java -jar $VERTICLE_FILE"]