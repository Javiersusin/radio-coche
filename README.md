# 📻 Javi Radio Launcher

> Una app Android minimalista y premium para gestionar emisoras favoritas en una **pantalla de coche con Android 11** — sin acceso root, sin APIs privadas, sin hackeos.

<br>

```
┌─────────────────┬────────────────────────────────────────────────────┐
│  RADIO          │   FM        FM        FM        FM        FM       │
│  Favoritas      │  95.0      97.7      88.2      91.5      97.2     │
│                 │   MHz       MHz       MHz       MHz       MHz      │
│  6 emisoras     │   ───       ───       ───       ───       ───      │
│                 │  COPE    R.EBRO   EUROPA    HIT FM   CAD.100      │
│                 │                                                    │
│  [+ Nueva]      │                                                    │
└─────────────────┴────────────────────────────────────────────────────┘
```

<br>

## ¿Por qué existe esto?

Tengo una pantalla Android aftermarket en el coche. La radio del sistema (`com.jancar.radio`) funciona bien pero **no tiene gestión de favoritas decente** — y cambiar de emisora mientras conduces es un suplicio.

La solución obvia sería controlar la radio por código. El problema: es una **app de sistema privilegiada** (`/system/priv-app`), sin API pública, sin intents documentados, sin forma de cambiarle la frecuencia desde fuera. No se puede hacer nada con ella sin root.

Solución alternativa: **no intentar controlarla**. En vez de eso, construir una capa de gestión de favoritas encima que simplemente **lance la radio original con un toque**. El conductor selecciona la emisora visualmente, la app abre la radio, y el usuario ya sabe en qué frecuencia tiene que estar. Simple. Estable. Sin hackeos que se rompan con el próximo arranque.

<br>

## El dispositivo real

| Parámetro | Valor |
|---|---|
| Sistema | Android 11 |
| Radio OEM | `com.jancar.radio` |
| Ruta APK | `/system/priv-app/radio/radio.apk` |
| Acceso root | ❌ No |
| API de la radio | ❌ No existe |
| RAM / CPU | Limitados (hardware de gama baja) |
| Zona | Zaragoza / Huesca, Aragón 🇪🇸 |

<br>

## Qué hace la app

- 📋 **Lista de emisoras favoritas** guardada localmente (SharedPreferences, sin dependencias de base de datos)
- ✨ **Autodetección de nombre** — escribes la frecuencia y la app identifica la emisora automáticamente (base de datos de frecuencias de Aragón + nacionales)
- 🚀 **Lanzamiento directo** de la radio del sistema con un toque sobre la card
- ➕ **Añadir / eliminar** emisoras fácilmente (long press para borrar)
- 📦 **Emisoras de Aragón precargadas** en el primer arranque: COPE, Radio Ebro, Cadena 100, Europa FM, Hit FM y Aragón Deportes

<br>

## Diseño

Estética **cockpit premium** — oscuro, limpio, acento en cian eléctrico. Pensado para usarse con el dedo desde el asiento del conductor en una pantalla táctil de coche.

- Layout landscape de dos paneles fijos
- Frecuencia como elemento héroe (número grande, peso thin)
- Sin scroll vertical — las cards son horizontales
- Fullscreen, sin ActionBar, sin distracciones
- Sin Jetpack Compose — XML puro, bajo consumo de RAM

<br>

## Stack técnico

| Capa | Tecnología |
|---|---|
| UI | Android Views (XML) + ViewBinding |
| Lista | RecyclerView horizontal |
| Persistencia | SharedPreferences + JSON manual |
| Arquitectura | `ui / data / domain / launcher` |
| Sin dependencias pesadas | Solo `androidx.appcompat` + `recyclerview` |
| Min SDK | 23 |
| Target SDK | 30 |

<br>

## Estructura del proyecto

```
app/src/main/java/com/javi/radio/
├── ui/
│   ├── MainActivity.kt        # Pantalla principal + diálogo añadir
│   └── StationsAdapter.kt     # RecyclerView adapter horizontal
├── data/
│   └── FavoritesRepository.kt # Persistencia local + emisoras por defecto
├── domain/
│   ├── FavoriteStation.kt     # Modelo de datos
│   └── SpanishRadioStations.kt # BD de frecuencias España / Aragón
└── launcher/
    └── RadioLauncher.kt       # Lanza com.jancar.radio
```

<br>

## Instalación

```bash
# Compilar
./gradlew assembleDebug

# Instalar vía ADB
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

O copiar la APK a un pendrive, conectarlo al coche y abrirla desde el explorador de archivos del sistema.

<br>

## Lo que NO hace (y por qué)

| ❌ No hace | ✅ Por qué no |
|---|---|
| Cambiar la frecuencia de la radio | Sin API pública — app de sistema cerrada |
| Controlar volumen / mute de la radio | Mismo motivo |
| Simular pulsaciones de teclas | Frágil, depende del layout interno de cada firmware |
| Usar Accessibility Service para controlarla | Inestable entre versiones, requiere permiso especial |
| Leer la frecuencia actual de la radio | No hay broadcast ni ContentProvider expuesto |

La filosofía del proyecto: **estabilidad sobre funcionalidad**. Una app que funciona siempre es mejor que una que hace más pero se rompe.

<br>
---
<p align="center">
  Hecho con ☕ y algo de paciencia con Gradle
</p>
