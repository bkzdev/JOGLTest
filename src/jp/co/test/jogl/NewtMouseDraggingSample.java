package jp.co.test.jogl;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import static com.jogamp.opengl.GL2.*;

/**
 * @author nagata
 * 5.5
 * マウスドラッグでおえかき
 */
public class NewtMouseDraggingSample implements GLEventListener, MouseListener {
	private final List<List> pointsList;
	private final List points;

	public NewtMouseDraggingSample(){
		pointsList = new ArrayList();
		points = new ArrayList();

		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		final GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Mouse Drag Sample (Newt)");
		glWindow.setSize(300, 300);
		glWindow.addWindowListener(new WindowAdapter(){
			public void windowDestroyed(WindowEvent evt){
				System.exit(0);
			}
		});

		glWindow.addGLEventListener(this);
		glWindow.addMouseListener(this);
		FPSAnimator animator = new FPSAnimator(60);
		animator.add(glWindow);
		animator.start();
		glWindow.setPosition(500, 500);
		glWindow.setVisible(true);
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		render(gl, points);
		for(List p : pointsList){
			render(gl, p);
		}

	}

	private void render(GL2 gl, List p) {
		gl.glBegin(GL_LINES);

		for(int i=0;i<p.size()-1;i++){
			Point2D.Float p0 = (Point2D.Float) p.get(i);
			Point2D.Float p1 = (Point2D.Float) p.get(i + 1);
			gl.glVertex2d(p0.getX(), p0.getY());
			gl.glVertex2d(p1.getX(), p1.getY());
		}
		gl.glEnd();
	}

	public void dispose(GLAutoDrawable drawable) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glClearColor(1f, 1f, 1f, 1.0f);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		System.out.printf("x:%d, y:%d, w:%d, h:%d, %n", x, y, width, height);
		gl.glOrthof(x, x+width, y+height, y, -1.0f, 1.0f);

		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();

	}

	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void mouseDragged(MouseEvent e) {
		points.add(new Point2D.Float(e.getX(), e.getY()));

	}

	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void mouseReleased(MouseEvent e) {
		pointsList.add(new ArrayList(points));
		points.clear();

	}

	public void mouseWheelMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NewtMouseDraggingSample();

	}

}
