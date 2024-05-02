# **APLICACIÓN ANDROID PARA LA GESTIÓN DE SERVICIOS DE SUSCRIPCIÓN**

## Por Luis Sánchez Mateos

## Índice de Contenidos

- [1 - Sobre este proyecto](#1---sobre-este-proyecto)
  - [1.1 - Control de versiones](#11---control-de-versiones)
  - [1.2 - Licencia de uso](#12---licencia-de-uso)
- [2 - Análisis del problema](#2---análisis-del-problema)
  - [2.1 - Introducción al problema](#21---introducción-al-problema)
  - [2.2 - Antecedentes](#22---antecedentes)
  - [2.3 - Objetivos](#23---objetivos)
  - [2.4 - Requisitos](#24---requisitos)
    - [2.4.1 - Funcionales](#241---funcionales)
    - [2.4.2 - No funcionales](#242---no-funcionales)
  - [2.5 - Recursos](#25---recursos)
    - [2.5.1 - Software](#251---software)
    - [2.5.2 - Hardware](#252---hardware)
- [3 - Diseño de la solución software](#3---diseño-de-la-solución-software)
  - [3.1 - Modelados](#31---modelados)
    - [3.1.1 - Casos de uso](#311---casos-de-uso)
    - [3.1.2 - Interacción](#312---interacción)
    - [3.1.3 - Estado](#313---estado)
    - [3.1.4 - Actividad](#314---actividad)
  - [3.2 - Prototipado gráfico](#32---prototipado-gráfico)
    - [3.2.1 - Tablets / Smartphones](#321---tablets--smartphones)
  - [3.3 - Base de datos](#33---base-de-datos)
    - [3.3.1 - Diseño Conceptual (ER)](#331---diseño-conceptual-er)
- [4 - Implementación](#4---implementación)
  - [4.1 - Codificación](#41---codificación)
    - [4.1.1 - Usabilidad](#411---usabilidad)
    - [4.1.2 - Backend](#412---backend)
    - [4.1.3 - Frontend](#413---frontend)
  - [4.2 - Pruebas](#42---pruebas)
- [5 - Documentación](#5---documentación)
  - [5.1 - Empaquetado / Distribución](#51---empaquetado--distribución)
  - [5.2 - Instalación](#52---instalación)
  - [5.3 - Manual de Usuario / Referencia](#53---manual-de-usuario--referencia)
- [6 - Conclusiones](#6---conclusiones)
- [7 - Bibliografía](#7---bibliografía)

## 1 - Sobre este proyecto

La aplicación propuesta es un gestor de servicios de suscripción para dispositivos Android, diseñado para ayudar a los usuarios a administrar y controlar sus gastos en suscripciones mensuales. Con esta app, los usuarios pueden agregar sus diversos servicios de suscripción, como Netflix, HBO, revistas, entre otros, y monitorear fácilmente su consumo mensual en cada uno. La aplicación permite visualizar el tiempo restante de cada suscripción, el gasto acumulado en un mes específico, y recibir notificaciones cuando una suscripción esté a punto de expirar. Además de los servicios de suscripción, la aplicación ofrece la flexibilidad de gestionar otros tipos de pagos recurrentes, como compartir gastos de transporte, mediante la función de agregar pagos personalizados. La tecnología utilizada incluye Android Studio para el desarrollo de la aplicación móvil, una API REST en Python para la comunicación con el backend, y MySQL en la nube para almacenar y gestionar los datos de los usuarios. La aplicación busca ofrecer una solución integral para el control financiero de los usuarios, adaptándose a sus necesidades individuales y brindando una experiencia intuitiva.

### 1.1 - Control de versiones

Se utilizará este repositorio de GitHub para controlar las versiones del software, documentación, etc. Se hará uso de commits y branches que reflejen cambios o nuevas funcionalidades.

### 1.2 - Licencia de uso

## 2 - Análisis del problema

### 2.1 - Introducción al problema
En la actualidad, la suscripción a servicios digitales se ha convertido en una práctica común para millones de personas en todo el mundo. Sin embargo, la proliferación de estas suscripciones ha llevado a un problema creciente de gestión financiera para los usuarios. En un momento donde la estabilidad económica no es garantizada y los presupuestos personales son ajustados, es fácil perder de vista la cantidad total de dinero que se destina mensualmente a diferentes servicios de suscripción. Esta falta de conciencia puede resultar en gastos innecesarios y en una acumulación de pagos que afecta negativamente la salud financiera del usuario. Por lo tanto, existe una necesidad urgente de una solución que permita a los usuarios controlar de manera efectiva sus gastos en suscripciones, asegurando una gestión más consciente y eficiente de su dinero.

### 2.2 - Antecedentes

Con el auge de los servicios de suscripción y la creciente complejidad de las finanzas personales en la era digital, las herramientas de gestión financiera han adquirido una importancia cada vez mayor. Si bien existen algunas aplicaciones disponibles en el mercado que abordan aspectos de la gestión de suscripciones, ninguna ofrece una solución integral y personalizable que se adapte a las necesidades específicas de los usuarios. La falta de una herramienta consolidada y versátil brinda la oportunidad para el desarrollo de una aplicación como la propuesta.

### 2.3 - Objetivos

El objetivo principal de esta aplicación es proporcionar a los usuarios una herramienta efectiva para gestionar y controlar sus gastos en servicios de suscripción, así como otros pagos recurrentes, promoviendo una mayor conciencia financiera y ayudando a optimizar el uso de sus recursos económicos. Además, la aplicación busca ofrecer una UX intuitiva que garantice la adaptabilidad a las necesidades individuales de cada usuario. A través de funciones como el seguimiento del tiempo restante de las suscripciones y la recepción de notificaciones oportuna, se pretende facilitar la toma de decisiones informadas en cuanto a la renovación o cancelación de servicios, contribuyendo así a una gestión más eficiente y responsable de las finanzas personales.

### 2.4 - Requisitos

#### 2.4.1 - Funcionales

En cuanto a requisitos funcionales mi idea es la siguiente:

- Registro de usuarios: La aplicación debe permitir a los usuarios crear una cuenta personalizada para acceder a todas las funcionalidades.
- Agregación de servicios de suscripción: Los usuarios deben poder agregar y gestionar una variedad de servicios de suscripción, así como otros pagos recurrentes, según sus necesidades.
- Visualización de gastos: Se requiere la capacidad de visualizar de manera clara y detallada los gastos mensuales en cada servicio de suscripción, incluyendo el tiempo restante de cada suscripción.
- Notificaciones: La aplicación debe enviar notificaciones automáticas cuando quede poco tiempo para que una suscripción e
- Personalización: Los usuarios deben poder personalizar ciertos ajustes de la aplicación según sus preferencias individuales, como configurar la frecuencia de las notificaciones.


#### 2.4.2 - No funcionales

Como requisitos no funcionales encuentro:

- Usabilidad: La interfaz de usuario debe ser intuitiva y fácil de navegar, garantizando una experiencia fluida para usuarios de todos los niveles de habilidad.
- Seguridad: Se debe implementar un alto nivel de seguridad para proteger la información confidencial de los usuarios, incluyendo datos de inicio de sesión y detalles de suscripciones.
- Rendimiento: La aplicación debe ser rápida y eficiente en su funcionamiento, con tiempos de carga mínimos y capacidad para manejar grandes volúmenes de datos sin problemas.
- Disponibilidad: La aplicación debe estar disponible para su uso en todo momento, con un tiempo de inactividad mínimo y una alta disponibilidad del servicio.

### 2.5 - Recursos

#### 2.5.1 - Software

Las tecnologías a usar serán las siguientes:

- Android Studio: Se utilizará como entorno de desarrollo integrado (IDE) para la creación de la aplicación móvil compatible con dispositivos Android.
- Python: Se empleará para el desarrollo del backend de la aplicación, aprovechando su versatilidad.
- Flask: Se utilizará este framework de Python para la creación de una API RESTful que permita la comunicación entre el frontend y el backend de la aplicación.
- MySQL: Se empleará como sistema de gestión de base de datos relacional para almacenar y gestionar los datos de los usuarios y sus suscripciones.
- Git: Se utilizará para el control de versiones del código fuente, facilitando el seguimiento de los cambios realizados en el proyecto.
- Firebase Cloud Messaging (Por confirmar): En primera instancia se planea usar esta plataforma de mensajería en la nube para enviar notificaciones push a los usuarios en tiempo real.

#### 2.5.2 - Hardware

Muy simple, por mi parte un ordenador con el que trabajar y, por parte del usuario, un terminal Android.

## 3 - Diseño de la solución software

### 3.1 - Modelados

#### 3.1.1 - Casos de uso

#### 3.1.2 - Interacción

#### 3.1.3 - Estado

#### 3.1.4 - Actividad

### 3.2 - Prototipado gráfico

#### 3.2.1 - Tablets / Smartphones

![Resumen de las pantallas](/recursos/img/general.png)

Falta alguna pantalla más, como la de gestión de las notificaciones.

### 3.3 - Base de datos

#### 3.3.1 - Diseño Conceptual (ER)

![Diagrama Entidad - Relación](/recursos/img/diagramaER.png)
El nombre de subPago hace referencia a: 1 - Servicio de suscripción (SUBscription) y 2 - Pago (pago recurrente que el usuario quiera gestionar en la app).

#### 3.3.2 - Modelo relacional

## 4 - Implementación

### 4.1 - Codificación

#### 4.1.1 - Usabilidad

#### 4.1.2 - Backend

#### 4.1.3 - Frontend

### 4.2 - Pruebas

## 5 - Documentación

### 5.1 - Empaquetado / Distribución

### 5.2 - Instalación

### 5.3 - Manual de Usuario / Referencia

## 6 - Conclusiones

## 7 - Bibliografía