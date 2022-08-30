FROM openjdk:11
EXPOSE 8080
ADD target/explore_buddy.jar explore_buddy.jar
ENTRYPOINT ["java","-jar", "explore_buddy.jar"]