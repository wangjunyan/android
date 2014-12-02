#!/bin/bash
# source dex_utils.sh //export the functions
# set | grep xxx //check current functions in shell
# dx --dex --output=temp.dex library.jar //convert jar to dex first
# dexdump -f MyApp.apk | grep method_ids_size //check the apk directly

function dex-method-count() {
  cat $1 | head -c 92 | tail -c 4 | hexdump -e '1/4 "%d\n"'
}

function dex-method-count-by-package() {
  dir=$(mktemp -d -t dexXXX)
  baksmali $1 -o $dir
  for pkg in `find $dir/* -type d`; do
    smali $pkg -o $pkg/classes.dex
    count=$(dex-method-count $pkg/classes.dex)
    name=$(echo ${pkg:(${#dir} + 1)} | tr '/' '.')
    echo -e "$count\t$name"
  done
  rm -rf $dir
}
