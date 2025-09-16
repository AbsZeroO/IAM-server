# IAM-server

## Purpose
This server provides **centralized authentication and authorization** for multiple applications.  
It acts as a unified entry point for user identity, login, and access control.  
Example use case: ShelfWatch application and other apps sharing the same user accounts.

## Features (Planned)
- User registration and login (**Authentication**)
- Multi-application authentication support (**SSO – Single Sign-On**)
- Secure password storage (BCrypt)
- Token-based authentication (**JWT, OAuth2/OIDC flows**)
- Role-based access control (**Authorization**)
- Integration with external identity providers (e.g. Google OAuth)
- Centralized user and role management
- Built with Spring Boot

## Getting Started
> This is an initial version. Setup instructions will be expanded as development progresses.

### Prerequisites
- Java 21
- Spring Boot
- Database: PostgreSQL
- `.env` file with configuration, for example:

```env
# Database configuration
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=authdb

# Application DB user
APP_USER=auth_service
APP_PASSWORD=auth_service
```

- Create RSA private key for JWT. (Add to `resources/cert`)

```bash
# Generating private key RSA 2048-bit. Length can be increased (e.g. 3072/4096) for stronger security
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in private_key.pem -out public_key.pem
```




### Running Locally
1. Start a PostgreSQL database (e.g. using Docker):
   ```bash
   docker compose up -d
   ```
2. Export environment variables from `.env` (or configure in your IDE).
3. Run the application:
   ```bash
   ./graldew bootRun
   ```

## Usage
> Usage instructions and API endpoints will be added as functionality is implemented.

## License
MIT License