# Cumulocity CloudWatch Microservice

### Overview

Microservice periodically offloading Cumulocity audit logs as CloudWatch events for every subscribed tenant.

### Build and Upload

1. Prerequisites
   1. JDK 11+
   2. Maven
   3. Docker
2. Build the microservice: `mvn clean install`
3. Upload the zip file in `target` folder to cumulocity.