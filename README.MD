# GesRes_API

# FamilySync

Proyecto Final de ciclo de 2º de DAM de San Valero.

El proyecto pretende construir una API para poder ser consumida por una aplicación android de comunicación con familias
para un centro residencial de atención a personas mayores.

La API se encuentra desplegada en un servidor gratuito, he usado para esto KOYEB, que permite desplegar porque cada mes 
nos dan un crédito para poder ser gratuito. Se debe añadir al fichero el fichero systems.properties. se configura el servicio y
se le indica el repositorio git y la rama que queremos usar para el despliegue.
https://app.koyeb.com/

Esta es la dirección desde la que se puede consultar y consumir la API desde un entorno WEB https://gesresapi-albertrodtab.koyeb.app/

A mayores también gracias a un servidor gratuito que permite desplegar la base de datos con un máximo de 5 conexiones 
simultáneas, he desplegado la base de datos usando CLEVER CLOUD, he usado una base de datos MySQL, he redirigido la API 
a este servidor para poder trabajar con la base de datos.
https://www.clever-cloud.com/

A ambos servicios se puede registrar uno directamente con la cuenta de GitHub.

La idea de esto es poder consumir luego la API usando una APP desarrollada en Android y que consuma la API desplegada 
en cualquier momento.