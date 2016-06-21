/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.groot.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.jlab.groot.base.AttributeType;
import org.jlab.groot.base.TStyle;
import org.jlab.groot.data.IDataSet;
import org.jlab.groot.math.Dimension2D;

/**
 *
 * @author gavalian
 */
public class FunctionPlotter implements IDataSetPlotter {

    private Dimension2D dataRegion  = new Dimension2D();
    private String      drawOptions = "";
    private IDataSet    functionData = null;
    
    public FunctionPlotter(IDataSet func){
        functionData = func;
    }
    
    @Override
    public String getOptions() {
        return this.drawOptions;
    }

    @Override
    public void setOptions(String opt) {
        this.drawOptions = opt;
    }

    @Override
    public String getName() {
        return this.functionData.getName();
    }

    @Override
    public IDataSet getDataSet() {
        return this.functionData;
    }

    @Override
    public void draw(Graphics2D g2d, GraphicsAxisFrame frame) {
        int npoints = functionData.getDataSize(0);
        GeneralPath path = new GeneralPath();
        double xp = frame.getPointX(functionData.getDataX(0));
        double yp = frame.getPointY(functionData.getDataY(0));
        path.moveTo(xp, yp);
        for(int p = 0; p < npoints; p++){
            xp = frame.getPointX(functionData.getDataX(p));
            yp = frame.getPointY(functionData.getDataY(p));
            path.lineTo(xp, yp);
        }
        int lineColor = functionData.getAttributes().get(AttributeType.LINE_COLOR);
        int lineWidth = functionData.getAttributes().get(AttributeType.LINE_WIDTH);

        g2d.setColor(TStyle.getColor(lineColor));
        g2d.setStroke(new BasicStroke(lineWidth));
        g2d.draw(path);
    }

    @Override
    public Dimension2D getDataRegion() {
        double xp = this.functionData.getDataX(0);
        double yp = this.functionData.getDataY(0);
        this.dataRegion.set(xp,xp,yp,yp);
        int npoints = functionData.getDataSize(0);
        for(int i = 0; i < npoints; i++){
            this.dataRegion.grow(functionData.getDataX(i), functionData.getDataY(i));
        }
        return dataRegion;
    }
 
}