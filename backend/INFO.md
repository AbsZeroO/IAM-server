# Notes for future me :D

1. IAM server is using asymmetric key to verify JWT tokens (IAM has private key and other apps can call to get public key from end point `/.well-known/jwks.json`)
2. User will have refresh token in Redis database in `/api/auth/refresh`
3. 