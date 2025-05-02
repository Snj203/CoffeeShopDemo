# Coffee Shop Project - Security Documentation

## Security Architecture Overview

The application implements a comprehensive security system with the following components:

1. **JWT-based Authentication**:
   - Custom JWT implementation with access and refresh tokens
   - Token expiration times configurable via properties
   - HMAC-SHA256 signature verification

2. **Authorization**:
   - Role-based access control (RBAC)
   - Three roles: ADMIN, USER, VIEWER
   - URL pattern-based permission rules

3. **Two-Factor Authentication**:
   - Optional 2FA for enhanced security
   - Code generation and verification flow

4. **Session Management**:
   - Stateless design with JWT
   - Cookie-based token storage for web clients
   - Header-based token for API clients

5. **OAuth2 Integration**:
   - Social login support
   - Configurable success/failure handlers

## Security Components

### 1. WebSecurityApplicationConfig
The main security configuration class that sets up:
- HTTP security rules
- Authentication/authorization filters
- CSRF protection (disabled for API endpoints)
- Session management (stateless)
- OAuth2 login configuration
- Exception handling for access denied scenarios

### 2. CustomAuthenticationFilter
Handles:
- Username/password authentication
- JWT token generation (access + refresh)
- 2FA initiation
- Response handling for both web and API clients

### 3. CustomAuthorizationFilter
Responsible for:
- JWT token verification
- Role extraction and authority setup
- Request authorization
- Error handling for invalid/expired tokens

### 4. CustomJwtHelper
Core JWT operations:
- Token creation with claims
- Signature verification
- Token validation
- Refresh token management

## Security Testing Guide

### 1. Authentication Testing

#### Web Interface:
1. Navigate to `/login`
2. Attempt login with:
   - Valid credentials (should redirect to success page)
   - Invalid credentials (should redirect to fail page)
   - 2FA-enabled accounts (should redirect to verification page)

#### API Endpoints:
```bash
# Successful login
curl -X POST -d "username=user&password=pass" http://localhost:8080/login

# Failed login
curl -X POST -d "username=wrong&password=wrong" http://localhost:8080/login


# Using refresh token to get new access token
curl -H "Authorization: Bearer <REFRESH_TOKEN>" http://localhost:8080/api/auth/refresh

2FA Testing
Enable 2FA for a test account with h2 console after start
Attempt login:
Should receive verification code
Verify with correct/incorrect codes
Check time-based expiration of codes

