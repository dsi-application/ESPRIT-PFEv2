package com.esprit.gdp;

public class TestMemory {

	public static void main(String[] args) {


		int MB = 1024 * 1024;

		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		// Print used memory
		System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / MB);

		// Print free memory
		System.out.println("Free Memory:" + runtime.freeMemory() / MB);

		// Print total available memory
		System.out.println("Total Memory:" + runtime.totalMemory() / MB);

		// Print Maximum available memory
		System.out.println("Max Memory:" + runtime.maxMemory() / MB);
	}
}