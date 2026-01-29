#!/usr/bin/env python
from jinja2 import Environment, FileSystemLoader

def generate():
    env = Environment(loader=FileSystemLoader('.'), extensions=['jinja2.ext.do'])
    template = env.get_template('webserver.j2')

    algorithms = ['fair', 'unfair']
    num_threads = 2  # 1-3
    queue_length = 3  # 1-5
    max_clients = 6
    max_service_time = 3
    max_wait_time = 10

    for algo in algorithms:
        filename = f"webserver_{algo}.smv"
        with open(filename, 'w') as f:
            f.write(template.render(
                num_threads=num_threads,
                queue_length=queue_length,
                max_clients=max_clients,
                max_service_time=max_service_time,
                max_wait_time=max_wait_time,
                algorithm=algo
            ))
        print(f"Generated {filename}")

if __name__ == "__main__":
    generate()
