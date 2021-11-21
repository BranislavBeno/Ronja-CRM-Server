FROM gradle:7.3-jdk17-alpine AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
# create fat jar
RUN gradle build -x test
# create temporary env variable to reach out built the jar file
ARG JAR_FILE=build/libs/ronja-server.jar
# rename the jar file
COPY ${JAR_FILE} application.jar
# extrect layered jar file
RUN java -Djarmode=layertools -jar application.jar extract

FROM azul/zulu-openjdk-alpine:17-jre
# install dumb-init
RUN apk add dumb-init
RUN mkdir /app
# add specific non root user for running application
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
# set work directory
WORKDIR /app
# copy jar from build stage
COPY --from=build /project/dependencies/ ./
COPY --from=build /project/snapshot-dependencies/ ./
COPY --from=build /project/spring-boot-loader/ ./
COPY --from=build /project/application/ ./
# change owner for jar directory
RUN chown -R javauser:javauser /app
# switch user
USER javauser
# run application, where dumb-init occupies PID 1 and takes care of all the PID special responsibilities
ENTRYPOINT ["dumb-init", "java", "org.springframework.boot.loader.JarLauncher"]