#!/usr/bin/env python3

import csv
import requests
import gzip


# SANITIZING... removing ' and \n from tsv file
def sanitize(row):
    array = []
    for data in row:
        if data != '\\N':
            array.append(data.replace("'", ""))
        else:
            array.append('null')
    array[0] = array[0]
    return array

def download_data():
    print("Downloading resources...")
    url = 'https://datasets.imdbws.com/title.basics.tsv.gz'
    myfile = requests.get(url)
    open('title.basics.tsv.gz', 'wb').write(myfile.content)
    with gzip.open("title.basics.tsv.gz", "rb") as f:
        with open("imdb_data.tsv", "wb") as file:
            file.write(f.read())


def eval(row):
    return (row[1] != 'movie' and row[4] != 0) or row[5] == 'null'


# FILTERING... including just movies and not adult movies
def filter(row):
    array = [None for _ in range(5)]
    id = row[0][2:]
    if eval(row):
        return []
    array[0] = int(id)  # ID
    array[1] = row[2]  # Title
    array[2] = int(row[5])  # Year
    array[3] = row[7]  # length
    array[4] = row[8]  # genres
    return array


def eval_dev(row):
    return (row[1] != 'movie' and row[4] != 0) or row[5] == 'null' or int(row[5]) < 2000


def filter_dev(row):
    array = [None for _ in range(5)]
    id = row[0][2:]
    if eval_dev(row):
        return []
    array[0] = int(id)  # ID
    array[1] = row[2]  # Title
    array[2] = int(row[5])  # Year
    array[3] = row[7]  # length
    array[4] = row[8]  # genres
    return array


def create_schema(file):
    command = "CREATE TABLE movies (id int PRIMARY KEY UNIQUE, title varchar, year int, length int, genres varchar); "
    file.write(command)


def insert_data(file, filter_function):
    insert = "INSERT INTO public.movies(id, title, year, length, genres) VALUES"
    file.write(insert)
    values = " ({}, N'{}', {}, {}, '{}')"
    with open("imdb_data.tsv", encoding='utf-8') as fd:
        first = False
        progress = True
        rd = csv.reader(fd, delimiter="\t")
        next(rd)
        for row in rd:
            data = sanitize(row)
            data = filter_function(data)
            if len(data) == 5:
                if first:
                    file.write(", ")
                else:
                    first = True
                file.write(values.format(data[0], data[1], data[2], data[3], data[4]))
                if progress:
                    print("\r/", end='')
                    progress = not progress
                else:
                    print("\r\\", end='')
                    progress = not progress
        print("\nWrite done.")
        file.write(";")


def main():
    print("Creating SQL:")
    download_data()
    with open("import.sql", "w", encoding='utf-8') as file:
        create_schema(file)
        insert_data(file, filter_dev)
    with open("import.prod.sql", "w", encoding='utf-8') as file:
        create_schema(file)
        insert_data(file, filter)
    print("SQL Created...")

if __name__ == '__main__':
    main()