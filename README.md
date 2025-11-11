# Incopatible Data Solver (IDS)

## Project information
### Backend
- Java 21
- Maven: 3.9.9
- Docker Desktop

### Frontend
- node (v18.13 or newer)
- Angular (v17)
- Angular CLI (v17)

## Starting the application

### In Docker Desktop
1. Open Docker Desktop
2. Run the following command in the root directory of the repository: 
`docker compose up -d`
3. Go to [localhost:4200](http://localhost:4200)

### In the terminal
1. Open Docker Desktop
2. Run the following command in the `IDS-backend\src\main\resources\db` directory of the repository: `docker compose up -d`
3. Run the following command in the `IDS-backend` directory of the repository: `mvn spring-boot:run`
4. Run the following command in the `IDS-frontend` directory of the repository: `npm start`
5. Go to [localhost:4200](http://localhost:4200)