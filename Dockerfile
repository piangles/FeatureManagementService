FROM adoptopenjdk/openjdk8:x86_64-alpine-jre8u232-b09
WORKDIR /
ADD ./target/FeatureToggleService.jar FeatureToggleService.jar
ENTRYPOINT ["java", "-Dprocess.name=FeatureToggleService", "-jar", "FeatureToggleService.jar"]
