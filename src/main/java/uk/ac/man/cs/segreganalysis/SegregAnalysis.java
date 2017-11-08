package uk.ac.man.cs.segreganalysis;

import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import uk.ac.man.cs.segreganalysis.controller.Controller;
import uk.ac.man.cs.segreganalysis.models.HenryModel;
import uk.ac.man.cs.segreganalysis.models.indices.DuncanSegregationIndex;
import uk.ac.man.cs.segreganalysis.models.indices.YulesQIndex;
import uk.ac.man.cs.segreganalysis.view.View;
import uk.ac.man.cs.segreganalysis.view.XYChart;

import java.util.Iterator;


public class SegregAnalysis {

    public enum GeneratorType {
        GRID, PREFERENTIAL_ATTACHMENT, RANDOM, RANDOM_EUCLIDEAN
    }

    public static void main(String args[]) {
        View view = new View("SegregAnalysis");
        Controller controller = new Controller(view);
        view.setVisible(true);
    }


    public SegregAnalysis(int numberOfNodes, GeneratorType generator,  int steps, int maxLinksPerStep ) {



        Graph graph = new SingleGraph("Network");
        graph.addAttribute("ui.stylesheet", "url('./stylesheet.css')");

        Generator gen;

        switch (generator){
            case GRID:
                gen = new GridGenerator();
                break;
            case PREFERENTIAL_ATTACHMENT:
                gen = new BarabasiAlbertGenerator(maxLinksPerStep);
                break;
            case RANDOM:
                gen = new RandomGenerator();
                break;
            case RANDOM_EUCLIDEAN:
                gen = new RandomEuclideanGenerator();
                break;
            default:
                gen = new BarabasiAlbertGenerator(maxLinksPerStep);
                break;
        }

        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < numberOfNodes; i++)
            gen.nextEvents();
        gen.end();


        // initialise random attribute
        Iterator<Node> iter = graph.iterator();

        while (iter.hasNext()){
            Node n = iter.next();
            if (Math.random() > 0.5) {
                n.addAttribute("gender", "male");
                n.addAttribute("ui.class", "male");
            }
            else {
                n.addAttribute("gender", "female");
                n.addAttribute("ui.class", "female");
            }
        }


        // segregation index
        DuncanSegregationIndex DSIndex = new DuncanSegregationIndex(graph);
        YulesQIndex yulesQIndex = new YulesQIndex(graph);

        // iterative model
        HenryModel henryModel = new HenryModel(graph);

        // dataset for plotting graph jfree
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        //final XYSeries duncan = new XYSeries( "DSI" );
        final XYSeries yules = new XYSeries( "Yule's Q" );


        // iterations of the algorithm
        int i = steps;
        while (i > 0) {
            //duncan.add(i, DSIndex.calculate());
            yules.add(i, yulesQIndex.movingAverage());
            henryModel.iteration();
            i--;
        }

        //dataset.addSeries(duncan);
        dataset.addSeries(yules);

        XYChart chart = new XYChart("Segregation Emergence Statistics",
                "", dataset);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);


    }

}
