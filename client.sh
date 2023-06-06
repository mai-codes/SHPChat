#!/bin/bash
host_name=$1
port_number=$2
  
# First, compile the Java programs into Java classes
javac Client.java
  
# Now pass the arguments to the Java classes
java Client ${host_name} ${port_number}