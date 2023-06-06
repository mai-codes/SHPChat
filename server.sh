#!/bin/bash
port_number=$1
  
# First, compile the Java programs into Java classes
javac Server.java
  
# Now pass the arguments to the Java classes
java Server ${port_number}