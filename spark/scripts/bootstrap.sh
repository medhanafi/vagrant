# update apt
sudo apt-get update

# install java
 sudo apt-get install openjdk-8-jdk-headless -y

# get spark 2.4.3 from shared directory and unzip
#sudo wget http://miroir.univ-lorraine.fr/apache/spark/spark-2.4.3/spark-2.4.3-bin-hadoop2.7.tgz
sudo tar -zxf /vagrant/resources/spark-2.4.3-bin-hadoop2.7.tgz

# fix permission
sudo chown -R vagrant spark-2.4.3-bin-hadoop2.7
sudo chgrp -R vagrant spark-2.4.3-bin-hadoop2.7


# log settings
sudo cp /vagrant/resources/log4j.properties /home/vagrant/spark-2.4.3-bin-hadoop2.7/conf

sudo chown -R vagrant:vagrant spark-2.4.3-bin-hadoop2.7/conf/log4j.properties

# spark environment
echo "#!/usr/bin/env bash
SPARK_MASTER_IP=192.168.33.30
SPARK_LOCAL_IP=192.168.33.3${1}
" > /home/vagrant/spark-2.4.3-bin-hadoop2.7/conf/spark-env.sh

sudo chown -R vagrant:vagrant /home/vagrant/spark-2.4.3-bin-hadoop2.7/conf/spark-env.sh

# slaves file
rm -rf /home/vagrant/spark-2.4.3-bin-hadoop2.7/conf/slaves
for i in `seq 2 $2`;
do
	echo "192.168.33.3${i}" >> /home/vagrant/spark-2.4.3-bin-hadoop2.7/conf/slaves
done

sudo chown -R vagrant:vagrant /home/vagrant/spark-2.4.3-bin-hadoop2.7/conf/slaves


# copy ssh config
cp /vagrant/resources/config /home/vagrant/.ssh
sudo chown -R vagrant:vagrant /home/vagrant/.ssh/config

# passwordless ssh to slaves
# private key is only required in master
if [ $1 -eq "1" ]; then
	cp /vagrant/resources/id_dsa /home/vagrant/.ssh
	sudo chown vagrant:vagrant /home/vagrant/.ssh/id_dsa
	sudo chmod 600 /home/vagrant/.ssh/id_dsa
fi
# public key on all slaves
cat /vagrant/resources/id_dsa.pub >> /home/vagrant/.ssh/authorized_keys


echo "set nocompatible" > /home/vagrant/.vimrc
sudo chown vagrant:vagrant /home/vagrant/.vimrc
