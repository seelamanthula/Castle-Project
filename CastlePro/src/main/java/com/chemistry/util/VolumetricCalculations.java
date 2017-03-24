package com.chemistry.util;

public class VolumetricCalculations {

	private double[] mass;
	private double volumes;
	private double average;
	private double rsd;
	private double percentError;
	
	
	public double[] getMass() {
		return mass;
	}
	public void setMass(double[] mass) {
		this.mass = mass;
	}
	public double getVolumes() {
		return volumes;
	}
	public void setVolumes(double volumes) {
		this.volumes = volumes;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public double getRsd() {
		return rsd;
	}
	public void setRsd(double rsd) {
		this.rsd = rsd;
	}
	public double getPercentError() {
		return percentError;
	}
	public void setPercentError(double percentError) {
		this.percentError = percentError;
	}

	
}
