forge reset

psql -h postgresql -U postgres -c 'CREATE DATABASE "'${ENVIRONMENT_NAME}'-moli-dashboard";'

psql -h postgresql -d ${ENVIRONMENT_NAME}-moli-dashboard -U postgres -f dump.sql
