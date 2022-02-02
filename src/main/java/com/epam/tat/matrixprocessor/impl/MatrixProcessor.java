package com.epam.tat.matrixprocessor.impl;

import com.epam.tat.matrixprocessor.IMatrixProcessor;
import com.epam.tat.matrixprocessor.exception.MatrixProcessorException;


public class MatrixProcessor implements IMatrixProcessor {

	@Override
	public double[][] transpose(double[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		double [][] resultMatrix = new double[m][n];
		for (int i = 0; i < n ; i++)  {
			for (int j = 0; j < m; j++) {
				resultMatrix [j][i] = matrix [i][j];
			}
		}
		return resultMatrix;
	}


	@Override
	public double[][] turnClockwise(double[][] matrix) {

		int n = matrix.length;
		int m = matrix[0].length;
		double [][] resultMatrix = new double[m][n];

		for (int i = 0; i < n ; i++)  {
			for (int j = 0; j < m; j++) {
				resultMatrix [j][n-i-1] = matrix [i][j];
			}
		}
		return resultMatrix;
	}

	@Override
	public double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
		int n = firstMatrix.length;
		int m = firstMatrix[0].length ;
		int n1 = secondMatrix.length;
		int m1 = secondMatrix[0].length;
		if (n1 != m) {
			throw new MatrixProcessorException("multiplication is impossible");
		}
		double [][] resultMatrix = new double [m][n];

		for (int i = 0; i < n ; i++)  {
			for (int j = 0; j < m; j++) {
				for (int l = 0; l < m1; l++) {
					resultMatrix [i][j] += Math.round ((firstMatrix [i][l] * secondMatrix [l][j]) * 1000) / 1000;
				}
			}
		}
		return resultMatrix;
		}

		@Override
	public double[][] getInverseMatrix(double[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		double determinant = getMatrixDeterminant(matrix);
		if (m != n ) {
			throw new MatrixProcessorException("Matrix can't be inversed");
		}
		if (determinant == 0) {
			throw new MatrixProcessorException("Matrix can't be inversed");

		}
		double [][] transposeMatrix = transpose(matrix);
		double [][] unionMatrix = getAlgebraicAdditionsMatrix(transposeMatrix);
		double [][] inverseMatrix = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				inverseMatrix[i][j] = (double) Math.round(((1/ determinant) * unionMatrix[i][j]) * 1000) / 1000;
			}
		}
		return inverseMatrix;
	}


	@Override
	public double getMatrixDeterminant(double[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		double determinant = 0;
		if (m != n ) {
			throw new MatrixProcessorException("Matrix can't be inversed");
		}
		double [][] algebraicAdditionsMatrix = getAlgebraicAdditionsMatrix(matrix);

		if (m != n) {
			throw new ArithmeticException ("finding the determinant is impossible");
		}
 		if (n == 2) {
		determinant = (matrix [0][0] * matrix [1][1]) - (matrix [0][1] * matrix [1][0]);
		}
		if (n == 3) {
			determinant = matrix [0][0] * matrix [1][1] * matrix [2][2]
					      - matrix [0][0] * matrix [1][2] * matrix [2][1]
		                  - matrix [0][1] * matrix [1][0] * matrix [2][2]
					      + matrix [0][1] * matrix [1][2] * matrix [2][0]
					      + matrix [0][2] * matrix [1][0] * matrix [2][1]
					      - matrix [0][2] * matrix [1][1] * matrix [2][0];
		}
		if (n > 3) {
			for (int i = 0; i < n; i++) {
					determinant += matrix [0][n] * algebraicAdditionsMatrix [0][n];
				}
			}
		return determinant;
	}

	public double[][] getAlgebraicAdditionsMatrix(double [][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		if (m != n ) {
			throw new MatrixProcessorException("Matrix can't be inversed");
		}
		double [][] minorMatrix = getMinorMatrix(matrix);
		double [][] algebraicAdditionsMatrix = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				algebraicAdditionsMatrix[i][j] = Math.pow(-1, ((i+1) + (j+1))) * minorMatrix[i][j];
			}
		}
		return  algebraicAdditionsMatrix;
	}


	public double [][] getMatrixWithLessCapacity(double [][] matrix, int a, int b) {
		int n = matrix.length;
		int z = 0; // i
		int y = 0; // j
		double [][] newMatrix = new double [n-1][n-1];
		for (int i = 0; i < n; i++) {
			y = 0;
			for (int j = 0; j < n; j++) {
				if (i != a && j != b) {
					newMatrix[z][y] = matrix[i][j];
					y = y + 1;
				}
			}
			if (i != a) {
				z =  z + 1;
			}
		}
		return newMatrix;
	}

	public double [][] getMinorMatrix(double [][] matrix) {
		int n = matrix.length;
		double [][] minorMatrix = new double [n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double[][] smallMatrix = getMatrixWithLessCapacity(matrix, i, j);
				if (smallMatrix.length > 1) {
					double determinant = getMatrixDeterminant(smallMatrix);
					minorMatrix[i][j] = determinant;
				} else {
					minorMatrix[i][j] = smallMatrix[0][0];
				}
			}
		}
		return  minorMatrix;
	}

	public void printMatrix(double [][] matrix) {
		int n = matrix.length;
		for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] );
                System.out.print(", ");
            }
            System.out.print("\n");
        }

        System.out.println("___________________");
	}

}

