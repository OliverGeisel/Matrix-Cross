import multiprocessing as mp
import os
import random
import sys
import time

import src.subthread

num_process = 1
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


def calc_per_column():
    try:
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

            start = time.perf_counter()

            # Begin matrix matrix multiply kernel
            i = 0
            while i < dim:
                j = 0
                a_row = mat_A[i]
                args = list()
                while j < dim:
                    b_column = list()
                    for row in size:
                        b_column.append(mat_B[row][i])
                    args.append((a_row, b_column, dim))
                    j += 1
                with mp.Pool(num_process) as pool:
                    result = pool.starmap(src.subthread.subthread_column, args)
                x = 0
                while x < len(result):
                    mat_C[i][x] += result[x]
                    x += 1
                i += 1
            # End matrix matrix multiply kernel

            end = time.perf_counter()
            gflops = ((2 * dim ** 3) / 1_000_000_000.0) / (end - start)
            collect_info(start, end, gflops)
        # collect results in one output File
    except:
        pass
    finally:
        with open("Ergebnisse_python-threaded.txt", "w") as output:
            output.writelines(header)
            output.writelines(results)


def calc_per_row():
    try:
        while dim < max_dim:
            set_dim()
            mat_A = random_mat(dim)
            mat_B = random_mat(dim)
            mat_B_transpose = [list() for x in range(dim)]

            for row in mat_B:
                for j, cell in enumerate(row):
                    mat_B_transpose[j].append(cell)

            if None in [mat_A, mat_B]:
                print("Allocation of matrix failed.\n")
                exit("EXIT_FAILURE")
            time.sleep(1)

            start = time.perf_counter()
            # Begin matrix matrix multiply kernel
            with mp.Pool(num_process) as pool:
                args = list()
                row_num = 0
                while row_num < dim:
                    # args for every row
                    row = mat_A[row_num]
                    args.append((row, mat_B_transpose, dim))
                    row_num += 1
                mat_C = pool.starmap(src.subthread.subthread_row, args)
            # End matrix matrix multiply kernel
            end = time.perf_counter()

            gflops = ((2 * dim ** 3) / 1_000_000_000.0) / (end - start)
            collect_info(start, end, gflops)
            # collect results in one output File
    except:
        pass
    finally:
        with open("Ergebnisse_python-multiprocessed.txt", "w") as output:
            output.writelines(header)
            output.writelines(results)


def matrix():
    global num_process
    print(mp.get_all_start_methods())
    mp.set_start_method("spawn")
    print(f"Method: {str(mp.get_start_method())}")
    num_process = int(os.environ.get("NUMBER_OF_PROCESSORS")) if len(sys.argv) < 2 else int(sys.argv[1])
    if len(sys.argv) > 2:
        print("Process per column per row ")
        calc_per_column()
    else:
        print("Process per row")
        calc_per_row()


if __name__ == "__main__":
    matrix()
