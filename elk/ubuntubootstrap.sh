#!/usr/bin/env bash

# update apt
sudo apt-get update

# install java
sudo apt-get install openjdk-8-jre-headless -y

# install elasticsearch
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.4.3.tar.gz
tar -xzf elasticsearch-6.4.3.tar.gz

# start elasticsearch on boot
#sudo update-rc.d elasticsearch defaults 95 10

# allow host OS to access through port forwarding
sudo echo "
network.bind_host: 0
network.host: 0.0.0.0" >> /home/vagrant/elasticsearch-6.4.3/config/elasticsearch.yml
sudo sed -i -e '$a\' /home/vagrant/elasticsearch-6.4.3/config/elasticsearch.yml
sudo sed -i -e '$a\' /home/vagrant/elasticsearch-6.4.3/config/elasticsearch.yml
sudo sed 's/-Xms1g/-Xms256m/g' /home/vagrant/elasticsearch-6.4.3/config/jvm.options
sudo sed 's/-Xmx1g/-Xmx256m/g' /home/vagrant/elasticsearch-6.4.3/config/jvm.options

#sudo vim /etc/sysctl.conf
#vm.max_map_count=262144

#Elastic yml config
#node.name: dsc-node-1
#cluster.name: dsc-cluster
#network.host: 0.0.0.0
#network.bind_host: 192.168.33.21
#transport.tcp.port: 9300

#JVM options
#sudo vim config/jvm.options
#-Xms512m
#-Xmx512m
