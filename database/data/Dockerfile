FROM python:3.8-slim as builder

COPY ./generate_ddl.py /usr/src/app/generate_ddl.py
COPY ./requirements.txt /usr/src/app/requirements.txt
WORKDIR /usr/src/app
RUN pip install --no-cache-dir -r requirements.txt
RUN [ "python", "./generate_ddl.py" ]

FROM postgres
COPY --from=builder /usr/src/app/import.prod.sql /docker-entrypoint-initdb.d/movies.sql
