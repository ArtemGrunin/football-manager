# Football-Manager-App

This project is a web application for managing a football club. It's developed using Spring Boot for the backend and Angular for the frontend.

## Features

**Backend:**

- Basic CRUD operations for managing teams and players.
- Player transfer operation from one team to another.

**Frontend:**

- Display a list of teams with basic information about each one (team name, city, country, etc.) with the ability to add/delete teams.
- Display a list of all players with basic information about each one (name, surname, career start date, team, etc.) with the ability to add/delete players.
- Navigate to a page with detailed information about a team and its players (with the ability to navigate to a player's page), ability to edit team data.
- Navigate to a page with detailed information about a player, ability to edit player data, and conduct a transfer operation, navigate to the player's team page.

## How to Run

To launch this project you need to install Docker Desktop and register on DockerHub.

Run the next command in the terminal from the `football-manager-api` directory:

```
mvn clean package
```

Navigate to the root directory and create a .env file with all environment variables:

```
MYSQLDB_ROOT_PASSWORD=Admin
MYSQLDB_DATABASE=football_manager
MYSQLDB_USER=appuser
MYSQLDB_PASSWORD=
MYSQLDB_LOCAL_PORT=3307
MYSQLDB_DOCKER_PORT=3306
SPRING_LOCAL_PORT=6867
SPRING_DOCKER_PORT=8081
FRONTEND_LOCAL_PORT=4202
FRONTEND_DOCKER_PORT=4200
```

Run the next command in the terminal from the root directory:

```
docker-compose up --build
```

Open your browser at `http://localhost:4202`.

`4202` is a `FRONTEND_LOCAL_PORT` from .env