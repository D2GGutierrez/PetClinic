# 🐾 PetClinic — Examen de Pruebas Unitarias

**Duración:** 2.5 horas | **Stack:** Spring Boot + JUnit 5 + Mockito


---

## 📌 Reglas generales

- Repositorio en GitHub o GitLab. Rama `main` protegida.
- Cada integrante trabaja en su propia rama `feature/...`.
- Toda integración a `main` se hace por merge con al menos 1 revisor del equipo.
- Cada integrante debe tener commits propios con su nombre/correo real.
- Mínimo: **una rama por integrante, un merge fusionado por integrante y 1 conflicto resuelto**.
- Al final, ejecutar `./mvnw test` debe pasar sin errores.

---

## 🎯 Objetivo

Cada grupo debe escribir **pruebas unitarias** para los servicios de su entidad asignada, usando **JUnit 5 + Mockito**.

Cada integrante tiene asignado su propio bloque de pruebas. Cada bloque debe ir en su propia rama y PR.

## 👥 Escenarios para grupos de 4 integrantes

### Grupo 4 — Pruebas para VisitService

**Integrante A — CRUD básico**
- `testCreateVisit` — registrar una visita.
- `testUpdateVisit` — actualizar una visita.
- `testFindVisitById` — buscar visita existente.

**Integrante B — Consultas por relación**
- `testFindByPetId` — buscar visitas de una mascota.
- `testFindByVetId` — buscar visitas de un veterinario.
- `testDeleteVisit` — eliminar visita.

**Integrante C — Cálculos y errores**
- `testCalculateTotalCostByPet` — sumar costos con `BigDecimal`.
- `testCalculateTotalCostByPet_NoVisits` — devuelve `BigDecimal.ZERO` si no hay visitas.
- `testFindVisitById_NotFound` — debe lanzar `VisitNotFoundException`.

---

## 📏 Evaluación

| Criterio                                                | Puntos |
|---------------------------------------------------------|--------|
| Uso de Git (ramas, merge, revisiones)                   | 6      |
| Cada integrante completó sus pruebas asignadas          | 4      |
| Resolución de conflictos                                | 3      |
| Calidad de las pruebas (asserts claros, uso de Mockito) | 4      |
| Las pruebas pasan (`./mvnw test`)                       | 3      |

---

## 📦 Entregables

1. Enlace al repositorio.
2. Captura de `git log --all --graph --oneline`.
3. Sección al final del README del repo con: integrantes, pruebas que hizo cada uno y conflictos resueltos.

---
