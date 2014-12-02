#!/bin/bash

type=$1
infile=$2
outfile=$3
folder=`pwd`
echo "remove ${type} resources..."
echo > ${outfile}

while read res; do 
    name=${res##*:}
    while read line; do
        if [ -f ${line} ]; then
            file=${line#${folder}/}
            echo "remove file ${file}"
            echo ${file}  `git log -1 --pretty="format:[%an][%aE][%h][%ci]%n" ${line}` >> ${outfile}
            rm ${line}
        fi
    done < <(grep "^${type}" ${infile} -B1 | grep ${name} | grep "^[[:blank:]]*/")
done < <(grep "^${type}" ${infile})
