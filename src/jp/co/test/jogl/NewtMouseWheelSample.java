package jp.co.test.jogl;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import static com.jogamp.opengl.GL.*;

/**
 * @author nagata
 * 5.6
 * ホイールで図形を拡大縮小・キーボードいろいろ
 */
public class NewtMouseWheelSample implements GLEventListener, KeyListener {

	private static final char KEY_ESC = 0x1b;
	private static final float INIT_SCALE = 20f;
	private static final int HEIGHT = 300;
	private static final int WIDTH = 300;

	private float scale = INIT_SCALE;

	public NewtMouseWheelSample(){
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		final GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Mouse wheel sample (Newt)");
		glWindow.setSize(WIDTH, HEIGHT);

		glWindow.addWindowListener(new WindowAdapter(){
			public void windowDestroyed(){
				System.exit(0);
			}
		});

		glWindow.addGLEventListener(this);
		glWindow.addMouseListener(new MouseAdapter(){
			public void mouseWheelMoved(MouseEvent e){
				float[] rot = e.getRotation();				//ここでホイールを取り出す
				scale *= (rot[1] > 0 ? 1.005f : 0.995f);	//3Dマウス用？に3軸あるため配列だがホイールの回転は[1]
				System.out.println("scale:"+scale);
			}
		});

		glWindow.addKeyListener(this);

		FPSAnimator animator = new FPSAnimator(10);
		animator.add(glWindow);
		animator.start();
		glWindow.setPosition(500, 500);
		glWindow.setVisible(true);
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(WIDTH/2 - scale, HEIGHT/2 - scale);
		gl.glVertex2f(WIDTH/2 + scale, HEIGHT/2 - scale);
		gl.glVertex2f(WIDTH/2 + scale, HEIGHT/2 + scale);
		gl.glVertex2f(WIDTH/2 - scale, HEIGHT/2 + scale);
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
		gl.glOrthof(x, x+width, y, y+height, -1.0f, 1.0f);

	}

	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void keyReleased(KeyEvent e) {
		char keyChar = e.getKeyChar();

		if(keyChar == ' '){
			scale = INIT_SCALE;
			System.out.println("scale:"+scale);
		}
		if(keyChar == 'q' || keyChar == KEY_ESC) System.exit(0);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NewtMouseWheelSample();
	}

}
