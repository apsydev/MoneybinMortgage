FROM openjdk:8-jdk
EXPOSE 8081
WORKDIR /opt
ADD ./loans.csv /opt/loans.csv
CMD java -Xmx512m -server -jar ./moneybin-mortgage-web/target/moneybin-mortgage-web-0.0.1-SNAPSHOT.jar