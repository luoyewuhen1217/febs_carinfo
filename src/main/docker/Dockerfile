FROM java:8
VOLUME /tmp
MAINTAINER "qiweb"<908701702@qq.com>
ADD *.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
RUN echo "qiweb 2018 Hello docker!"
