#!/usr/bin/env bash

# update apt
sudo apt-get update

# install java
sudo apt-get install openjdk-8-jre-headless -y

# install elasticsearch
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.7.2.deb
sudo dpkg -i elasticsearch-6.7.2.deb

# start elasticsearch on boot
sudo update-rc.d elasticsearch defaults 95 10

# allow host OS to access through port forwarding
sudo echo "
network.bind_host: 0
network.host: 0.0.0.0" >> /etc/elasticsearch/elasticsearch.yml
sudo sed -i -e '$a\' /etc/elasticsearch/elasticsearch.yml
sudo sed -i -e '$a\' /etc/elasticsearch/elasticsearch.yml
