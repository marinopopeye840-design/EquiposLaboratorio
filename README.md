# Control de Equipos de Laboratorio

## Descripción
Este proyecto consiste en una aplicación de escritorio desarrollada en JavaFX, utilizando Hibernate para gestionar los datos almacenados en PostgreSQL.

Su objetivo es llevar un control de los equipos de laboratorio, permitiendo registrar, consultar, modificar y eliminar equipos.

## Funcionalidades
- Registrar nuevos equipos.
- Mostrar los equipos registrados.
- Modificar y eliminar equipos existentes.
- Buscar equipos por nombre.
- Filtrar equipos por tipo.
- Validar los datos ingresados.
- Clasificar los equipos según su tipo y estado.

## Base de datos
La base de datos utilizada se llama `bd_laboratorio_orm`.
Contiene el esquema `catalogos` y dos tablas:
- `tipo_equipo`: almacena los tipos de equipos.
- `equipo`: almacena los equipos registrados.

Ambas tablas están relacionadas mediante una relación de uno a muchos, implementada en Hibernate con `@ManyToOne` y `@JoinColumn`.

## Configuración y ejecución
1. Tener instalado Java 21, PostgreSQL e IntelliJ IDEA.
2. Crear la base de datos ejecutando el script SQL del proyecto.
3. Verificar la conexión en `persistence.xml`.
4. Configurar la variable de entorno `DB_PASSWORD` con la contraseña local de PostgreSQL.
5. Abrir el proyecto en IntelliJ IDEA y cargar las dependencias de Maven.
6. Ejecutar la clase `Launcher.java`.

## Persistencia y consultas
El proyecto utiliza `EntityManager` para realizar las operaciones sobre la base de datos.

Se emplean `persist()`, `merge()` y `remove()` para registrar, actualizar y eliminar equipos. Las transacciones utilizan `commit()` y `rollback()`.

Las consultas se realizan mediante JPQL, utilizando parámetros para buscar equipos por nombre y tipo.

## Autor
Jarold Jose Montealto Cantillano

**Asignatura:** Programación de Aplicaciones de Escritorio  
**Universidad:** Universidad Americana (UAM)
