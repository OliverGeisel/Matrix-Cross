import random
import time

dim = 0
step = 32
max_dim = 2048 * 4
results = list()
header = """Ergebnisse der Matrixmultiplikation mit Python
Berechnung mit einfacher Multiplikation (in O(n³)) und liste von listen

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
    string = f"Dim: {dim} runtime: {round(t_end - t_start, 2)}s GFLOP/s: {round(gflops, 5)}"
    print(string)
    results.append(string + "\n")


def random_mat(dim_of_mat):
    return [[random.uniform(-100, 100) for i in range(dim_of_mat)] for i in range(dim_of_mat)]


def zero_mat(dim_of_mat):
    return [[0] * dim_of_mat] * dim_of_mat


def calc():
    while dim < max_dim:
        set_dim()
        mat_A = random_mat(dim)
        mat_B = random_mat(dim)
        mat_C = zero_mat(dim)
        size = range(dim)

        if None in [mat_A, mat_B, mat_C]:
            print("Allocation of matrix failed.\n")
            exit("EXIT_FAILURE")
        time.sleep(1)

        start = time.time()

        # Begin matrix matrix multiply kernel
        for i in size:
            for k in size:
                for j in size:
                    # C[i][j] += A[i][k] * B[k][j]
                    mat_C[i][j] += mat_A[i][k] * mat_B[k][j]
        # End matrix matrix multiply kernel

        end = time.time()
        gflops = ((2 * dim ** 3) / 1_000_000_000.0) / (end - start)
        collect_info(start, end, gflops)
    # collect results in one output File
    with open("Ergebnisse_python-2D-list.txt", "w") as output:
        output.writelines(header)
        output.writelines(results)

    return


def matrix():
    calc()


matrix()
