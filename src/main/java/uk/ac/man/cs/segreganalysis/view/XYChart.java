package uk.ac.man.cs.segreganalysis.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.NumberAxis;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

public class XYChart extends JFrame {


    public XYChart(String applicationTitle, String chartTitle, XYDataset dataset){

        super(applicationTitle);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "Steps" ,
                "Index value" ,
                dataset ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot xyPlot = xylineChart.getXYPlot( );

        // make the bars static
//        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
//        range.setRange(-0.1, 1.00);
//        range.setTickUnit(new NumberTickUnit(0.1));

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setBaseShapesVisible(false); // no data points
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.GREEN );
        renderer.setSeriesPaint( 2 , Color.YELLOW );
        renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 1.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 1.0f ) );
        xyPlot.setRenderer( renderer );
        setContentPane( chartPanel );

    }

}
