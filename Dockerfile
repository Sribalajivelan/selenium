FROM selenium/standalone-chrome:131.0

COPY . /app
WORKDIR /app

USER root

# Install Maven
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz \
    && tar -xvzf apache-maven-3.9.9-bin.tar.gz \
    && rm apache-maven-3.9.9-bin.tar.gz \
    && mv apache-maven-3.9.9 /app/maven

ENV MAVEN_HOME="/app/maven"
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

# Install Java (JDK)
RUN wget https://download.oracle.com/java/23/latest/jdk-23_linux-x64_bin.deb \
    && dpkg -i jdk-23_linux-x64_bin.deb \
    && rm jdk-23_linux-x64_bin.deb

# Verify installations
RUN echo "JAVA_HOME=${JAVA_HOME}" && java -version
RUN echo "MAVEN_HOME=${MAVEN_HOME}" && mvn --version

# Build the application
RUN mvn clean install -DskipTests=true

# Add and set permissions for the shell script
COPY run_services.sh /app/run_services.sh
RUN chmod +x /app/run_services.sh

ENV SELENIUM_HUB_URL="http://localhost:4444/wd/hub"
ENV SE_ENABLE_TRACING=false

CMD ["/app/run_services.sh"]
