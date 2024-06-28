# Cumulocity CloudWatch Microservice

### Overview

Microservice periodically offloading Cumulocity audit logs as CloudWatch events for every subscribed tenant.

### Build and Upload

1. Prerequisites
   * JDK 11+
   * Maven
   * Docker
2. Configure the microservice
   * With an HTTP Request
   ```http request
   PUT /tenant/options/cumulocity-cloudwatch
   Authorization: ...
   Content-Type: application/json
   ```
   ```json
   {
     "aws-region": "<awsRegion>",
     "aws-access-key-id": "<accessKeyID>",
     "credentials.aws-secret-access-key": "<awsSecretAccessKey>"
   }
   ```
   * With [go-c8y-cli](https://goc8ycli.netlify.app/) using the [cumulocity-cloudwatch-configuration-template](src/main/configuration/cumulocity-cloudwatch-configuration-template.jsonnet) 
   ```shell
   c8y tenantoptions updateBulk --category cumulocity-cloudwatch --template ./src/main/configuration/cumulocity-cloudwatch-configuration-template.jsonnet --templateVars "awsRegion=<awsRegion>,awsAccessKeyID=<accessKeyID>,awsSecretAccessKey=<awsSecretAccessKey>"   
   ```
3. Build the microservice: `mvn clean install`
4. Upload the zip file in `target` folder to cumulocity.