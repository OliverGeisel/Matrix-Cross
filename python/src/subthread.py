import multiprocessing as mp


def subthread(row_a, column_b, dim):
    mp.freeze_support()
    k = 0
    result = 0
    while k < dim - 31:
        temp = 0
        # C[i][j] += A[i][k] * B[k][j]
        temp += row_a[k] * column_b[k]
        temp += row_a[k + 1] * column_b[k + 1]
        temp += row_a[k + 2] * column_b[k + 2]
        temp += row_a[k + 3] * column_b[k + 3]
        temp += row_a[k + 4] * column_b[k + 4]
        temp += row_a[k + 5] * column_b[k + 5]
        temp += row_a[k + 6] * column_b[k + 6]
        temp += row_a[k + 7] * column_b[k + 7]
        temp += row_a[k + 8] * column_b[k + 8]
        temp += row_a[k + 9] * column_b[k + 9]
        temp += row_a[k + 10] * column_b[k + 10]
        temp += row_a[k + 11] * column_b[k + 11]
        temp += row_a[k + 12] * column_b[k + 12]
        temp += row_a[k + 13] * column_b[k + 13]
        temp += row_a[k + 14] * column_b[k + 14]
        temp += row_a[k + 15] * column_b[k + 15]
        temp += row_a[k + 16] * column_b[k + 16]
        temp += row_a[k + 17] * column_b[k + 17]
        temp += row_a[k + 18] * column_b[k + 18]
        temp += row_a[k + 19] * column_b[k + 19]
        temp += row_a[k + 20] * column_b[k + 20]
        temp += row_a[k + 21] * column_b[k + 21]
        temp += row_a[k + 22] * column_b[k + 22]
        temp += row_a[k + 23] * column_b[k + 23]
        temp += row_a[k + 24] * column_b[k + 24]
        temp += row_a[k + 25] * column_b[k + 25]
        temp += row_a[k + 26] * column_b[k + 26]
        temp += row_a[k + 27] * column_b[k + 27]
        temp += row_a[k + 28] * column_b[k + 28]
        temp += row_a[k + 29] * column_b[k + 29]
        temp += row_a[k + 30] * column_b[k + 30]
        temp += row_a[k + 31] * column_b[k + 31]
        result += temp
        k += 32
    return result
