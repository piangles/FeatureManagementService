FROM eclipse-temurin:17-jre-alpine
WORKDIR /
ADD ./target/FeatureManagementService.jar FeatureManagementService.jar
ENTRYPOINT ["java", "-Dprocess.name=FeatureManagementService", "-jar", "FeatureManagementService.jar"]
