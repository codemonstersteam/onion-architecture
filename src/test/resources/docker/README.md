# Описание
Конфигурация очередей подгружается в Docker ibm mq из файла [20-config.mqsc](20-config.mqsc)

## Test-containers : Volume mapping
https://java.testcontainers.org/features/files/

## Для локального тестирования посредствам сборки образа

Для запуска ibm mq и тестирования локально можно собрать докер образ
и запустить ibm mq, как показано в разделе RUN

### build
```
docker build -t ibmmqlocal . 
````
### RUN
````
docker run \
--env LICENSE=accept \
--env MQ_QMGR_NAME=QM1 \
--publish 1414:1414 \
--publish 9443:9443 \
--publish 9157:9157 \
ibmmqlocal
````

## Для локального тестирования посредствам монтирования раздела

```
docker run \
--env LICENSE=accept \
--env MQ_QMGR_NAME=QM1 \
--publish 1414:1414 \
--publish 9443:9443 \
--publish 9157:9157 \
--mount type=bind,source=./src/test/resources/docker/20-config.mqsc,target=/etc/mqm/20-config.mqsc \
icr.io/ibm-messaging/mq
```
