# Bantumi - Front-end para Móviles

Este proyecto es una modificación de la aplicación de juego Bantumi, proporcionada por en Moodle. Mi nombre es David Yareth Macaya Albericio, y esta versión incluye varias características adicionales y mejoras en comparación con la versión original.

## Características Implementadas

### Reiniciar Partida
- Al pulsar la opción "Reiniciar", se muestra un diálogo de confirmación.
- En caso de respuesta afirmativa, se reinicia la partida actual.

### Guardar Partida
- Al pulsar la opción "Guardar", se muestra un diálogo de confirmación.
- En caso de respuesta afirmativa, se guarda la situación actual del tablero en el sistema de ficheros del dispositivo.
- Solo se guarda una única partida.
- Para guardar la información de una forma estructurada, se utiliza un fichero `.json`, por lo que es añadida la libería GSON al proyecto.

### Recuperar Partida
- Al pulsar la opción "Recuperar partida", en caso de existir una partida guardada, se muestra un diálogo de confirmación.
- En caso de respuesta afirmativa, se recupera la partida guardada.

### Guardar Puntuación
- Al finalizar cada partida se guarda la puntuación, utilizando el sistema de bases de datos de Room.
- La información guardada en la base de datos es la siguiente:
    - Nombre del jugador ganador.
    - Número de semillas del jugador ganador.
    - Nombre del jugador perdedor.
    - Número de semillas del jugador perdedor.
    - Fecha y hora del fin de la partida.

### Mejores Resultados
- Esta opción muestra el histórico con los diez mejores resultados obtenidos, ordenados por el mayor número de semillas obtenido por cualquier jugador.
- Incluye una opción con confirmación para eliminar todos los resultados guardados.

### Opciones Opcionales
Además de las características obligatorias, se han implementado las siguientes opciones opcionales:

- **Cronómetro**: Se añade un cronómetro que comienza cuando se realiza el primer movimiento de la partida y vuelve a 0 una vez que la partida termina, se reinicia o se recupera una partida guardada. No se guarda el tiempo actual del cronómetro al guardar la partida.

- **Cambios de Color**: Se ha cambiado el color primario de la aplicación por el utilizado en la página web personal de David Yareth Macaya Albericio (davidyareth.com).

- **Juego de Dos Jugadores**: En la pantalla de preferencias, se ha dejado desarrollada la estructura para seleccionar jugar contra otro jugador en lugar de contra la máquina (jugador 2). Si se elige jugar con otro jugador, se proporciona la opción de introducir el nombre del segundo jugador. Cabe destacar que solo se ha desarrollado esta estructura en preferencias y no se ha tocado la lógica de juego proporcionada inicialmente.