package de.geisel.oliver;

import de.geisel.oliver.matrix.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;

public class Main {

	static int dim = 0;
	static int step = 32;
	static int max = 2048 * 4;

	static private final String OOP = "OOP";
	static private final String NORMAL = "1D";
	static private final String THREAD = "Thread";
	static private final String VECTOR = "Vector";

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

	private static void run(Matrix A, Matrix B, int dim, String typ) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Instant start, end;

		Constructor<? extends Matrix> constructor = null;
		try {
			var clazz = A.getClass();
			constructor = clazz.getDeclaredConstructor(int.class, int.class);
			constructor.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		// prepare
		Matrix tempA = null;
		Matrix tempB = null;
		try {
			tempA = constructor.newInstance(1024, 1024);
			tempB = constructor.newInstance(1024, 1024);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		Matrix tempC = tempA.multiply(tempB);

		start = Instant.now();
		/* Begin matrix matrix multiply kernel */
		Matrix C = A.multiply(B);
		/* End matrix matrix multiply kernel */
		end = Instant.now();

		double seconds = ((double) Duration.between(start, end).toNanos()) / 1_000_000_000;
		double gflops = getGFLOPs(dim, seconds);

		collect_info(seconds, gflops, typ);

	}

	public static void main(String[] args) {
		dim = 0;
		System.out.print("\nMatrix matrix multiply example:\n\n");
		while (dim < max) {
			set_dim();

			MatrixArray1D A = MatrixArray1D.random(dim, dim);
			MatrixArray1D B = MatrixArray1D.random(dim, dim);
			run(A, B, dim, NORMAL);

			MatrixOO A_oo = new MatrixOO(dim, dim);
			MatrixOO B_oo = new MatrixOO(dim, dim);
			run(A_oo, B_oo, dim, OOP);

			MatrixWithThread A_Thread = MatrixWithThread.random(dim, dim);
			MatrixWithThread B_Thread = MatrixWithThread.random(dim, dim);
			run(A_Thread, B_Thread, dim, THREAD);

			VectorMatrix A_Vector = new VectorMatrix(dim, dim);
			VectorMatrix B_Vector = new VectorMatrix(dim, dim);
			run(A_Vector, B_Vector, dim, VECTOR);

			System.out.println();
		}
	}

	private static double getGFLOPs(double dim, double seconds) {
		return (2 * Math.pow(dim, 3) / 1_000_000_000.0) / seconds;
	}

	static void collect_info(double duration, double gflops, String typ) {
		System.out.printf("%s\tDim: %4d runtime: %7.6fs GFLOP/s: %.6f\n", typ, dim, duration, gflops);
	}
}
