# Trabajo práctico JAVA

## Integrantes

45282 - Edgar Gastón Altamirano

## Índice

- [Links últiles](#Links-útiles)
- [Tabla de requerimientos para el TP](#Tabla-de-requerimientos-para-el-TP)
- [Enunciado general del TP](#Enunciado-general-del-TP)
- [Introducción al negocio](#Introducción-al-negocio)
- [Dominio](#Dominio)
- [Requerimientos](#Requerimientos)
- [Niveles de acceso](#Niveles-de-acceso)
- [Casos de uso](#Casos-de-uso)
- [Listados complejos](#Listados-complejos)
- 

## Links útiles

[Pautas del TP](https://github.com/utnfrrojava/javaClases/tree/master/tp)

## Tabla de requerimientos para el TP

| Requerimiento                | cant. mín. 1 o 2 integ | Detalle/Listado de casos incluidos |
| ---------------------------- | ---------------------- | ---------------------------------- |
| ABMC                         | todos                  | ✅                                  |
| CU "Complejo"(nivel resumen) | 1                      | ✅                                  |
| Listado complejo             | 1                      | ✅                                  |
| Nivel de acceso              | 2                      | ✅                                  |
| Manejo de errores            | obligatorio            | no requiere detalle                |
| publicar el sitio            | olbigatorio            | no requiere detalle                |

- Listado de integrantes: indicar legajo, apellido y nombres. ✅
- Enunciado general del tp. Uno o dos parrafos describiendo el sistema. ✅
- Imagen con un borrador del modelo de dominio/modelo de datos.  ✅
- Lista del NOMBRE de los Casos de Uso/User Stories de cada requerimiento funcional. ✅
- Tablas de listados complejos (en caso que el nombre del CU no sea representativo de su complejidad, 1 o 2 lineas) ✅

## Enunciado general del TP

### Descripción del sistema

El presente proyecto apunta a elaborar un sistema que permita a sus usuarios hacer un seguimiento de su rendimiento en diferentes ejercicios a lo largo del tiempo. Este sistema permitirá que el usuario cargue sus ejercicios (que realizan en el gimnasio) así como también cargue los diferentes resultados que obtendrá en los mismos (llevando un histórico de estos). Adicionalmente el usuario podrá agregar información como el aparato que usa para ese ejercicio como también el/los músculos que entrenó en el mismo.

Este sistema permitirá al usuario llevar registro de sus medidas a lo largo del tiempo como así también seguir a otros usuarios para ver (en caso de que lo permitan) el rendimiento de estos terceros. El sistema podrá informar con un gráfico de la evolución tanto sea del rendimiento en ejercicios como en las medidas del usuario.

## Introducción al negocio

El negocio parte de una aplicación que solicitó mi novia para tener un seguimiento del peso que levanta en los distintos ejercicios que realiza cuando va al gimnasio. La idea era poder agregar los ejercicios que realiza (pudiedo también modificarlos o eliminarlos según necesite) y llevar el hisotrial de los distintos pesos que aguantó en los distintos ejercicios. Su idea es poder ver el rendimiento que tiene en el gimnasio a lo largo del tiempo (podríamos pensar en un gráfico de barras que demuestre su evolución en los ejercicios).

### Objetos identificados

Con la narrativa descripta anteriormente, logramos identificar los siguientes objetos en el negocio:

| Objeto de Negocio | Descripción                                                  | Depende de         |
| ----------------- | ------------------------------------------------------------ | ------------------ |
| Persona           | Representa a un usuario de la aplicación, incluyendo su información personal y su conjunto de ejercicios realizados en el gimnasio. |                    |
| Ejercicio         | Representa un tipo de ejercicio físico que se realiza en el gimnasio, incluyendo detalles como el nombre del ejercicio, los pesos utilizados, y el historial de rendimiento. |                    |
| Resultado         | Representa un registro del rendimiento del usuario en un ejercicio específico durante una sesión de entrenamiento, incluyendo detalles como el peso levantado, las repeticiones y la fecha del registro. | Persona, Ejercicio |

## Dominio

Diagrama de Entidad-Relación

![DER](/Users/gastonalt/Library/Mobile Documents/com~apple~CloudDocs/TP Java.drawio.png)

[Link al DER](https://drive.google.com/file/d/1U_5Lx3mCz3gyhXkO0re-1-4kmrYTT0Km/view?usp=sharing)

## Requerimientos

### Funcionales

- Registrar personas
- Registrar medidas para persona
- Registrar localidades
- Registrar músculos
- Registrar Ejercicios
- Registrar Emociones
- Registrar seguimientos entre usuarios
- Registrar resultados para una persona realizando un ejercicio
- Registrar aparato
- Informar Ejercicios registrados
- Informar localidades
- Informar histórico de Medidas
- Informar histórico de resultados
- Informar evolución de resultados
- Informar músculos registrados
- Informar aparatos registrados
- Informar emociones
- Informar resultados de ejercicios de personas a las que sigo
- Registrar inicio de sesión de una persona
- Eliminar Ejercicios
- Eliminar Resultado
- Eliminar Aparatos

## Niveles de acceso

Se presentan los siguientes niveles de acceso:

| Tipo de usuario    | Descripción de nivel de acceso                               |
| ------------------ | ------------------------------------------------------------ |
| Usuario Registrado | El usuario registrado podrá seguir otros usuarios, cargar sus ejercicicios y resultados. |
| Administrador      | El administrador es quien podrá ver todas las actividades de los usuarios y además cargar las localidades, máquinas y músculos. |
| Invitado           | El invitado podrá visualizar la página de landing y crear su cuenta |

## Casos de uso

CUR01 - Llevar seguimiento de rendimiento en ejercicios y medidas

CUR02 - Informar la evolución de todos los usuarios dado un rango de fecha (analizar factibilidad)

CUU01 - Crear cuenta de usuario

CUU02 - Cargar ejercicio

CUU03 - Cargar nueva medida

CUU04 - Crear ejercicio

CUU05 - Crear aparato

CUU06 - Cargar resultado

CUU07 - Informar histórico de resultados

CUU08 - Informar histórico medidas

CUU09 - Cargar músculos

CUU10 - Eliminar resultado

CUU11 - Eliminar aparato

CUU12 - Eliminar medida

CUU13 - Eliminar resultado

CUSF01 - Registrar medidas inciales

CUSF02 - Seguir usuario

CUSF03 - Iniciar sesión

CUSF04 - Listar ejercicios

CUSF05 - Listar medidas

## Listados complejos

### LC01 - Listado de rendimientos para un ejercicio

Mostramos los rendimientos para un ejercicio, con la complejidad de tener que hacer el join correspondiente.

### LC02 - Listado de rendimiento para todos los usuarios dado un rango de fecha

Mostramos los rendimientos para todos los usuarios dado un ejercicio (Mapea CUR02) (Podría ser un gráfico en vez de un listado)

## Tablas en BD

| Tabla       | Campos             | FK   |
| ----------- | ------------------ | ---- |
| localidades | cod_postal, nombre |      |
|             |                    |      |
|             |                    |      |
|             |                    |      |
|             |                    |      |
|             |                    |      |
|             |                    |      |
|             |                    |      |
|             |                    |      |

