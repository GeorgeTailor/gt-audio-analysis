package com.gt.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.VerticalAlignment;

import com.gt.main.Main;
import com.gt.note.harmonic.finder.HarmonicsByIndexFinder;

public class ChartUtils {

	public static JFreeChart createChart(DefaultTableXYDataset dataset) {

		//x axis
		NumberAxis domainAxis = new NumberAxis("Frequency");
		// arrow to right
		domainAxis.setPositiveArrowVisible(true);
		// padding from graph
		domainAxis.setUpperMargin(0.2);

		//y axis
		NumberAxis rangeAxis = new NumberAxis("Amplitude");

		// arrow to right
		rangeAxis.setPositiveArrowVisible(true);
		
		//SamplingXYLineRenderer renderer = new SamplingXYLineRenderer();
		XYBarRenderer renderer = new XYBarRenderer();
		// without frame
		renderer.setDrawBarOutline(false);
		// colors for each one of series
		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesPaint(1, Color.DARK_GRAY);
		renderer.setSeriesPaint(2, Color.yellow);
		renderer.setSeriesPaint(3, Color.green);
		renderer.setSeriesPaint(4, Color.cyan);
		renderer.setSeriesPaint(5, Color.blue);
		renderer.setSeriesPaint(6, new Color( 128, 0, 128 ));
		renderer.setSeriesPaint(7, Color.black);
		renderer.setSeriesPaint(8, Color.pink);
		// format and text of title
		renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator("{0} : {1} = {2} tonnes", new SimpleDateFormat(
				"yyyy"), new DecimalFormat("#,##0")));
		renderer.setSeriesItemLabelGenerator(0, new StandardXYItemLabelGenerator());
		renderer.setSeriesItemLabelGenerator(1, new StandardXYItemLabelGenerator());
		// showing it
		renderer.setSeriesItemLabelsVisible(0, false);
		renderer.setSeriesItemLabelsVisible(1, false);
		// its type
		renderer.setSeriesItemLabelFont(0, new Font("Serif", Font.BOLD, 10));
		renderer.setSeriesItemLabelFont(1, new Font("Serif", Font.BOLD, 10));

		// Plot
		XYPlot plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);

		// Chart
		JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(Color.gray);
		chart.setAntiAlias(false);
		chart.getLegend().setPosition(RectangleEdge.RIGHT);
		chart.getLegend().setVerticalAlignment(VerticalAlignment.TOP);
		chart.getLegend().setVisible(false);
		return chart;
	}
	@SuppressWarnings("unused")
	private static void refreshChart(JScrollPane sp, JFreeChart chart)throws Exception {
		sp.removeAll();
		sp.revalidate(); // This removes the old chart 
		chart = ChartUtils.createChart(Main.createDataset()); 
		chart.removeLegend(); 
	    ChartPanel chartPanel = new ChartPanel(chart); 
	    chartPanel.setPreferredSize(new Dimension(450, 450));
	    sp.add(chartPanel); 
	    sp.repaint(); // This method makes the new chart appear
	}
	public static XYSeries prepareXYSeries(Map<Integer, Double> map){
		XYSeries xyseries = new XYSeries("Guitar D#m7", false, false);
		int maxIndex = 0;
		Double maxValue = 0d;
		for (Map.Entry<Integer, Double> entry : map.entrySet())
		{
			if(entry.getKey() < 1100)
				xyseries.add(entry.getKey(),entry.getValue());
			if(entry.getValue() > maxValue){
				maxValue = entry.getValue();
				maxIndex = entry.getKey();
			}
		}
		double frequency = maxIndex*44100/65536;
		HarmonicsByIndexFinder.populateNoteName(frequency, maxIndex, maxValue, "1");
		HarmonicsByIndexFinder.printNotes();
		HarmonicsByIndexFinder.clearMap();
		System.out.println("Max frequency = " + frequency + " at index " + maxIndex);
		return xyseries;
	}
	
	public static XYSeries prepareFullXYSeries(List<Double> spectrum){
		XYSeries xyseries = new XYSeries("Guitar D#m7", false, false);
		for(int i=0;i<spectrum.size();i++){
			if(i < 10000)
				xyseries.add(i,spectrum.get(i));
		}
		return xyseries;
	}
}
