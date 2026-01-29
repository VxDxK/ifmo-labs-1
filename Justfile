generate:
    python generator.py

run-fair:
    ~/Downloads/nuXmv-2.1.0-linux64/bin/nuXmv webserver_fair.smv

run-unfair:
    ~/Downloads/nuXmv-2.1.0-linux64/bin/nuXmv webserver_unfair.smv

all: generate run-fair
