package com.chemistry.util;

import java.text.DecimalFormat;

public class Calculations {
		/*
		 * Use these functions as class instances, i.e.:
		 * -double ans = Calc.average(array);
		 * -double mol = Calc.molarity(volume);
		 * 
		 * the answer may need to be rounded depending on the question, 
		 * but if its just a comparison check then it won't matter if we round
		 * 
		 * 
		 */

	public double[] getVolumeOfWater(double[] nums, double density)
	{
		double[] densities = new double[nums.length];
		DecimalFormat format = new DecimalFormat("##.####");
		
		System.out.println("Densities : ");
		for(int x = 0; x < nums.length; x++) {
//		int x = 0;	
		densities[x]= Double.parseDouble(format.format(nums[x] / density));
			System.out.println(densities[x]);
		}
		
		return densities;
	}

		
		public double average(double[] nums)
		{
			double ans = 0;
			int size = nums.length;
			
			for(int x = 0; x < size; x++)
				ans += nums[x];
			
			ans = ans/size;
			return ans;
		}
		
		public double RSD(double[] nums)
		{
			double ans = 0;
			double mean = average(nums);
			double[] temp = nums;
			double val;
			
			for(int x = 0; x < temp.length; x++)
			{
				val = temp[x] - mean;
				temp[x] = val*val;
			}
			
			for(int x = 0; x < temp.length; x++)
				ans += temp[x];
			
			ans = ans/(temp.length-1);
			ans = Math.sqrt(ans);
			ans = (ans*1000)/mean;
			DecimalFormat format = new DecimalFormat("##.####");
			return Double.parseDouble(format.format(ans));
//			return ans;
		}
		/////return value is a percent
		
		
		public double percentError(double trueVal, double experVal)
		{
			double err = Math.abs(trueVal-experVal);
			err = err/trueVal;
			err =err*100;
			DecimalFormat format = new DecimalFormat("##.####");
			
			return Double.parseDouble(format.format(err));
		}
		////based on numbers given by professor HCL molarity 
		
		public double[] getMolarites(double[] volumes) {
			double[] molarities = new double[volumes.length];
			for(int i = 0; i< volumes.length; i++) {
				molarities[i] = molarity(volumes[i]);
			}
			return molarities;
		}
		
		
		public double molarity(double vol)
		{
			double ans = vol/1000;
			ans = ans * (.1/.005);
			return ans;
		}

	}

