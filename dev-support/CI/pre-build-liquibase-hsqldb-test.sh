#! /bin/bash
cd ./dev-support/db-scripts/
sh ./liquibase-2.0.2-bin/liquibase --defaultsFile=./liquibase-test.properties update