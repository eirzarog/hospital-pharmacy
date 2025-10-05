# Hospital Pharmacy Management System API Documentation

## Overview
This API enables hospitals to manage pharmacy operations. 
---
 
## Endpoints

### 1. Get All Drugs

`GET http://localhost:8080/api/drugs`

**Response**
```json
[
  {
    "id": 1,
    "name": "Amoxicillin",
    "code": "AMX-500",
    "price": 12.50,
    "stock": 230,
    "categoryId": 1,
    "categoryName": "Antibiotics",
    "description": "Broad-spectrum antibiotic",
    "manufacturer": "PharmaCorp",
    "createdAt": null,
    "updatedAt": null
  },
  {
    "id": 2,
    "name": "Ibuprofen",
    "code": "IBU-200",
    "price": 5.75,
    "stock": 200,
    "categoryId": 2,
    "categoryName": "Analgesics",
    "description": "Non-steroidal anti-inflammatory drug",
    "manufacturer": "HealthGen",
    "createdAt": null,
    "updatedAt": null
  }
]
```

### 2. Add New Drug

`POST http://localhost:8080/api/drugs`

**Request Body**
```json
{
  "name": "Isopropyl Alcohol",
  "code": "IPA-70",
  "price": 4.50,
  "stock": 120,
  "categoryId": 3,
  "categoryName": "Antiseptics",
  "description": "70% isopropyl alcohol solution for disinfection",
  "manufacturer": "SteriPure"
  }
```

### 3. Get All Stock Movements

`GET http://localhost:8080/api/stock-movements`

### 4. Add Stock Movement

`POST http://localhost:8080/api/stock-movements`

**Request Body**
```json
{
  "drugId":1,
  "performedBy": 1,
  "movementType": "OUTBOUND",
  "quantity": 20
}
```



