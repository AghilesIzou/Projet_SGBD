#!/bin/sh

javac -d Code/MiniSGBD_IZOUAOUEN/bin/up_info_bdda_iz_ha Code/MiniSGBD_IZOUAOUEN/src/up_info_bdda_iz_ha/*.java

java -cp Code/MiniSGBD_IZOUAOUEN/bin/up_info_bdda_iz_ha up_info_bdda_iz_ha/Main DB
