package com.example.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

@SuppressWarnings("serial")
public class SerializableTools implements Serializable {
	private Map<String, Object> mapObject;
	private byte[] b;

	public byte[] getB() {
		return b;
	}

	public void setB(byte[] b) {
		this.b = b;
	}

	public Map<String, Object> getMapObject() {
		return mapObject;
	}

	public void setMapObject(Map<String, Object> mapObject) {
		this.mapObject = mapObject;
	}

}
