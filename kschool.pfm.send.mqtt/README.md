# Emisor de eventos MQTT
Simple emisor de eventos a servidor mqtt. 

Recoge los eventos simulados de archivo y los envía línea a línea. El delay es configurable con el parámetro --sleep

```sh
java -jar kschool-send-to-mqtt.jar [--sleep timeInMillis]
```


"timeInMillis" es el tiempo en milisegundos que tardará en enviar un nuevo evento al servidor mqtt.
