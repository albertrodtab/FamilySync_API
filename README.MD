# GesResFamily

Proyecto de la asignatura de acceso a datos de 2º de DAM de San Valero.

El proyecto pretende construir una API para poder ser consumida por una aplicación android de comunicación con familias
para un centro residencial de atención a personas mayores.

### Requisitos (1 pto cada uno, obligatorios)

* Diseña la API y escribe el fichero OpenAPI 3.0 de la API. Incluye, al menos, los casos de éxito (20X), 400, 404 y los 500.
* Diseña una API Virtual de forma que existan, al menos, 3 Casos de Uso para cada operación (uno de OK y otro para KO).
      * Aquí se puede consultar la API Virtual realizada con WireMock
      * https://github.com/albertrodtab/Virtual-GesResFamilyAPI

* Prepara una colección Postman de prueba para la API diseñada y otra que permita probar todos los Casos de Uso de la API virtual
* Diseña, al menos, 3 operaciones para que funcionen de forma reactiva con WebFlux.
      * Aquí se puede consultar la Version Reactiva de la API 
      * https://github.com/albertrodtab/GesResFamilyAPI-Reactive
* Ajusta el desarrollo de tu proyecto para que cumpla todas las decisiones de diseño adoptadas en los puntos anteriores


### Otras funcionalidades (1 pto cada una)

* Si tu API está securizada, añade la información necesaria al fichero OpenAPI 3.0
* Añade alguna operación en la que se envien o reciban ficheros
* Parametriza ambas colecciones Postman de forma que sea fácil cambiar el host, puerto o basePath de la API
* Añade al fichero de especificación de la API (OpenAPI 3.0) un par de ejemplos para cada operación
* 
##### *IMPLEMENTADO*
* Utiliza las herramientas Git y GitHub durante todo el desarrollo de la aplicación. Utiliza el gestor de Issues 
para los problemas/fallos que vayan surgiendo.
