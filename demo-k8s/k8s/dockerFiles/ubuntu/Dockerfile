FROM ubuntu:latest

## Update the package list
#RUN apt-get update -y
## Install necessary packages
#RUN apt install curl -y
## Create a directory
#RUN mkdir /app

#RUN apt-get update && \
#    apt install curl -y && \
#    mkdir /app


#chmod +x setup.sh
# Copy the shell script into the image
COPY setup.sh /setup.sh
# Run the shell script
RUN /setup.sh