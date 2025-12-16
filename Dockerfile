FROM eclipse-temurin:21-jre

# 로그 디렉토리 생성
RUN mkdir -p /logs

# 빌드된 jar 복사
COPY ./build/libs/*SNAPSHOT.jar project.jar

# JVM 메모리 제한 + 실행
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "project.jar"]
