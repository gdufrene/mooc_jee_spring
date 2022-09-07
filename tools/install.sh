wget https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.11%2B9/OpenJDK11U-jdk_x64_mac_hotspot_11.0.11_9.tar.gz
tar xvf OpenJDK11U-jdk_x64_mac_hotspot_11.0.11_9.tar.gz
ln -s jdk-11.0.11+9/ java
./java/Contents/Home/bin/java -version
export JAVA_HOME="$(pwd)/java/Contents/Home"

wget https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.zip
unzip apache-maven-3.8.6-bin.zip
ln -s apache-maven-3.8.6/ maven
./maven/bin/mvn -v

wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.65/bin/apache-tomcat-9.0.65.tar.gz
tar xvf apache-tomcat-9.0.65.tar.gz
ln -s apache-tomcat-9.0.65/ tomcat
./tomcat/bin/version.sh
