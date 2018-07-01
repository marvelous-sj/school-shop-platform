package xyz.marsj.o2o.service;

import java.util.List;

import xyz.marsj.o2o.entity.Area;

public interface IAreaService {
	public static final String AREALISTKEY="arealist";
	List<Area> getAreaList();
}
