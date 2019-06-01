#!/usr/bin/env bash

# usage: file_env VAR [DEFAULT]
#    ie: file_env 'XYZ_DB_PASSWORD' 'example'
# (will allow for "$XYZ_DB_PASSWORD_FILE" to fill in the value of
#  "$XYZ_DB_PASSWORD" from a file, especially for Docker's secrets feature)
file_env() {
	local var="$1"
	local fileVar="${var}_FILE"
	local def="${2:-}"
	if [ "${!var:-}" ] && [ "${!fileVar:-}" ]; then
		echo >&2 "error: both $var and $fileVar are set (but are exclusive)"
		exit 1
	fi
	local val="$def"
	if [ "${!var:-}" ]; then
		val="${!var}"
	elif [ "${!fileVar:-}" ]; then
		val="$(< "${!fileVar}")"
	fi
	export "$var"="$val"
	unset "$fileVar"
}

file_env 'MYSQL_USER_NAME'
file_env 'MYSQL_USER_PASSWORD'
file_env 'MYSQL_DB_NAME'
file_env 'JWT_ACCESS_TOKEN_SIGNING_KEY'
file_env 'AUTHORIZATION_CLIENT_ID'
file_env 'AUTHORIZATION_CLIENT_SECRET'
file_env 'ACCESS_TOKEN_VALIDITY_SECONDS'
file_env 'ACCESS_TOKEN_VALIDITY_SECONDS'
file_env 'REFRESH_TOKEN_VALIDITY_SECONDS'
file_env 'BCRYPT_PASSWORD_ENCODER_ROUNDS_NUMBER'

java -jar /opt/authorizationServer-0.0.1-SNAPSHOT.jar

exec "$@"
