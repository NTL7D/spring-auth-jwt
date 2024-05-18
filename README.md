for .env usage:
```yaml
# application:
SV_NAME= application name (default: server)
SV_PORT= application port (default: 8080)
SV_PREFIX= api prefix endpoint (ex: localhost:5000/{prefix}/{controller_endpoint})
# database:
DB_USER= db username
DB_PASS= db password
DB_PORT= db port
DB_NAME= name of db
# pgadmin4:
PG_EMAIL= pgadmin email
PG_PASS= pgadmin password
PG_PORT= pgadmin port
# jwt:
JWT_SECRET= secret key to generate jwt token
# (in terminal, use node, require("crypto").randomBytes(key length).toString("hex"))

```
