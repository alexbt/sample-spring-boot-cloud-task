#!/bin/bash

export dataflow_version=1.0.0.RC1
export examplespringbootcloudtask_version=1.0.0-SNAPSHOT

#rm -rf target
mkdir target
mkdir target/spring-data-flow
git clone https://github.com/alexturcot/example-spring-boot-cloud-task.git target

mvn -f target/example-spring-boot-cloud-task/pom.xml
wget -N http://repo.spring.io/milestone/org/springframework/cloud/spring-cloud-dataflow-server-local/${dataflow_version}/spring-cloud-dataflow-server-local-${dataflow_version}.jar target/spring-data-flow/
wget -N http://repo.spring.io/milestone/org/springframework/cloud/spring-cloud-dataflow-shell/${dataflow_version}/spring-cloud-dataflow-shell-${dataflow_version}.jar target/spring-data-flow/

java -jar target/spring-data-flow/spring-cloud-dataflow-server-local-${dataflow_version}.jar &
sleep 20
java -jar target/spring-data-flow/spring-cloud-dataflow-shell-${dataflow_version}.jar

app register --name mytask --type task --uri file:///home/oem/.m2/repository/com/alexbt/example-spring-boot-cloud-task/${examplespringbootcloudtask_version}/example-spring-boot-cloud-task-${examplespringbootcloudtask_version}.jar
task create mytask --definition "mytask"
task list
task launch mytask

