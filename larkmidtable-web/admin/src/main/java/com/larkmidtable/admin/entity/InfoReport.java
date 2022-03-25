package com.larkmidtable.admin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class InfoReport {
    private int dbCount;
    private int tCount;
    private int devCount;
	private int apiCount;
}
