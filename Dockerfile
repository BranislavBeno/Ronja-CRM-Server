FROM gradle:7.2.0-jdk16-hotspot AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
# create fat jar
RUN gradle build -x test

FROM adoptopenjdk/openjdk16:jre-16.0.1_9-alpine
# install dumb-init
RUN apk add dumb-init
RUN mkdir /app
# add specific non root user for running application
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
# copy jar from build stage
COPY --from=build /project/build/libs/ronja-server.jar /app/ronja-server.jar
WORKDIR /app
# change owner for jar directory
RUN chown -R javauser:javauser /app
# switch user
USER javauser
# run application, where dumb-init occupies PID 1 and takes care of all the PID special responsibilities
CMD "dumb-init" "java" "-jar" "ronja-server.jar"