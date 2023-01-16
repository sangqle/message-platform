# The system operation flow is inspired by the model below

You can refer in github link: [Go to site](https://github.com/codekarle/system-design/tree/master/system-design-prep-material/architecture-diagrams)

And some refer from [ACE-the system interview design](https://towardsdatascience.com/ace-the-system-interview-design-a-chat-application-3f34fd5b85d0)

![architecture flow](assets/message-architecture.png)

# How to start (may be later)
## Setup Docker
In root directory
### Setup Redis for User Mapping Service
```bash
docker run -d --name some-redis -p 6379:6379 redis
```

### Setup Mysql for User Service (maybe use later, this is optional)
```bash
docker run --name jmysql -e MYSQL_ROOT_PASSWORD=root -p 3307:3306 -d mysql:latest
```
### Setup Cassandra for message service
```code
./setup-config.sh
```
Run docker compose Cassandra it will be create 3 node as docker-compsose define
```code
docker-compose up
```
## Run Spring boot project

```code
```