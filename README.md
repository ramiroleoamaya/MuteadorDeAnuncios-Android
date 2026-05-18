# Muteador de Anuncios para Spotify (Android)# Spotify Ad Muter for Android | Nativo con Kotlin & Jetpack Compose 🎧🚫

¡Mi primer proyecto de desarrollo mobile! Es una herramienta nativa para Android enfocada en mejorar la experiencia del usuario al escuchar música, automatizando el control del volumen multimedia ante contenido publicitario.

## ✨ Características principales
* **Detección Automática:** Utiliza un `NotificationListenerService` para capturar en tiempo real las actualizaciones de la barra de estado del sistema.
* **Filtro Anti Falsos Positivos:** Cuenta con un algoritmo optimizado que analiza títulos, descripciones y subtextos para distinguir con precisión una canción normal (incluso si empieza con palabras conflictivas) de una pauta publicitaria.
* **Control de Audio Forzado:** Interactúa directamente con el `AudioManager` de Android para mitigar el volumen multimedia de inmediato y restaurarlo al nivel original apenas retorna la música.
* **Interfaz Moderna:** Desarrollada con **Jetpack Compose**, incorporando accesos directos por código para facilitar la activación de los "Ajustes Restringidos" de Android.

## 🛠️ Tecnologías utilizadas
* **Lenguaje:** Kotlin
* **UI Framework:** Jetpack Compose (Material Design 3)
* **Android SDK:** API 26 (Android 8.0) en adelante (Testeado con éxito en hardware físico con Android Oreo y superior)
* **Arquitectura:** Component Activity & Servicios en segundo plano

## 🔧 Requisitos de Instalación
Debido a las políticas de seguridad nativas de Android, tras compilar e instalar la aplicación es mandatorio otorgar el permiso especial de **"Acceso a notificaciones"**. La propia app provee un botón directo a dicha sección en la interfaz principal.
