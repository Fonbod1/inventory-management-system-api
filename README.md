
# 📦 Inventory Management System API

This is a fully featured **Inventory Management System API** that handles **sales and purchase operations** for businesses.

It supports user authentication, role-based access control, and allows CRUD operations based on assigned privileges.

---

## 🔧 Technologies Used

This project is built using:

- 💻 **IntelliJ IDEA**
- 🐘 **PostgreSQL**
- 🧪 **H2 Database** (for testing)
- 🐧 **Ubuntu**
- ☕ **Spring Boot (Java)**

---

## 🚀 How to Use the API

All API endpoints are protected by role-based authorization. Follow the steps below to interact with the system.

---

### 1️⃣ Register a New User

- Endpoint: `POST http://localhost:8085/api/v1/register`
- **Required Fields**: `firstName`, `username`, `password`
- Set **Authorization Type** to **No Auth** for this request.
  
📸 Example:
![Registration](https://github.com/Fonbod1/inventory-management-system-api/blob/main/src/main/resources/photos/Registration.png?raw=true)

---

### 2️⃣ Login

- Endpoint: `POST http://localhost:8085/api/v1/login`
- Provide your `username` and `password`.
- You’ll receive a **JWT Token** upon successful login.
- This **token must be used** to authorize all future requests (as a Bearer token).

📸 Example:
![Login](https://github.com/Fonbod1/inventory-management-system-api/blob/main/src/main/resources/photos/login.png?raw=true)

---

### 3️⃣ Role Management

#### 🔍 View All Roles

- Endpoint: `GET http://localhost:8085/api/v1/roles`
- Use **Authorization Type: Bearer Token**
- Paste the token received during login.

📸 Example:
![All Roles](https://github.com/Fonbod1/inventory-management-system-api/blob/main/src/main/resources/photos/All%20Roles.png?raw=true)

#### ➕ Assign a Role to a User

- Endpoint: `POST http://localhost:8085/role/{roleid}/assign/user/{userid}`
- Required: Replace `{roleid}` and `{userid}` with actual values.
- Requires Bearer Token authentication.

📸 Example:
![Assign Role](https://github.com/Fonbod1/inventory-management-system-api/blob/main/src/main/resources/photos/Assign%20Role%20To%20User.png?raw=true)

---

### 4️⃣ Access Other Resources

Once a user is assigned a role, they can access other API endpoints based on their role's privileges.

#### ✅ Example: View All Brands

- Endpoint: `GET http://localhost:8085/api/v1/brands`
- Authorization: Bearer Token (paste your token)

📸 Example:
![View Brands](https://github.com/Fonbod1/inventory-management-system-api/blob/main/src/main/resources/photos/View%20All%20Brand.png?raw=true)

---

## 📘 API Documentation with Swagger

Swagger UI provides interactive documentation of the API.

> Access it at:  
> 👉 [`http://localhost:8085/api/v1/swagger-ui/index.html`](http://localhost:8085/api/v1/swagger-ui/index.html)

---

## 📌 Notes

- Always use your **JWT Token** as a **Bearer Token** in the `Authorization` header when accessing protected routes.
- Roles define user capabilities (e.g., View Products, Manage Inventory, etc.).

---

## 📄 License

This project is provided for learning and demonstration purposes. Feel free to adapt it for your needs.

---

✅ Built with ❤️ using Spring Boot
