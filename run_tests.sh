#!/bin/bash
# Simple test runner for MOOD analyzer

# Use venv Python if available, otherwise use system Python
if [ -f "/home/VxDxK/PyVenv/bin/python3" ]; then
    PYTHON="/home/VxDxK/PyVenv/bin/python3"
else
    PYTHON="python3"
fi

echo "Running MOOD Metrics Analyzer on test examples..."
echo "Using Python: $PYTHON"
echo ""

for test_file in test/example_*.cpp; do
    if [ -f "$test_file" ]; then
        echo "=========================================="
        echo "Analyzing: $test_file"
        echo "=========================================="
        $PYTHON mood_analyzer.py "$test_file"
        echo ""
    fi
done

