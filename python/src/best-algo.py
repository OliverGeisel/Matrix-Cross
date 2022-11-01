import time

import numpy as np

dim = 0
step = 32
max_dim = 2048 * 4
results = list()
header = """Ergebnisse der Matrixmultiplikation mit Python
Berechnung mit einfacher Multiplikation (in O(n³)) und numpy

"""


def set_dim():
    global dim, step
    if dim < 256:
        dim += step
    elif dim < 512:
        dim += step * 2
    elif dim < 1024:
        dim += step * 4
    elif dim < 2048:
        dim += step * 8
    elif dim < 4096:
        dim += step * 16
    else:
        dim += step * 32


def collect_info(t_start, t_end, gflops):
    global results
    string = f"Dim: {dim} runtime: {round(t_end - t_start, 6)}s GFLOP/s: {round(gflops, 5)}"
    print(string)
    results.append(string + "\n")


def random_mat(dim_of_mat) -> np.ndarray:
    return 200 * np.random.rand(dim_of_mat, dim_of_mat) - 100


def zero_mat(dim_of_mat):
    return np.zeros((dim_of_mat, dim_of_mat))


def calc():
    try:
        while dim < max_dim:
            set_dim()
            mat_A = random_mat(dim)
            mat_B = random_mat(dim)
            mat_C = zero_mat(dim)

            if None is [mat_A, mat_B, mat_C]:
                print("Allocation of matrix failed.\n")
                exit("EXIT_FAILURE")
            time.sleep(1)

            start = time.perf_counter()
            mat_C = mat_A.dot(mat_B)

            end = time.perf_counter()
            gflops = ((2 * dim ** 3) / 1_000_000_000.0) / (np.inf if (end - start) == 0.0 else end - start)
            collect_info(start, end, gflops)
    except:
        pass
    finally:
        # collect results in one output File
        with open("Ergebnisse_python-best-algo.txt", "w") as output:
            output.writelines(header)
            output.writelines(results)


def matrix():
    calc()


if __name__ == "__main__":
    matrix()
