package driverFactory;

import org.testng.annotations.Test;

public class AppTest 
{
	@Test
	public void kickStart() throws Throwable 
	{
		driverScript ds = new driverScript();
		ds.startTest();
	}

}
