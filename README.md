# Isla Parametrizada - Simulación de Ecosistema

Este proyecto implementa una simulación de un ecosistema en una isla parametrizada utilizando programación orientada a objetos y multihilos en Java.

## Descripción del Proyecto

La simulación consiste en una isla representada como una matriz de ubicaciones (por ejemplo, 100x20) donde habitan diferentes especies de plantas y animales. Los animales pueden realizar diversas acciones como:

- Comer plantas y/u otros animales
- Moverse a ubicaciones vecinas
- Reproducirse cuando hay dos del mismo tipo en una ubicación
- Morir de hambre o ser comidos

## Estructura de Clases

### Jerarquía Principal
- `Animal` (Clase abstracta base)
  - `Carnivoro`
    - Lobo 🐺
    - Boa 🐍
    - Zorro 🦊
    - Oso 🐻
    - Águila 🦅
  - `Herbivoro`
    - Caballo 🐎
    - Ciervo 🦌
    - Conejo 🐇
    - Ratón 🐁
    - Cabra 🐐
    - Oveja 🐑
    - Jabalí 🐗
    - Búfalo 🐃
    - Pato 🦆
    - Oruga 🐛
- `Planta`

## Características de los Animales

### Carnívoros

| Animal | Peso (kg) | Máx por ubicación | Velocidad | Alimento necesario (kg) |
|--------|-----------|-------------------|-----------|------------------------|
| Lobo   | 50        | 30               | 3         | 8                     |
| Boa    | 15        | 30               | 1         | 3                     |
| Zorro  | 8         | 30               | 2         | 2                     |
| Oso    | 500       | 5                | 2         | 80                    |
| Águila | 6         | 20               | 3         | 1                     |

### Herbívoros

| Animal  | Peso (kg) | Máx por ubicación | Velocidad | Alimento necesario (kg) |
|---------|-----------|-------------------|-----------|------------------------|
| Caballo | 400       | 20               | 4         | 60                    |
| Ciervo  | 300       | 20               | 4         | 50                    |
| Conejo  | 2         | 150              | 2         | 0.45                  |
| Ratón   | 0.05      | 500              | 1         | 0.01                  |
| Cabra   | 60        | 140              | 3         | 10                    |
| Oveja   | 70        | 140              | 3         | 15                    |
| Jabalí  | 400       | 50               | 2         | 50                    |
| Búfalo  | 700       | 10               | 3         | 100                   |
| Pato    | 1         | 200              | 4         | 0.15                  |
| Oruga   | 0.01      | 1000             | 0         | 0                     |

### Plantas
- Peso: 1 kg
- Máximo por ubicación: 200

## Probabilidades de Alimentación

La simulación incluye una matriz de probabilidades que determina la posibilidad de que un animal coma a otro cuando se encuentran en la misma ubicación. Por ejemplo:
- Un lobo tiene 60% de probabilidad de comer un conejo
- Un zorro tiene 90% de probabilidad de comer un ratón
- Los herbívoros tienen 100% de probabilidad de comer plantas

## Implementación Multihilo

El proyecto utiliza múltiples hilos para simular diferentes aspectos del ecosistema:
1. Crecimiento de plantas
2. Acciones de animales
3. Visualización de estadísticas del sistema

## Configuración

La simulación es altamente parametrizable, permitiendo ajustar:
- Tamaño de la isla
- Duración del ciclo de simulación
- Población inicial de cada especie
- Condiciones de finalización
- Número de crías por especie

## Características Principales

- Jerarquía completa de clases usando POO
- Comportamientos específicos por especie
- Sistema multihilo para simulación paralela
- Estadísticas en tiempo real
- Configuración flexible

## Características Opcionales Implementadas

- Parámetros centralizados para fácil gestión
- Representación visual con caracteres Unicode
- Comportamientos personalizados por especie

## Requisitos del Sistema

- Java JDK 8 o superior
- Memoria suficiente para manejar la simulación de múltiples entidades

## Ejecución

Para ejecutar la simulación:
```bash
java Main
```

## Notas de Implementación

- Los animales tienen comportamientos específicos según su especie
- Se utiliza un generador de números aleatorios multiproceso para las probabilidades
- El sistema de alimentación considera las probabilidades definidas en la matriz
- La reproducción ocurre cuando dos animales de la misma especie coinciden en una ubicación
