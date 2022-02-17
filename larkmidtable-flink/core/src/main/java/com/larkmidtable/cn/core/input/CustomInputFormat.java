package com.larkmidtable.cn.core.input;

import org.apache.flink.api.common.io.RichInputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;

import java.io.IOException;

/**
 *
 *
 * @Date: 2022/2/16 9:56
 * @Description:
 **/
public class CustomInputFormat   extends RichInputFormat {
	@Override
	public void configure(Configuration configuration) {

	}

	@Override
	public BaseStatistics getStatistics(BaseStatistics baseStatistics) throws IOException {
		return null;
	}

	@Override
	public InputSplit[] createInputSplits(int i) throws IOException {
		return new InputSplit[0];
	}

	@Override
	public InputSplitAssigner getInputSplitAssigner(InputSplit[] inputSplits) {
		return null;
	}

	@Override
	public void open(InputSplit inputSplit) throws IOException {

	}

	@Override
	public boolean reachedEnd() throws IOException {
		return false;
	}

	@Override
	public Object nextRecord(Object o) throws IOException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "i am coming";
	}

	@Override
	public void close() throws IOException {

	}
}
