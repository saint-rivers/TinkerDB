#!/bin/bash

docker run -itd --name ${5} -e POSTGRES_USER=${2} -e POSTGRES_PASSWORD=${3} -e POSTGRES_DB=${4} -p "5433:5432" postgres:${1}
