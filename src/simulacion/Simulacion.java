/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulacion;

/**
 *
 * @author Erlinda Zambrano Andres Sastoque
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;


public class Simulacion implements ActionListener
{
	JTextField textPuntos;
	JTextField textPi;
	JTextField textError;
	JButton botonCalcular;
	JButton botonReset;
	JPanel panelSup;
	JPanelDibujo panelInf;
	
	public static void main(String[] args) 
	{
		Simulacion simulacion = new Simulacion();
		simulacion.CrearFormulario();
		simulacion.panelSup.repaint();
		simulacion.panelInf.repaint();
    }
    
    public void CrearFormulario()  
    {
        JFrame ventana = new JFrame("Simulación");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(640, 480);
        ventana.setVisible(true);
        
        panelSup = new JPanel();
        panelSup.setLayout(null);
        panelSup.setBackground(Color.white);
        panelSup.setLocation(0, 0);
        panelSup.setSize(640, 80);
        
        panelInf = new JPanelDibujo();
        panelInf.setLayout(null);
        panelInf.setBackground(Color.black);
        panelInf.setLocation(0, 80);
        panelInf.setSize(640, 400);
        
        JLabel labelPuntos = new JLabel("N Puntos");
        labelPuntos.setBounds(140, 8, 70, 10);
        textPuntos = new JTextField();
        textPuntos.setText("");
        textPuntos.setHorizontalAlignment(JTextField.RIGHT);
        textPuntos.setBounds(140, 20, 70, 40);
        textPuntos.setFont(new Font("Arial", Font.BOLD, 16));
        
        botonCalcular = new JButton();
        botonCalcular.setBounds(220, 20, 100, 40);
        botonCalcular.setText("Calcular");
        botonCalcular.addActionListener(this);
        
        JLabel labelPi = new JLabel("Pi calculado");
        labelPi.setBounds(330, 8, 70, 10);
        textPi = new JTextField();
        textPi.setText("");
        textPi.setEditable(false);
        textPi.setHorizontalAlignment(JTextField.RIGHT);
        textPi.setBounds(330, 20, 70, 40);
        textPi.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel labelError = new JLabel("Error");
        labelError.setBounds(410, 8, 70, 10);
        textError = new JTextField();
        textError.setText("");
        textError.setEditable(false);
        textError.setHorizontalAlignment(JTextField.RIGHT);
        textError.setBounds(410, 20, 70, 40);
        textError.setFont(new Font("Arial", Font.BOLD, 16));
        
        botonReset = new JButton();
        botonReset.setBounds(490, 20, 70, 40);
        botonReset.setText("Reset");
        botonReset.addActionListener(this);
        
        panelSup.add(botonCalcular);
        panelSup.add(labelPuntos);
        panelSup.add(textPuntos);
        panelSup.add(labelPi);
        panelSup.add(textPi);
        panelSup.add(labelError);
        panelSup.add(textError);
        panelSup.add(botonReset);
        
        ventana.add(panelSup);
        ventana.add(panelInf);                
    }
    
	class JPanelDibujo extends JPanel
	{
		private ArrayList<Punto> puntos;
		private ArrayList<Punto> diferencias;
		
		public JPanelDibujo()
		{
			puntos = new ArrayList<Punto>();
			diferencias = new ArrayList<Punto>();
		}
		
		public void agregarPunto(Punto p)
		{
			puntos.add(p);
		}
		
		public void borrarPuntos()
		{
			puntos.clear();
		}
		
		public int puntosTotales()
		{
			return puntos.size();
		}
		
		public int puntosValidos()
		{
			int validos = 0;
			for(Punto p: puntos)
			{
				if(p.valido)
					validos++;
			}
			return validos;
		}
		
		public void agregarDiferencia(Punto p)
		{
			diferencias.add(p);
		}
		
		public void borrarDiferencias()
		{
			diferencias.clear();
		}
		
		@Override
	    public void paintComponent(Graphics g) 
		{	        
	        super.paintComponent(g);
	        
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setColor(Color.white);
	        //
	        g2d.drawLine(20, 100, 20, 300);
	        g2d.drawLine(20, 300, 220, 300);
	        //
	        g2d.drawLine(320, 100, 320, 300);
	        g2d.drawLine(320, 300, 520, 300);
	        // dibujar puntos
	        for(Punto p: puntos)
	        {
	        	int ex = 0;
	        	int ey = 0;
	        	
	        	ex = ((Double)(200*p.getX())).intValue();
	        	ex = ex + 20;
	        	ey = ((Double)(200*p.getY())).intValue();
	        	ey = 300 - ey;
	        		        	
	        	if(p.esValido())
	        		g2d.setColor(Color.GREEN);
	        	else
	        		g2d.setColor(Color.RED);
	        	g2d.drawOval(ex, ey, 3, 3);
	        }
	        
	        // dibujar diferencias
	        for(Punto p: diferencias)
	        {
	        	int ex = 0;
	        	int ey = 0;
	        	
	        	ex = ((Double)(200*p.getX()/100000)).intValue();
	        	ex = ex + 320;
	        	ey = ((Double)(200*p.getY()*10)).intValue();
	        	ey = 300 - ey;
	        	g2d.setBackground(Color.BLUE);	        	
        		g2d.setColor(Color.BLUE);
	        	g2d.drawOval(ex, ey, 5, 5);
	        }
	    }
	}
	
	// Clase para definir un punto en la gráfica
	class Punto
	{
		private double x;
		private double y;
		private boolean valido;
		
		public Punto(double x1, double y1)
		{
			x = x1;
			y = y1;
			
			double h = x * x + y * y;
			if(h <= 1)
				valido = true;
			else
				valido = false;
		}
		
		public double getX()
		{
			return x;
		}
		
		public double getY()
		{
			return y;
		}
		
		public boolean esValido()
		{
			return valido;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == botonCalcular)
		{	
			int n = Integer.parseInt(textPuntos.getText());
			// Calculos
			panelInf.borrarPuntos();
			for(int i=0; i<n; i++)
			{
				double x = ThreadLocalRandom.current().nextDouble();
		        double y = ThreadLocalRandom.current().nextDouble();
		        panelInf.agregarPunto(new Punto(x, y));
			}
			// Cálculo del pi esperado
			DecimalFormat df = new DecimalFormat("#.###");
			df.setRoundingMode(RoundingMode.CEILING);
			double pi = ((double)panelInf.puntosValidos()/(double)panelInf.puntosTotales())*4;
			textPi.setText(df.format(pi));
			df = new DecimalFormat("#.##");
			double error = Math.abs(Math.PI-pi);
			textError.setText(df.format(error));
			//
			panelInf.agregarDiferencia(new Punto(n, error));
			//
			panelSup.repaint();
			panelInf.repaint();
		}
		else if(arg0.getSource() == botonReset)
		{
			panelInf.borrarDiferencias();
			panelInf.borrarPuntos();
			panelSup.repaint();
			panelInf.repaint();
		}
	}
}