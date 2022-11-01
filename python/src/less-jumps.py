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

            start = time.time()

            # Begin matrix matrix multiply kernel
            i = 0
            while i < dim:
                j = 0
                while j < dim:
                    k = 0
                    while k < dim - 31:
                        temp = 0
                        # C[i][j] += A[i][k] * B[k][j]
                        temp += mat_A[i][k] * mat_B[k][j]
                        temp += mat_A[i][k + 1] * mat_B[k + 1][j]
                        temp += mat_A[i][k + 2] * mat_B[k + 2][j]
                        temp += mat_A[i][k + 3] * mat_B[k + 3][j]
                        temp += mat_A[i][k + 4] * mat_B[k + 4][j]
                        temp += mat_A[i][k + 5] * mat_B[k + 5][j]
                        temp += mat_A[i][k + 6] * mat_B[k + 6][j]
                        temp += mat_A[i][k + 7] * mat_B[k + 7][j]
                        temp += mat_A[i][k + 8] * mat_B[k + 8][j]
                        temp += mat_A[i][k + 9] * mat_B[k + 9][j]
                        temp += mat_A[i][k + 10] * mat_B[k + 10][j]
                        temp += mat_A[i][k + 11] * mat_B[k + 11][j]
                        temp += mat_A[i][k + 12] * mat_B[k + 12][j]
                        temp += mat_A[i][k + 13] * mat_B[k + 13][j]
                        temp += mat_A[i][k + 14] * mat_B[k + 14][j]
                        temp += mat_A[i][k + 15] * mat_B[k + 15][j]
                        temp += mat_A[i][k + 16] * mat_B[k + 16][j]
                        temp += mat_A[i][k + 17] * mat_B[k + 17][j]
                        temp += mat_A[i][k + 18] * mat_B[k + 18][j]
                        temp += mat_A[i][k + 19] * mat_B[k + 19][j]
                        temp += mat_A[i][k + 20] * mat_B[k + 20][j]
                        temp += mat_A[i][k + 21] * mat_B[k + 21][j]
                        temp += mat_A[i][k + 22] * mat_B[k + 22][j]
                        temp += mat_A[i][k + 23] * mat_B[k + 23][j]
                        temp += mat_A[i][k + 24] * mat_B[k + 24][j]
                        temp += mat_A[i][k + 25] * mat_B[k + 25][j]
                        temp += mat_A[i][k + 26] * mat_B[k + 26][j]
                        temp += mat_A[i][k + 27] * mat_B[k + 27][j]
                        temp += mat_A[i][k + 28] * mat_B[k + 28][j]
                        temp += mat_A[i][k + 29] * mat_B[k + 29][j]
                        temp += mat_A[i][k + 30] * mat_B[k + 30][j]
                        temp += mat_A[i][k + 31] * mat_B[k + 31][j]
                        k += 32
                        mat_C[i][j] = temp
                    j += 1
                i += 1
            # End matrix matrix multiply kernel

            end = time.time()
            gflops = ((2 * dim ** 3) / 1_000_000_000.0) / (end - start)
            collect_info(start, end, gflops)
    except:
        pass
    finally:
        # collect results in one output File
        with open("Ergebnisse_python-2D-list.txt", "w") as output:
            output.writelines(header)
            output.writelines(results)


def matrix():
    calc()


matrix()
