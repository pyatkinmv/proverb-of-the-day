# Proverb of the day

## Proverb learning application

# Architecture

The application contains 2 modules:
- `proverb-service` that supply with new proverbs;
- `tg-client` that receives from the proverb-service a proverb and sends it to the telegram channel `@proverboftheday`.

The application has a microservice architecture and interacts via REST API.

# Features

The proverb-service module uses the embedded h2-database and be runned with different profiles: dev or prod.

At each new start with the dev profile, the scheme is created, initialized by the data.sql script and deleted at the end of the session.

When starting with the prod profile, the file storage of the database is used. If the application is runned for the first time, a new file `~/h2/proverb-data` will be created; if not, this file will be reused. Therefore, in order to maintain data consistency, it is necessary to avoid running the `data.sql script. To do this, you have two options: 
1) delete `data.sql` file after first run;
2) set `spring.datasource.initialization-mode=never` in `proverb-service/src/main/resources/application.yml` file (just uncomment it).

# Application run

At the root of the application, run the following commands:
1) `mvn -pl proverb-service spring-boot: run -Dspring-boot.run.profiles = prod`
2) `mvn -pl tg-client -Dspring-boot.run.arguments = "- jasypt.encryptor.password = [secret-key]" spring-boot: run`

The application uses an encrypted token for telegram bot. Therefore, instead of `[secret-key]`, you must specify the secret key.

By default, proverb-service will start on port `3500`, tg-client on `3700`.
