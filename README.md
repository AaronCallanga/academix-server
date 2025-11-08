````markdown
# Academix Server

Academix is a web application designed for school registrars to manage and audit document requests. It includes features such as logging, email notifications, admin authentication, and scheduled tasks to streamline registrar operations.

## Docker Image

The Docker image for Academix Server is available on Docker Hub:

**Image Name:** `pogiii/academix:1.0`
**Image DockerHub:** `https://hub.docker.com/r/pogiii/academix`
**PostgreSQL DB Image:** `postgres:latest`

You can pull the image using:

```bash
docker pull pogiii/academix-server:2.0
docker pull postgres:latest
````

## Features

* Registrar management of student document requests
* Admin authentication
* Logging of actions
* Email notifications for document requests
* Scheduled tasks for automating periodic operations

## Requirements

* Docker
* Docker Compose

## Setup Instructions

1. **Clone the repository:**

```bash
git clone https://github.com/AaronCallanga/academix-server.git
cd academix-server
```

2. **Configure environment variables:**

The server requires connection to a PostgreSQL database. Default environment variables:

* `POSTGRES_DB`: academix
* `POSTGRES_USER`: user
* `POSTGRES_PASSWORD`: password
* `DB_USER`: user
* `DB_PASSWORD`: password
* `POSTGRES_URL`: academix-db:5432

You can modify them in the `docker-compose.yml` if needed.

3. **Run the application with Docker Compose:**

```bash
docker compose up --build
```

This will build the server image from the Dockerfile, start the PostgreSQL database, and ensure the server waits until the database is ready.

4. **Access the application:**

Once running, the server will be accessible at:

```
http://localhost:8080
```

## Docker Compose Details

* `academix-db` uses the official PostgreSQL image. Database initialized with `academix` name and configured user/password.
* `academix-server` is built from the local Dockerfile and connects to `academix-db`.
* Healthchecks ensure the server waits until the database is fully ready.
* The server port `8080` is exposed to the host.

## Contributing

Feel free to fork this repository and contribute by submitting pull requests. Ensure Docker images build successfully and all tests pass.


Do you want me to do that next?
```
