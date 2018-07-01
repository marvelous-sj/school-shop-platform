package xyz.marsj.o2o.dto;

import java.util.HashSet;

public class EchartXAxis {
 private String type="category";
 //去处重复
 private HashSet<String> data;
public HashSet<String> getData() {
	return data;
}
public void setData(HashSet<String> data) {
	this.data = data;
}
public String getType() {
	return type;
}
 
}
