package Biparted_Matching;

import Biparted_Matching.Engine.Interface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Controller
{
	
	@FXML
	public Slider LeftSlider;
	@FXML
	public Slider DensitySlider;
	@FXML
	public Button StartButton;
	@FXML
	public Slider RightSlider;
	@FXML
	public Canvas canvas;
	
	private int n, m;
	
	@FXML
	public void initialize()
	{
		
	}
	
	public void StartButtonAction(ActionEvent actionEvent)
	{
		System.out.println("=====================================================\n\n\n\n");
		Interface in = new Interface();
		m = (int) LeftSlider.getValue();
		n = (int) RightSlider.getValue();
		int dens = (int) DensitySlider.getValue();
		in.init(m, n);
		
		pad = 50;
		w = (canvas.getWidth() - pad * 2);
		h = (canvas.getHeight() - pad * 2);
		//r = Math.min(h / n, h / m);
		r = h/LeftSlider.getMax();
		
		in.random(System.currentTimeMillis(), 100 - dens);
		int[][] graph = in.getGraph();
		
		drawGraph(graph);
		
		int[] ans = in.getMaximumMatchingGraph();
		
		
		
		
		drawAnswer(ans);
		
		
		
	}
	
	private void drawAnswer(int[] ans)
	{
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Pointf p1, p2;
		
		gc.setStroke(Color.rgb(190, 90, 120, .5));
		gc.setLineWidth(10);
		for (int i = 0; i < m; i++)
		{
			
			p1 = getLeftVert(i);
			if(ans[i] == -1)
				continue;
			p2 = getRightVert(ans[i]);
			gc.strokeLine(p1.x + r / 2, p1.y + r / 2, p2.x + r / 2, p2.y + r / 2);
		}
		
		double cx,cy;
		gc.setFill(Color.rgb(190, 20, 90));
		for (int i = 0; i < m; i++)
		{
			if( ans[i] == -1 )
				continue;
			
			p1 = getLeftVert(i);
			cx = p1.x;
			cy = p1.y;
//			cy = center - ( m/2. - i )*r +pad ;
			gc.fillOval(cx, cy, r - 2, r - 2);
			
			
			p1 = getRightVert(ans[i]);
			cx = p1.x;
			cy = p1.y;
//			cy = center - ( m/2. - i )*r +pad ;
			gc.fillOval(cx, cy, r - 2, r - 2);
		}
		
	}
	
	private double pad;
	private double w;
	private double h;
	private double r;
	
	private void drawGraph(int[][] graph)
	{
		
		
		System.out.printf("n:%d m:%d r:%.2f", n, m, r);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.setFill(Color.GRAY);
		gc.fillRect(0, 0, 1000, 1000);
		double center = w / 2;
		
		double cx, cy;
		Pointf p1, p2;
		
		
		gc.setStroke(Color.rgb(110, 180, 220, .6));
		gc.setLineWidth(10);
		for (int i = 0; i < m; i++)
		{
			
			for (int j = 0; j < graph[i].length; j++)
			{
				p1 = getLeftVert(i);
				p2 = getRightVert(graph[i][j]);
				gc.strokeLine(p1.x + r / 2, p1.y + r / 2, p2.x + r / 2, p2.y + r / 2);
			}
		}


//		cx = r;
		gc.setFill(Color.AQUAMARINE.darker().darker());
		for (int i = 0; i < m; i++)
		{
			p1 = getLeftVert(i);
			cx = p1.x;
			cy = p1.y;
//			cy = center - ( m/2. - i )*r +pad ;
			gc.fillOval(cx, cy, r - 2, r - 2);
		}

//		cx=w-r;
		gc.setFill(Color.AQUAMARINE.darker().darker());
		for (int i = 0; i < n; i++)
		{
			p2 = getRightVert(i);
			cx = p2.x;
			cy = p2.y;
//			cy = center - ( n/2. - i )*r +pad;
			gc.fillOval(cx, cy, r - 2, r - 2);
		}
		
		
	}
	
	private Pointf getLeftVert(int l)
	{
		Pointf res = new Pointf();
		res.x = r;
		res.y = w / 2 - (m / 2. - l) * r + pad;
		
		return res;
	}
	
	private Pointf getRightVert(int rr)
	{
		Pointf res = new Pointf();
		res.x = w - r;
		res.y = w / 2 - (n / 2. - rr) * r + pad;
		
		return res;
	}
	
	
}

class Pointf
{
	double x, y;
}