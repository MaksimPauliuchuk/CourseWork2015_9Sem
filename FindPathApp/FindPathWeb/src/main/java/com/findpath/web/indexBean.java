package com.findpath.web;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="indexB")
public class indexBean {
	private String test;
	
	public indexBean() {
		setTest("Hallo");
	}

	public final String getTest() {
		return test;
	}

	public final void setTest(String test) {
		this.test = test;
	}
}
