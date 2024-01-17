# API Template

## Description

This is an api to manage a stock in a store

## Base URL

The base URL for all API requests is:

`https://localhost:8080/

## Endpoints

### `GET /stock/`

Returns a list of registered stock in all stores

### Parameters

Not required (Requires limit and offset implenetation)

### Response

Returns a JSON list with Stock objects with the following properties:
- stockId: Unique identifier of the Stock
- product: Product object with the folowwing properties:
    - `productId`: The unique identifier of the product
    - `name`: The name of the product
    - `brand`: The brand of the product
    - `model`: The model of the product
    - `price`: The price of the product
    - `sku`: The sku code of the product
- store: Store object with the folowwing properties:
    - `storeId`: The unique identifier of the store
    - `name`: The name of the store
    - `address`: The address of the store
    - `phone`: The phone of the store
    - `city`: The city of the store
    - `state`: The state code of the store
    - `zipCode`: The sku code of the store
- quantity: Amount of products of available in the store

### Example

Request:

```
GET /stock/
```

Response:

```json

    {
        "stockId": 1001,
        "product": {
            "productId": 1001,
            "name": "Product 1",
            "brand": "Brand 1",
            "model": "Model 1",
            "price": 50,
            "sku": "SKU1"
        },
        "store": {
            "storeId": 1001,
            "name": "Store 1",
            "address": "123 Main St",
            "phone": "555-1234",
            "city": "City1",
            "state": "State1",
            "zipCode": "12345"
        },
        "quantity": 10
    },
    {
        "stockId": 1002,
        "product": {
            "productId": 1002,
            "name": "Product 2",
            "brand": "Brand 2",
            "model": "Model 2",
            "price": 75,
            "sku": "SKU2"
        },
        "store": {
            "storeId": 1002,
            "name": "Store 2",
            "address": "456 Oak St",
            "phone": "555-5678",
            "city": "City2",
            "state": "State2",
            "zipCode": "56789"
        },
        "quantity": 20
    },

```
### `GET /stock/store_id/{storeId}/`

Returns a list of products available in the specified store

### Parameters

- `storeId`: Unique identifier of the store

### Response

Returns a JSON list with Stock objects with the following properties:
- stockId: Unique identifier of the Stock
- product: Product object with the folowwing properties:
    - `productId`: The unique identifier of the product
    - `name`: The name of the product
    - `brand`: The brand of the product
    - `model`: The model of the product
    - `price`: The price of the product
    - `sku`: The sku code of the product
- store: Store object with the folowwing properties:
    - `storeId`: The unique identifier of the store
    - `name`: The name of the store
    - `address`: The address of the store
    - `phone`: The phone of the store
    - `city`: The city of the store
    - `state`: The state code of the store
    - `zipCode`: The sku code of the store
- quantity: Amount of products of available in the store

### Example

Request:

```
GET /stock/store_id/1001/
```

Response:

```json

    {
        "stockId": 1001,
        "product": {
            "productId": 1001,
            "name": "Product 1",
            "brand": "Brand 1",
            "model": "Model 1",
            "price": 50,
            "sku": "SKU1"
        },
        "store": {
            "storeId": 1001,
            "name": "Store 1",
            "address": "123 Main St",
            "phone": "555-1234",
            "city": "City1",
            "state": "State1",
            "zipCode": "12345"
        },
        "quantity": 10
    },
    {
        "stockId": 1002,
        "product": {
            "productId": 1002,
            "name": "Product 2",
            "brand": "Brand 2",
            "model": "Model 2",
            "price": 75,
            "sku": "SKU2"
        },
        "store": {
            "storeId": 1001,
            "name": "Store 1",
            "address": "123 Main St",
            "phone": "555-1234",
            "city": "City1",
            "state": "State1",
            "zipCode": "12345"
        },
        "quantity": 20
    },

