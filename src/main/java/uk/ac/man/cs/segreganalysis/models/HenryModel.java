package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.*;


public class HenryModel {

    Graph graph;

    public HenryModel(Graph g){
        graph = g;
    }

    public void iteration(){

        // choose random edge uv
        Edge edge = getRandomEdge();

        // assess attribute distance d(u,v)
        double attributeDistance = calculateAttributeDistance(edge);

        // choose: delete or keep
        if (isToBeDeleted(attributeDistance)){
            graph.removeEdge(edge);
            rewire();
        }


    }

    private Edge getRandomEdge(){
        int numberOfEdges = this.graph.getEdgeCount();
        int randomEdge = (int)(Math.random()*100) % numberOfEdges;
        return graph.getEdge(randomEdge);
    }

    // todo
    private void rewire(){

    }

    // todo
    private boolean isToBeDeleted(double attributeDistance){

        return Math.random() > 0.5 ;
    }

    // todo
    private double calculateAttributeDistance(Edge edge){
        return 0.3;
    }


}
