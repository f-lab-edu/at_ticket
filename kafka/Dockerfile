FROM eclipse-temurin:11 as build

# 소스 복사
COPY .. /

RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM eclipse-temurin:11-jre

ARG MODULE_NAME=kafka

#jar 복사
#product/build/libs/product-1.0.0-SNAPSHOT.jar
COPY --from=build ${MODULE_NAME}/build/libs/${MODULE_NAME}-*-SNAPSHOT.jar  app.jar

#실행
ENTRYPOINT [ "java","-jar","/app.jar" ]
