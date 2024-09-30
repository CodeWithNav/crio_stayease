# StayEase Hotel Booking API

## Overview
StayEase is a simplified hotel booking API built using Spring Boot. This API facilitates room bookings for a hotel management aggregator application, allowing users to register, login, manage hotel details, and book rooms.

## Key Features
- User Registration and Login with JWT authentication
- Role-based access (CUSTOMER, HOTEL MANAGER, ADMIN)
- Hotel management (create, update, delete, and view hotels)
- Room booking and cancellation
- MySQL database for data persistence

## Technologies Used
- Java 11
- Spring Boot
- Spring Security (JWT)
- MySQL
- JUnit 5
- Mockito
- Lombok (optional for boilerplate code reduction)

## API Endpoints

### Authentication Endpoints
- **POST** `/auth/register`: Register a new user
- **POST** `/auth/login`: Authenticate and get a JWT token

### Hotel Management Endpoints
- **GET** `/hotels`: Retrieve all hotels
- **POST** `/hotels`: Create a new hotel (ADMIN only)
- **PUT** `/hotels/{hotelId}`: Update hotel details (HOTEL MANAGER only)
- **DELETE** `/hotels/{hotelId}`: Delete a hotel (ADMIN only)

### Booking Management Endpoints
- **POST** `/hotels/{hotelId}/book`: Book a room (CUSTOMER only)
- **DELETE** `/hotels/bookings/{bookingId}`: Cancel a booking (HOTEL MANAGER only)

## Database Schema
- **User**: `id`, `email`, `password`, `firstName`, `lastName`, `role`
- **Hotel**: `id`, `name`, `location`, `description`, `availableRooms`
- **Booking**: `id`, `userId`, `hotelId`, `bookingDate`

## Installation and Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/stayease-api.git
   cd stayease-api

2. **Configure MySQL Database**
3. **Run the Application**
4. **Test the API Endpoints**
5. **Explore the API Documentation**
6. POSTMAN Collection Link: [StayEase API](https://www.postman.com/telecoms-astronomer-41589421/workspace/crio/collection/33264336-7722d419-39a6-447a-a1c4-4347dcb3c268?action=share&creator=33264336)

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/33264336-7722d419-39a6-447a-a1c4-4347dcb3c268?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D33264336-7722d419-39a6-447a-a1c4-4347dcb3c268%26entityType%3Dcollection%26workspaceId%3D93ffd489-3e02-4aec-9693-c4f3cc8777e1)