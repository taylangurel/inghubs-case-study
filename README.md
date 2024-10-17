# Spring Boot API Application

This project is a Spring Boot application packaged as an executable JAR file. The JAR file can be downloaded from the GitHub releases and run locally.

## Download and Run

### Step 1: Download the JAR
1. Navigate to the [Releases](https://github.com/taylangurel/inghubs-case-study/releases) section of this repository.
2. Find the initial release and download the `.jar` file.

### Step 2: Run the JAR
1. Ensure that [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) is installed on your machine.
2. Open a terminal or command prompt and navigate to the directory where the JAR file was downloaded.
3. Run the following command to start the application:

    ```bash
    java -jar casestudy-0.0.1-SNAPSHOT.jar
    ```

4. Once the application is running, you can access the API via `http://localhost:8080`.

## API Endpoints Overview

## 1. Register Customer
**Endpoint**: `POST /api/auth/register`
- **Description**: Registers a new customer.
- **Example Request**:
  ```json
  {
    "username": "customer1",
    "password": "password123"
  }
  ```
- **Example Response**:
  ```
  "Registration successful"
  ```

## 2. Deposit Money
**Endpoint**: `POST /api/money/deposit`
- **Description**: Deposits money into the customer's account.
- **Example Request**:
  ```
  http://localhost:8080/api/money/deposit?amount=XXX
  ```
- **Example Response**:
  ```
  "Deposit successful"
  ```

## 3. Withdraw Money
**Endpoint**: `POST /api/money/withdraw`
- **Description**: Withdraws money from the customer's account.
- **Example Request**:
  ```
  http://localhost:8080/api/money/withdraw?amount=XXX
  ```
- **Example Response**:
  ```
  "Withdraw successful"
  ```

## 4. Create Order
**Endpoint**: `POST /api/orders/create`
- **Description**: Creates a buy or sell order for a specified asset.
- **Example Request**:
  ```json
  {
    "assetName": "AAPL",
    "orderSide": "BUY",
    "price": 100,
    "size": 10
  }
  ```
- **Example Response**:
  ```
  "Order created successfully"
  ```

## 5. List Orders
**Endpoint**: `GET /api/orders/list`
- **Description**: Lists all orders for the authenticated customer.
- **Example Response**:
  ```json
  [
    {
      "orderId": 1,
      "assetName": "AAPL",
      "orderSide": "BUY",
      "price": 100,
      "size": 10,
      "status": "PENDING"
    }
  ]
  ```

## 6. Delete Order
**Endpoint**: `DELETE /api/orders/delete/{orderId}`
- **Description**: Deletes an order by ID.
- **Example Response**:
  ```
  "Order successfully deleted"
  ```

## 7. Match Orders (Admin)
**Endpoint**: `POST /admin/orders/match`
- **Description**: Matches pending orders (admin-only).
- **Example Response**:
  ```
  "Pending orders have been matched successfully."
  ```

