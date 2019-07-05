#!/usr/bin/env bash

sudo apt-get update && sudo apt-get -y upgrade
sudo apt-get install -y wget vim
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
echo "deb http://apt.postgresql.org/pub/repos/apt/ bionic-pgdg main" | sudo tee  /etc/apt/sources.list.d/pgdg.list
sudo apt-get update
sudo apt-get -y install postgresql-11 postgresql-contrib-11

sudo sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" "/etc/postgresql/11/main/postgresql.conf"

sudo sed -i "s/host    all             all             127.0.0.1\/32            md5/host    all             all             all                     md5/" "/etc/postgresql/11/main/pg_hba.conf"

sudo systemctl restart postgresql
sudo ufw allow 5432/tcp

cat << EOF | su - postgres -c psql
alter user postgres with password 'postgres';

EOF

sudo sed -i "s/local   all             postgres                                peer/local   all             postgres                                md5/" "/etc/postgresql/11/main/pg_hba.conf"
sudo sed -i "s/local   all             all                                     peer/local   all             all                                md5/" "/etc/postgresql/11/main/pg_hba.conf"


