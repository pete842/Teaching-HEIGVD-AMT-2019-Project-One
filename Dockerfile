FROM jboss/wildfly:15.0.0.Final

# Maintainer
MAINTAINER Christian Metz <christian@metzweb.net>

# Appserver
ENV WILDFLY_USER admin
ENV WILDFLY_PASS admin

# Database
ENV DB_NAME libmovie
ENV DB_USER mysql
ENV DB_PASS mysql
ENV DB_URI db:3306

ENV MYSQL_VERSION 2.5.1
ENV JBOSS_CLI /opt/jboss/wildfly/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR /opt/jboss/wildfly/standalone/deployments/
#ENV JAVA_OPTS


# Setting up WildFly Admin Console
RUN echo => "Adding WildFly administrator"
RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

# Configure Wildfly server
RUN echo "=> Starting WildFly server" && \
      bash -c '$JBOSS_HOME/bin/standalone.sh &' && \
    echo "=> Waiting for the server to boot" && \
      bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' && \
    echo "=> Downloading MySQL driver" && \
      curl --location --output /tmp/connector-java-${MYSQL_VERSION}.jar --url https://downloads.mariadb.com/Connectors/java/connector-java-${MYSQL_VERSION}/mariadb-java-client-${MYSQL_VERSION}.jar && \
    echo "=> Adding MySQL module" && \
      $JBOSS_CLI --connect --command="module add --name=org.mariadb.jdbc --resources=/tmp/connector-java-${MYSQL_VERSION}.jar --dependencies=javax.api,javax.transaction.api" && \
    echo "=> Adding MySQL driver" && \
                                     #/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql.driver,driver-class-name=com.mysql.jdbc.Driver)
      $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=org.mariadb.jdbc.Driver:add(driver-name=org.mariadb.jdbc.Driver,driver-module-name=org.mariadb.jdbc)" && \
     #$JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=org.mariadb.jdbc.Driver:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)" && \
    echo "=> Creating a new datasource" && \
      $JBOSS_CLI --connect --command="data-source add \
        --name=${DB_NAME} \
        --jndi-name=java:/jdbc/${DB_NAME} \
        --user-name=${DB_USER} \
        --password=${DB_PASS} \
        --driver-name=org.mariadb.jdbc.Driver \
        --connection-url=jdbc:mariadb://${DB_URI}/${DB_NAME} \
        --use-ccm=false \
        --max-pool-size=25 \
        --blocking-timeout-wait-millis=5000 \
        --enabled=true" && \
    echo "=> Shutting down WildFly and Cleaning up" && \
      $JBOSS_CLI --connect --command=":shutdown" && \
      rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
      rm -f /tmp/*.jar

# Add project to deployments folder
ADD projectone.war /opt/jboss/wildfly/standalone/deployments/

# Expose http and admin ports
EXPOSE 8080 9990 8787

#echo "=> Restarting WildFly"
# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interfaces
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug", "*:8787"]