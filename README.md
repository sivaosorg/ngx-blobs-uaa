<h1 align="center">
  <img alt="Eagle logo" src="assets/profile.png" width="224px"/><br/>
  Resource server Users
</h1>

<p align="center">
The uaa server provide <b>user details</b>, <b>user role</b>, <b>user privilege</b>, <b>user perms</b>, <b>user group</b>, <b>user team</b>
<br/>
</p>


## ⚡️ Quick start

Build application:

```bash
/bin/bash gradlew bootJar
```

Run application on `dev`:

```bash
start java -Xmx1024m -jar -Dspring.profiles.active=dev build\libs\ngx-blobs-uaa-1.0.0.jar &
```

Run application on `prod`:

```bash
start java -Xmx1024m -jar -Dspring.profiles.active=prod build\libs\ngx-blobs-uaa-1.0.0.jar &
```

> output jar: <b><i>ngx-blobs-uaa-1.0.0.jar</i></b>

## :rocket: Functions

#### Checkout params

:package: checkout file [`application-params.yml`](src/main/resources/application-params.yml)

```yml
# ////////////////////////////
# Config Params Attributes
# ////////////////////////////
---
sivaos:
  swagger:
    description: API reference for developers
    contact-name: sivaos
    contact-url: https://github.com/pnguyen215
    contact-email: sivaglobalos@gmail.com
    license: sivaos 1.1
    license-url: https://swagger.io/license/
    version: v.1.1
  authentication:
    client-id: "sivaosUser" # set client id for user register
    client-secret: "sivaosU" # set client secret for user register
    host: "localhost:8083" # set host oauth2.0 for user register
    protocol: "http://" # set protocol for user register
    enable: true # enable authentication server (OAuth2.0), default is true
  cache:
    key-master: uaa
    callbacks:
      user-details:
        enabled: false # enable cache user info response into redis, default is false

# ////////////////////////////
# Config Kafka Attributes
# ////////////////////////////
---
spring:
  kafka:
    enable-ssl: false
    sasl:
      enabled: false
    producer:
      enable-deep-config: false

# ////////////////////////////
# Config 3rd Ally Attributes
# ////////////////////////////
---
spring:
  resource-server-callback-starter:
    enabled: false # enable to check token from authorization server (url)
```

