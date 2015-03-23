git clone https://github.com/mkurnikov/pizza.git
sudo apt-get install git postgresql
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java7-installer
sudo apt-get install oracle-java7-set-default
wget http://mirror.tcpdiag.net/apache/tomcat/tomcat-7/v7.0.59/bin/apache-tomcat-7.0.59.tar.gz
tar xvf apache-tomcat-7.0.59.tar.gz
sh apache-tomcat-7.0.59//bin/startup.sh
cd pizza/
cp tomcat-conf/* ../apache-tomcat-7.0.59/conf/
sudo -u postgres psql -f src/main/resources/database/db.sql
sudo -u postgres psql -f src/main/resources/database/schema.sql
sudo -u postgres psql -f src/main/resources/database/fixture.sql
