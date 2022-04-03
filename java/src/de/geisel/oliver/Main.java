package de.geisel.oliver;

import de.geisel.oliver.matrix.*;

import java.time.Duration;
import java.time.Instant;

public class Main {

	static int dim = 0;
	static int step = 32;
	static int max = 2048 * 4;

	static void set_dim() {
		if (dim < 256) {
			dim += step;
		} else if (dim < 512) {
			dim += step * 2;
		} else if (dim < 1024) {
			dim += step * 4;
		} else if (dim < 2048) {
			dim += step * 8;
		} else if (dim < 4096) {
			dim += step * 16;
		} else {
			dim += step * 32;
		}
	}

	public static void main(String[] args) {
		double gflops;
		dim = 0;

		System.out.print("\nMatrix matrix multiply example:\n\n");

		while (dim < max) {
			set_dim();
			MatrixArray1D A = new MatrixArray1D(dim, dim, true);
			MatrixArray1D B = new MatrixArray1D(dim, dim, true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Instant end;

			// prepare
			Matrix tempA = new MatrixArray1D(1024, 1024, true);
			Matrix tempB = new MatrixArray1D(1024, 1024, true);
			Matrix tempC = tempA.multiply(tempB);

			Instant start = Instant.now();
			/* Begin matrix matrix multiply kernel */
			Matrix C = A.multiply(B);
			/* End matrix matrix multiply kernel */
			end = Instant.now();

			double seconds = (double) Duration.between(start, end).toNanos() / 1_000_000_000;
			gflops = getGFLOPs(dim, seconds);
			collect_info(seconds, gflops);


			MatrixOO A_oo = new MatrixOO(dim, dim);
			MatrixOO B_oo = new MatrixOO(dim, dim);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// prepare
			tempA = new MatrixOO(1024, 1024);
			tempB = new MatrixOO(1024, 1024);
			tempC = tempA.multiply(tempB);

			start = Instant.now();
			/* Begin matrix matrix multiply kernel */
			Matrix C_oo = A_oo.multiply(B_oo);
			/* End matrix matrix multiply kernel */
			end = Instant.now();

			seconds = (double) Duration.between(start, end).toNanos() / 1_000_000_000;
			gflops = getGFLOPs(dim, seconds);
			collect_info(seconds, gflops);


			MatrixWithThread A_Thread = new MatrixWithThread(dim, dim, true);
			MatrixWithThread B_Thread = new MatrixWithThread(dim, dim, true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// prepare

			tempA = new MatrixWithThread(1024, 1024, true);
			tempB = new MatrixWithThread(1024, 1024, true);
			tempC = tempA.multiply(tempB);

			start = Instant.now();
			/* Begin matrix matrix multiply kernel */
			Matrix C_Thread = A_Thread.multiply(B_Thread);
			/* End matrix matrix multiply kernel */
			end = Instant.now();

			seconds = (double) Duration.between(start, end).toNanos() / 1_000_000_000;
			gflops = getGFLOPs(dim, seconds);
			collect_info(seconds, gflops);


			VectorMatrix A_Vector = new VectorMatrix(dim, dim);
			VectorMatrix B_Vector = new VectorMatrix(dim, dim);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// prepare

			tempA= new VectorMatrix(1024,1024);
			tempB= new VectorMatrix(1024,1024);
			tempC = tempA.multiply(tempB);

			start = Instant.now();
			/* Begin matrix matrix multiply kernel */
			Matrix C_Vector = A_Vector.multiply(B_Vector);
			/* End matrix matrix multiply kernel */
			end = Instant.now();

			seconds = (double) Duration.between(start, end).toNanos() / 1_000_000_000;
			gflops = getGFLOPs(dim, seconds);
			collect_info(seconds, gflops);

			System.out.println();
		}
	}

	private static double getGFLOPs(double dim, double seconds) {
		return (2 * Math.pow(dim, 3) / 1_000_000_000.0) / seconds;
	}

	static void collect_info(double duration, double gflops) {
		System.out.printf("Dim: %4d runtime: %7.6fs GFLOP/s: %.3f\n", dim, duration, gflops);
	}
}
