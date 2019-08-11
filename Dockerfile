# Extend vert.x image
FROM vertx/vertx3

ENV VERTICLE_NAME subhasys.wds.verticles.WorkDistributionVerticle
ENV VERTICLE_FILE target/work-distribution-api-1.0-SNAPSHOT.jar

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 9000

# Copy verticle to the container
COPY $VERTICLE_FILE $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]