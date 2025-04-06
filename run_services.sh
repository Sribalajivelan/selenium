#!/bin/bash

# Start Selenium in the background
/opt/bin/entry_point.sh &

# Wait for Selenium to be ready
sleep 10

# Run Maven tests
mvn test
