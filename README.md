# 🥗 Recetas Cliente - Java Desktop

Este proyecto es la interfaz de escritorio desarrollada en **Java** para la gestión de recetas. Actúa como cliente de la [Recetas_API](https://github.com/vicroix/Recetas_API), permitiendo a los usuarios autenticarse y administrar su catálogo culinario de forma intuitiva.

## ✨ Funcionalidades
* **Gestión de Sesión:** Sistema de Login conectado a un backend con seguridad **JWT**.
* **Visualización Dinámica:** Listado de recetas obtenidas en tiempo real desde la API.
* **Operaciones CRUD:** Interfaz para añadir, editar y eliminar recetas (según permisos).
* **Consumo de API REST:** Implementación de peticiones HTTP para comunicación con el servidor.

## 🛠️ Tecnologías y Librerías
* **Lenguaje:** Java 17/21.
* **Interfaz Gráfica:** Java Swing / WindowBuilder.
* **Dependencias principales:**
    * `Gson` / `Jackson` (para el parseo de JSON).
    * `HttpClient` (para las peticiones a la API).

## 🚀 Cómo empezar

### Requisitos previos
1. Tener instalada la [Recetas_API](https://github.com/vicroix/Recetas_API) y en ejecución (por defecto en `http://localhost:9090`).
2. Disponer de un JDK 17 o superior.

### Instalación y Ejecución
1. Clona este repositorio:
   ```bash
   git clone [https://github.com/vicroix/Recetas_cliente.git](https://github.com/vicroix/Recetas_cliente.git)
