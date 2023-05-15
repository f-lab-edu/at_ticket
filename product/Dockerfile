FROM eclipse-temurin:11

# JAR_FILE 변수 정의
ARG PRODUCT_JAR_FILE=build/libs/product-*-SNAPSHOT.jar

# JAR 파일 메인 디렉토리에 복사
COPY ${PRODUCT_JAR_FILE} product-app.jar

# 시스템 진입점 정의
ENTRYPOINT [ "java","-jar","/product-app.jar" ]
