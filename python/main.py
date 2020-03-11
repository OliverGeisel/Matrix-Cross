import random
import time

dim = 0
step = 32
max_dim = 2048 * 4


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
    print(f"Dim: {dim} runtime: {round(t_end - t_start,2)}s GFLOP/s: {round(gflops,5)}")


def random_mat(dim):
    matrix = list()
    if matrix is None:
        return None
    return [random.uniform(-100, 100) for i in range(dim**2)]


def zero_mat(dim):
    return [0] * dim ** 2


def calc():
    while dim < 256:
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
                    mat_C[i * dim + j] += mat_A[i * dim + k] * mat_B[k * dim + j]
        # End matrix matrix multiply kernel

        end = time.time()
        gflops = ((2 * dim ** 3) / 1_000_000_000.0) / (end - start)
        collect_info(start, end, gflops)
    return


def matrix():
    calc()


if __name__ == "__main__":
    matrix()
