package org.jlab.groot.demo;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JFrame;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.math.F1D;
import org.jlab.groot.math.RandomFunc;

public class MultiGaus {

	public static void main(String[] args) {
		Dimension screensize  = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame = new JFrame("GROOT DEMO");
		frame.setSize((int)(screensize.getHeight()*.75*1.618), (int) (screensize.getHeight()*.75));
		EmbeddedCanvas c1 = new EmbeddedCanvas();
		c1.divide(4, 4);
		Random rand = new Random();
		/*
		H2F hh = new H2F("junk",10,0.,10.,10,0.,10.);
	    H1F hp = hh.sliceX(4);
	    //hp.fill(.3); 
	    //c1.getPad(0).getAxisY().setRange(0, 10.0);
	    c1.draw(hp);
	    frame.add(c1);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 
		*/
		H1F[] h1 = new H1F[16];
		for (int i = 0; i < h1.length-1; i++) {
			h1[i] = new H1F("h" + i, "", 200, -5.0, 5.0);
			h1[i].setXTitle("Randomly Generated Function");
			h1[i].setYTitle("Counts");
			h1[i].setOptStat(1100);
			F1D f1 = new F1D("f1","[amp]*gaus(x,[mean],[sigma])", -5.0, 5.0);
			f1.setParameter(0, 120.0);
			f1.setParameter(1, (-3.0 + rand.nextDouble() * 6));
			f1.setParameter(2, .4 + (rand.nextDouble() * 1));
			f1.setOptStat(1000);

			RandomFunc rndm = new RandomFunc(f1);
			for (int j = 0; j < 34000; j++) {
				h1[i].fill(rndm.random());
			}
			h1[i].setLineWidth(2);
			h1[i].setLineColor(21);
			h1[i].setFillColor(30 + (i % 4) + 2);
			
			c1.cd(i);
			String optStatString = "";
			for(int j=0; j<4-i%4; j++){
				optStatString +="1";
			}
			optStatString+="0";
			c1.getPad(i).setOptStat(Integer.parseInt(optStatString));
			//c1.getPad(i).getAxisX().setRange(-6.0, 5.0);
			//c1.getPad(i).getAxisX().setAutoScale(true);
			//c1.getPad(i).getAxisY().setRange(0, 2000.0);
			//c1.getPad(i).getAxisY().setAutoScale(true);
			//System.out.println(c1.getPad(i).getAxisX());
			c1.draw(h1[i]);
			
			//DataFitter fitter = new DataFitter();
			f1.setParameter(0, h1[i].getEntries());
			DataFitter.fit(f1, h1[i], "");
			f1.setLineColor(30 + (i % 4) + 2);
			f1.setLineWidth(3);
			f1.setLineStyle(i%4);
			c1.draw(f1,"same");
		}
		frame.add(c1);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

}
