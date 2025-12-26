FROM eclipse-temurin:21-jre

# 로그 디렉토리 생성
RUN mkdir -p /logs

# 타임존 고정
ENV TZ=UTC

# 빌드된 jar 복사
COPY ./build/libs/*SNAPSHOT.jar project.jar

# JVM 메모리 제한 + 실행
ENTRYPOINT ["java", "-Duser.timezone=UTC", "-Xms256m", "-Xmx512m", "-jar", "project.jar"]
