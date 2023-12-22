FROM ubuntu:22.04

ARG username
ARG userid

RUN apt-get update

RUN apt-get install --yes openjdk-21-jdk sudo unzip wget git

RUN mkdir --parents /opt/kotlin
RUN wget https://github.com/JetBrains/kotlin/releases/download/v1.9.21/kotlin-compiler-1.9.21.zip
RUN unzip kotlin-compiler-1.9.21.zip -d /opt/kotlin
RUN rm kotlin-compiler-1.9.21.zip

RUN mkdir --parents /opt/gradle
RUN wget https://services.gradle.org/distributions/gradle-8.5-bin.zip
RUN unzip gradle-8.5-bin.zip -d /opt/gradle
RUN rm gradle-8.5-bin.zip

RUN useradd --uid $userid --create-home --shell /bin/bash $username --groups sudo

WORKDIR /home/micah

USER micah

ENV PATH="${PATH}:/opt/gradle/gradle-8.5/bin"
ENV PATH="${PATH}:/opt/kotlin/kotlinc/bin"
