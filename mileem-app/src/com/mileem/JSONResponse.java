package com.mileem;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONResponse {
	private JSONArray jArray;
	private JSONObject jobject;
	private String error = "";
	
	public JSONArray getjArray() {
		return jArray;
	}
	public void setjArray(JSONArray jArray) {
		this.jArray = jArray;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public JSONObject getJobject() {
		return jobject;
	}
	public void setJobject(JSONObject jobject) {
		this.jobject = jobject;
	}
}
