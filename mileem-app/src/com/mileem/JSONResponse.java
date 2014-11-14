package com.mileem;

import org.json.JSONArray;

public class JSONResponse {
	private JSONArray jArray;
	private String error = "";
	private String result = "";
	
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
