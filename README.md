# Cumulocity CloudWatch Microservice

### Overview

Microservice periodically offloading Cumulocity audit logs as CloudWatch events for every subscribed tenant.

### Build and Upload

1. Prerequisites
   1. JDK 11+
   2. Maven
   3. Docker
2. Configure the microservice by setting the following tenant options:
```http request
PUT /tenant/options/cumulocity-cloudwatch
Authorization: ...
Content-Type: application/json
```
```json
{
  "aws-access-key-id": "<accessKeyID>",
  "aws-region": "<awsRegion>",
  "credentials_aws-secret-access-key": "<awsSecretAccessKey>"
}
```
3. Build the microservice: `mvn clean install`
4. Upload the zip file in `target` folder to cumulocity.