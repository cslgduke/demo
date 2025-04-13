FROM nginx
COPY ./index.html /usr/share/nginx/html
COPY ./evil.jpg /usr/share/nginx/html
COPY ./docker-nginx.conf /etc/nginx/conf.d/nginx.conf
RUN echo "hello world"
LABEL maintainer="yourname@example.com"
LABEL version="1.0"
LABEL description="This is a sample Docker image"

ENV APP_PORT=8080
ENV DB_HOST=localhost

# ADD https://example.com/file.tar.gz /app/
VOLUME /data
# USER appuser
# WORKDIR /app
#ENTRYPOINT ["echo"]
#CMD ["default argument print by ENTRYPOINT"]

EXPOSE 8080