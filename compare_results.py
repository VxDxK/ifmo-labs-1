#!/usr/bin/env python3

import subprocess
import sys
from tabulate import tabulate

expected = {
    'example_mhf.cpp': {'MHF': 0.5000, 'AHF': None, 'MIF': None, 'AIF': None, 'POF': None, 'COF': None},
    'example_ahf.cpp': {'MHF': None, 'AHF': 0.5714, 'MIF': None, 'AIF': None, 'POF': None, 'COF': None},
    'example_mif.cpp': {'MHF': None, 'AHF': None, 'MIF': 0.6000, 'AIF': None, 'POF': None, 'COF': None},
    'example_aif.cpp': {'MHF': None, 'AHF': None, 'MIF': None, 'AIF': 0.6000, 'POF': None, 'COF': None},
    'example_pof.cpp': {'MHF': None, 'AHF': None, 'MIF': None, 'AIF': None, 'POF': 0.2857, 'COF': None},
    'example_cof.cpp': {'MHF': None, 'AHF': None, 'MIF': None, 'AIF': None, 'POF': None, 'COF': 0.2500},
}

def parse_output(output):
    metrics = {}
    lines = output.split('\n')
    for line in lines:
        if '|' in line and 'Metric' not in line and 'Value' not in line and '=' not in line:
            parts = [p.strip() for p in line.split('|') if p.strip()]
            if len(parts) == 2:
                try:
                    metric = parts[0]
                    value = float(parts[1])
                    metrics[metric] = value
                except:
                    pass
    return metrics

def compare_results():
    results = []
    for test_file, expected_vals in expected.items():
        test_path = f'test/{test_file}'
        try:
            result = subprocess.run(
                ['/home/VxDxK/PyVenv/bin/python3', 'mood_analyzer.py', test_path],
                capture_output=True,
                text=True,
                timeout=10
            )
            actual_metrics = parse_output(result.stdout)
            for metric in ['MHF', 'AHF', 'MIF', 'AIF', 'POF', 'COF']:
                expected_val = expected_vals.get(metric)
                if expected_val is not None:
                    actual_val = actual_metrics.get(metric, 0.0)
                    diff = abs(actual_val - expected_val)
                    match = "✓" if diff < 0.0001 else "✗"
                    
                    results.append({
                        'Test File': test_file,
                        'Metric': metric,
                        'Expected': f"{expected_val:.4f}",
                        'Actual': f"{actual_val:.4f}",
                        'Match': match,
                        'Diff': f"{diff:.4f}" if diff >= 0.0001 else "0.0000"
                    })
        except Exception as e:
            results.append({
                'Test File': test_file,
                'Metric': 'ERROR',
                'Expected': 'N/A',
                'Actual': str(e),
                'Match': '✗',
                'Diff': 'N/A'
            })
    print("\n" + "="*80)
    print("MOOD Metrics Test Results Comparison")
    print("="*80 + "\n")
    print(tabulate(results, headers="keys", tablefmt="grid"))
    print("\n" + "="*80)
    total = len([r for r in results if r['Match'] != '✗' and r['Metric'] != 'ERROR'])
    passed = len([r for r in results if r['Match'] == '✓'])
    failed = len([r for r in results if r['Match'] == '✗'])
    
    print(f"\nSummary: {passed} passed, {failed} failed out of {total} tests")
    print("="*80 + "\n")
    
    return passed == total

if __name__ == "__main__":
    success = compare_results()
    sys.exit(0 if success else 1)

