# Inventario Microservicio

## POST /inventario
Crea un inventario nuevo.

### Body JSON
```json
{
  "idProducto": 1,
  "cantidadDisponible": 100,
  "ubicacionBodega": "Almacén 1"
}
```

## PUT /inventario/{idInventario}
Actualiza un inventario existente.

### Body JSON
```json
{
  "idProducto": 1,
  "cantidadDisponible": 120,
  "ubicacionBodega": "Almacén 2"
}
```
