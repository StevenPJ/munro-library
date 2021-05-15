# spj - munro-library
![workflow](https://github.com/StevenPJ/munro-library/actions/workflows/master.yml/badge.svg)


Coding challenge. We are provided a CSV file containing information about munros and munro tops within Scotland. The goal of your solution is to create a simple API which other software can use to sort and filter the munro data. Your solution should be developed using either Java or Kotlin. You are welcome to use any relevant libraries or frameworks, including application frameworks such as Spring or Micronaut. However, you should not use a database (in-memory or otherwise) to implement the search functionality.

## The Rules
The API should provide the following functionality:
* Filtering of search by hill category (i.e. Munro, Munro Top or either). If this information is
not provided by the user it should default to either. This should use the “post 1997”
column and if it is blank the hill should be always excluded from results.
* The ability to sort the results by height in meters and alphabetically by name. For both
options it should be possibly to specify if this should be done in ascending or descending
order.
* The ability to limit the total number of results returned, e.g. only show the top 10
* The ability to specify a minimum height in meters
* The ability to specify a maximum height in meters
* Queries may include any combination of the above features and none are mandatory.
* Suitable error responses for invalid queries (e.g. when the max height is less than the
minimum height)
* The query results should be returned as a list of items using JSON. Each item should contain the name, height in meters, hill category and grid reference (e.g. NN773308). Other fields should not be included.

### Constraints

* Parsed Munro data should be held in memory, without the use of a database.
* The munro data should be loaded from the local CSV file on API startup.
* There is no need to add authentication or rate limiting to endpoints developed.
* Please include any testing code you write in your submitted solution.
* Whilst developing your solution please commit your work into a git repository as you go.
This is not to see how much time you take or at what times you worked on the solution but is so that we can evaluate how you broke down and approached the problem.
  

# Requirements

- Java 11
- [Docker](https://docs.docker.com/docker-for-mac/install/)


# Running locally

## Running the tests
```
./mvnw verify # all tests
./mvnw test   # unit tests only
```

## Running the app
```
./mvnw spring-boot:run # using maven
docker compose up -d   # using docker
```

## Building a Docker image
```
./mvnw package
docker build . -t munro-library
```

## Using the API
The API can be tested via the swagger docs, which is accessible at [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/) by default while the app is running

