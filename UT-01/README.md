# VideoClub

Aplicación Java Swing para gestionar usuarios, videojuegos, alquileres y devoluciones. La interfaz está en catalán. Los datos se guardan en memoria y se pierden al cerrar el programa.

## Cómo ejecutarlo

Hace falta JDK 17 o superior y NetBeans con soporte Maven.

1. Abrir la carpeta `UT-01` como proyecto Maven.
2. Hacer `clean install` en el proyecto raíz.
3. Ejecutar `cat.videoclub.App` desde el módulo `app`.

Desde la terminal, dentro de `UT-01`:

```sh
./mvnw clean install
java -jar app/target/VideoClub.jar
```

No mover solo el JAR principal. Necesita las bibliotecas de `app/target/lib`.

## Estructura

- `ValidationAPI`: validaciones de campos.
- `app`: modelo e interfaz.
- `MainFrame`: ventana principal y menús.
- `UserDialog`, `GameDialog`, `RentalDialog` y `ReturnDialog`: formularios.
- `Bundle_ca.properties`: textos en catalán.

Los formularios con archivo `.form` se pueden abrir con el diseñador de NetBeans (botón Design).

## Funcionamiento

- Al iniciar solo se muestra Lloguers.
- Los alquileres vencidos se ven en rojo y los devueltos en verde.
- Crear usuario pide nombre, apellidos, DNI, teléfono y fecha.
- Crear juego permite elegir género y unidades por plataforma.
- Registrar alquiler descuenta una unidad de la plataforma elegida.
- Devolver alquiler recupera la unidad de stock.
- No se puede repetir un alquiler activo ni borrar un usuario o juego con alquileres pendientes.

## Datos iniciales

Al arrancar hay dos usuarios, dos juegos y dos alquileres de ejemplo.
