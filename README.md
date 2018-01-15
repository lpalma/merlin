# To run Postgres from a docker container
docker run --name merlin-postgres -e POSTGRES_USER=merlin -e POSTGRES_PASSWORD=merlin@local -p 5432:5432 -d postgres:9.6
