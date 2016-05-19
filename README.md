# Sistema de detección de alertas

Sistema para detectar alerta en base a parámetros recibidos de dispositivos móviles. Actualmente es una simulación para proyecto de fin de máster. Los datos están simulados, y se envían desde un fichero csv.

Los datos para la fase de prueba serán extraídos de un dataset proveniente de acelerómetros de un grupo de alumnos de [Donald Bren School of Information and Computer Sciences]. Los datos están en formato csv, por lo que habrá que hacer una labor de transformación antes de ser analizados. Están modificados para que contengan coordenadas simuladas en la provincia de Madrid.

Los eventos tienen la siguiente forma:

(creation_time, x, y, z, latitude, longitude, user)

Por ejemplo:

1.424776359595E12,-0.011538195718654432,-0.04366253516819572,1.0382104994903159,40.326948134436044,-3.517910681155397,Daniel

Existen dos tipos de alertas aplicado a cualquiera de los 3 ejes (x,y,z):

    - 2 segundos o más a una acelaración superior a 3G
    - Evento con 5G o más

La arquitectura es la siguiente:

![ScreenShot](https://github.com/DVentas/PFM-Kschool-Flink-Docker/master/architecture.png)

Todo el proyecto está montado con Docker, coordinado a través de docker compose.

Con Maven se compila los módulos hechos en scala y se genera el jar en la ruta accesible desde el docker correspondiente para cada uno de los 3 proyectos dockerizados ([sendmqtt], [sendmail] y [kschool.flink])

Para iniciar el proyecto (compilar código e iniciar docker con todas las herramientas será necesario:

```sh
$ cd docker
$ ./start
```

Será necesario tener instalado docker & docker-compose. Si su sistema operativo no es linux, además necesitará docker-machine para simular la máquina linux.

Una vez iniciado, debido a que actualmente no es posible iniciar Apache NiFi por línea de comando, será necesario iniciarlo desde la URL :

**dockerIP:8080/nifi**


Este proyecto está en progreso y tiene mucho que mejorar. Todo feedback será bienvenido.


   [Donald Bren School of Information and Computer Sciences]: <http://www.ics.uci.edu/>
   [sendmqtt]: <https://github.com/DVentas/PFM-Kschool-Flink-Docker/tree/master/kschool.pfm.send.mqtt>
   [sendmail]: <https://github.com/DVentas/PFM-Kschool-Flink-Docker/tree/master/kschool.pfm.sendMAil>
   [kschool.flink]: <https://github.com/DVentas/PFM-Kschool-Flink-Docker/tree/master/kschool.pfm.flink>


