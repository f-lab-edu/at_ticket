FROM  ibmjava:11-jdk

# JAR_FILE 변수 정의
ARG RESERVATION_JAR_FILE=build/libs/reservation-*-SNAPSHOT.jar

# JAR 파일 메인 디렉토리에 복사
COPY ${RESERVATION_JAR_FILE} reservation-app.jar

# 시스템 진입점 정의
ENTRYPOINT [ "java","-jar","/reservation-app.jar" ]