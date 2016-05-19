# Procesador de eventos con Apache Flink

Recibe eventos de Kafka, guarda la últimas corrdenadas de cada usuario en MongoDB y revisa reglas para detectar los eventos programados

Cuando encuentra una alerta, envía un evento a una cola kafka para que sea leído por la aplicación de avisos.