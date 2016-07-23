package com.gt.visualization;

import java.awt.DisplayMode;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.util.FPSAnimator;

public class Sound3d implements GLEventListener, MouseListener, MouseMotionListener {

	public static DisplayMode dm, dm_old;
	private GLU glu = new GLU();
	private float rquad = 0.0f;
	private static List<List<double[]>> soundWave;

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(-500f, 0f, -100f);
		gl.glRotatef(rquad, 10f, 0f, 0f); // Rotate The Cube On X, Y & Z
		// giving different colors to different sides
		gl.glBegin( GL2.GL_POLYGON);		
		List<double[]> firstDimension = soundWave.get(0);
		List<double[]> secondDimension = soundWave.get(1);
		List<double[]> thirdDimension = soundWave.get(2);
		for(int i=0;i<firstDimension.get(0).length;i++){
			gl.glColor3f( 1f,0f,0f ); 
			for(int v=0;v<8;v++){
				gl.glVertex3d(firstDimension.get(0)[i], 
								secondDimension.get(0)[i]*10, 
								thirdDimension.get(v)[i]*10);
			}	
			//gl.glEvalCoord2d(firstDimension[i], secondDimension[i]);
		}
		gl.glEnd(); // Done Drawing The Quad
		gl.glFlush();
		rquad += 0.15f;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
	}

	@SuppressWarnings("unused")
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL2 gl = drawable.getGL().getGL2();
		if (height == 0) height = 1;   // prevent divide by zero
	      float aspect = (float)width / height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(500.0f, 7, 1, 100.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public static void main(String[] args) {
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		try {
			DataForVisualizationCreator dataCreator = new DataForVisualizationCreator();
			soundWave = dataCreator.createDataForVisualization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// The canvas
		final GLCanvas glcanvas = new GLCanvas(capabilities);
		Sound3d wave = new Sound3d();
		glcanvas.addGLEventListener(wave);
		glcanvas.setSize(1200, 800);
		final JFrame frame = new JFrame(" Multicolored cube");
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
		animator.start();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("mouse dragged");
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("mouse moved");
		
	}

	@Override
	public void mouseClicked(com.jogamp.newt.event.MouseEvent arg0) {
		System.out.println("jogl mouse clicked");
		
	}

	@Override
	public void mouseDragged(com.jogamp.newt.event.MouseEvent arg0) {
		System.out.println("jogl mouse dragged");
		
	}

	@Override
	public void mouseEntered(com.jogamp.newt.event.MouseEvent arg0) {
		System.out.println("jogl mouse entered");
		
	}

	@Override
	public void mouseExited(com.jogamp.newt.event.MouseEvent arg0) {
		System.out.println("jogl mouse exited");
		
	}

	@Override
	public void mouseMoved(com.jogamp.newt.event.MouseEvent arg0) {
		System.out.println("jogl mouse moved");
		
	}

	@Override
	public void mousePressed(com.jogamp.newt.event.MouseEvent arg0) {
		System.out.println("jogl mouse pressed");
		
	}

	@Override
	public void mouseReleased(com.jogamp.newt.event.MouseEvent arg0) {
		System.out.println("jogl mouse released");
		
	}

	@Override
	public void mouseWheelMoved(com.jogamp.newt.event.MouseEvent arg0) {
		System.out.println("jogl mouse wheel moved");
		
	}

}
