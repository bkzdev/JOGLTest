package jp.co.test.jogl;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import static com.jogamp.opengl.GL2.*;

/**
 * @author nagata
 * 5.2
 * いろんな点とか線を書くサンプル
 */
public class LineAndPointNewt1 implements GLEventListener {

	private float[] colors;
	private final short linePattern = (short) Integer.parseInt("111100011001010", 2);;

	public void display(GLAutoDrawable drawable) {
		final GL2 gl2 = drawable.getGL().getGL2();
		gl2.glClear(GL_COLOR_BUFFER_BIT);

		//大きさの違う点
		for(int i=0;i<8;i++){
			gl2.glPointSize((i+1)*0.5f);	//点の大きさ設定
			gl2.glColor3f(1.0f,1.0f,1.0f);
			gl2.glBegin(GL_POINTS);			//点を描写
			gl2.glVertex2f(-0.9f,(i-7)*(1.6f/7f)+0.8f);
			gl2.glEnd();
		}

		//濃度の違う点
		for(int i=0;i<8;i++){
			gl2.glPointSize(2f);
			gl2.glBegin(GL_POINTS);			//点を描写
			gl2.glColor3f(colors[i], colors[i], colors[i]);
			gl2.glVertex2f(-0.8f, (i-7)*(1.6f/7f)+0.8f);
			gl2.glEnd();
		}

		//太さの違う線
		for(int i=1;i<9;i++){
			gl2.glLineWidth(i*0.5f);		//線の幅を設定
			gl2.glColor3f(1.0f,1.0f,1.0f);
			gl2.glBegin(GL_LINES);			//線を描写
			gl2.glVertex2f(-0.6f + i*0.05f, -0.8f);
			gl2.glVertex2f(-0.6f + i*0.05f, +0.8f);
			gl2.glEnd();
		}

		//濃度の違う線
		gl2.glLineWidth(1f);
		for(int i=0;i<8;i++){
			gl2.glColor3f(colors[i], colors[i], colors[i]);
			gl2.glBegin(GL_LINES);
			gl2.glVertex2f(-0.1f + i*0.05f, -0.8f);
			gl2.glVertex2f(-0.1f + i*0.05f, +0.8f);
			gl2.glEnd();
		}

		//破線の色とスケールが違う線
		gl2.glEnable(GL_LINE_STIPPLE);		//OpenGLの機能を有効化(破線を指定) 定数はhttps://www.khronos.org/registry/OpenGL-Refpages/gl2.1/xhtml/glEnable.xml
		for(int i=0;i<8;i++){
			gl2.glLineStipple(i+1, linePattern);	//破線のパターンを2進数で指定
			gl2.glColor3f(colors[i], colors[i], colors[i]);
			gl2.glBegin(GL_LINES);
			gl2.glVertex2f(+0.4f + i*0.05f, -0.8f);
			gl2.glVertex2f(+0.4f + i*0.05f, +0.8f);
			gl2.glEnd();
		}
		gl2.glDisable(GL_LINE_STIPPLE);		//OpenGLの機能を無効化(破線を指定)

	}

	public void dispose(GLAutoDrawable drawable) {
//		if(animator != null)animator.stop();
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LineAndPointNewt1();

	}

	public LineAndPointNewt1(){
		initColors();
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Line and point (Newt)");
		glWindow.setSize(400, 300);
		glWindow.addWindowListener(new WindowAdapter(){
			public void windowDestroyed(WindowEvent evt){
				System.exit(0);
			}
		});
		glWindow.addGLEventListener(this);
		FPSAnimator animator = new FPSAnimator(60);
		animator.add(glWindow);
		animator.start();
		glWindow.setVisible(true);
	}

	private void initColors(){
		colors = new float[8];
		for(int i = 0;i<8;i++){
			colors[i] = 0.3f + (0.1f * i);
		}
	}

}