```

### `GET /store_id/{storeId}/product_id/{productId}/`

Returns the quantity of product available in a store

### Parameters

- `storeId`: Unique identifier of the store
- `productId`: Unique identifier of the product

### Response

Returns an integer number

### Example

Request:

```
GET /store_id/1001/product_id/1001/
```

Response:

```json
10
```
### `POST /stock/`

Saves a new stock in a store

### Parameters

- `Stock`: A Store object with the following properties:
  - `storeId`: Store unique identifier
  - `productId`: Product unique identifier
  - `quantity`: Quantity of product to add

### Response

Returns a JSON object with the following properties:

- `Stock`: The Stock object saved in the database with the following data
  - stockId: Unique identifier of the Stock
  - product: Product object with the folowwing properties:
      - `productId`: The unique identifier of the product
      - `name`: The name of the product
      - `brand`: The brand of the product
      - `model`: The model of the product
      - `price`: The price of the product
      - `sku`: The sku code of the product
  - store: Store object with the folowwing properties:
      - `storeId`: The unique identifier of the store
      - `name`: The name of the store
      - `address`: The address of the store
      - `phone`: The phone of the store
      - `city`: The city of the store
      - `state`: The state code of the store
      - `zipCode`: The sku code of the store
  - quantity: Amount of products of available in the store

### Example

Request:

```
POST /stock/
Content-Type: application/json

{
  "storeId": 1001,
  "productId": 1001,
  "quantity": 50
}

```

Response:

```json
{
    "stockId": 1,
    "product": {
        "productId": 1001,
        "name": "Product 1",
        "brand": "Brand 1",
        "model": "Model 1",
        "price": 75,
        "sku": "SKU1"
    },
    "store": {
        "storeId": 1001,
        "name": "Store 1",
        "address": "456 Oak St",
        "phone": "555-5678",
        "city": "City1",
        "state": "State1",
        "zipCode": "56789"
    },
    "quantity": 10
}

```
### `PUT /stock/`

Updates a stock in the database

### Parameters

- `storeId`: Store unique identifier
- `productId`: Product unique identifier
- `quantity`: Quantity of product to add

### Response

Returns a JSON object with the following properties:

- `Stock`: The Stock object saved in the database with the following data
  - stockId: Unique identifier of the Stock
  - product: Product object with the folowwing properties:
      - `productId`: The unique identifier of the product
      - `name`: The name of the product
      - `brand`: The brand of the product
      - `model`: The model of the product
      - `price`: The price of the product
      - `sku`: The sku code of the product
  - store: Store object with the folowwing properties:
      - `storeId`: The unique identifier of the store
      - `name`: The name of the store
      - `address`: The address of the store
      - `phone`: The phone of the store
      - `city`: The city of the store
      - `state`: The state code of the store
      - `zipCode`: The sku code of the store
  - quantity: Amount of products of available in the store

### Example

Request:

```
POST /stock/
Content-Type: application/json

{
  "storeId": 1001,
  "productId": 1001,
  "quantity": 99
}

```

Response:

```json
{
    "stockId": 1,
    "product": {
        "productId": 1001,
        "name": "Product 1",
        "brand": "Brand 1",
        "model": "Model 1",
        "price": 75,
        "sku": "SKU1"
    },
    "store": {
        "storeId": 1001,
        "name": "Store 1",
        "address": "456 Oak St",
        "phone": "555-5678",
        "city": "City1",
        "state": "State1",
        "zipCode": "56789"
    },
    "quantity": 99
}

```
### `DELETE /stock/`

Deletes a stock in the database

### Parameters

- `storeId`: Store unique identifier
- `productId`: Product unique identifier

### Response

Returns a String with the status of the operation

### Example

Request:

```
DELETE /stock/
Content-Type: application/json

{
  "storeId": 1001,
  "productId": 1001
}
```

Response:

```json
Stock deleted successfully

```

## Errors
**Note: To be updated, ResponseEntity what kind of error throws?**
This API uses the following error codes:

- `400 Bad Request`: The request was malformed or missing required parameters.
- `401 Unauthorized`: The API key provided was invalid or missing.
- `404 Not Found`: The requested resource was not found.
- `500 Internal Server Error`: An unexpected error occurred on the server.
