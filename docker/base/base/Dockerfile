FROM ubuntu

#FROM ubuntu:14.04

# Install spark standalone

RUN sudo apt-get -y update
RUN sudo apt-get -y install software-properties-common
RUN sudo add-apt-repository -y ppa:webupd8team/java
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | sudo debconf-set-selections



# Setting debconf to non interactive
RUN echo 'debconf debconf/frontend select Noninteractive' | debconf-set-selections
RUN echo "deb http://security.ubuntu.com/ubuntu trusty-security main" >>  /etc/apt/sources.list 

#stratio-ingestion
RUN apt-get -y update &&\
	apt-get install -yq --force-yes openssh-server vim nano python-software-properties debconf-utils oracle-java8-installer &&\
	apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*


#config
RUN echo "root:kschool" | sudo chpasswd
RUN sed -i "/PermitRootLogin without-password/s/^/#/" /etc/ssh/sshd_config 
RUN echo "PermitRootLogin yes" >>  /etc/ssh/sshd_config 

CMD service ssh restart && /bin/bash





