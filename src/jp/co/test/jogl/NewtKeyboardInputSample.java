package jp.co.test.jogl;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * @author nagata
 * 5.7
 * キーボードの操作をコンソールに出す
 */
public class NewtKeyboardInputSample implements GLEventListener, KeyListener {
	private static final char KEY_ESC = 0x1b;
	private final GLWindow glWindow;

	public NewtKeyboardInputSample(){
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		glWindow = GLWindow.create(caps);
		glWindow.setTitle("Keyboard input demo (Newt)");
		glWindow.setSize(300, 300);

		glWindow.addWindowListener(new WindowAdapter(){
			public void windowDestroyed(WindowEvent evt){
				System.exit(0);
			}
		});

		glWindow.addGLEventListener(this);
		glWindow.addKeyListener(this);
		FPSAnimator animator = new FPSAnimator(10);
		animator.add(glWindow);
		animator.start();
		glWindow.setVisible(true);
	}

	public void display(GLAutoDrawable drawable) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void dispose(GLAutoDrawable drawable) {
		System.out.println("dispose()");
//		if(animator != null) animator.stop();
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0f, 0f, 0f, 1.0f);
//		showGLInfo(drawable);

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void keyPressed(KeyEvent e) {
		char keyChar = e.getKeyChar();
		printKeyChar(keyChar, " pressed");

		if(e.isAltDown()){
			System.out.println("ALT key pressed");
		}
		if(e.isShiftDown()){
			System.out.println("SHIFT key pressed");
		}
		if(e.isControlDown()){
			System.out.println("Ctrl key pressed");
		}
		if(e.isAltGraphDown()){
			System.out.println("AltGraph key pressed");
		}
		if(e.isMetaDown()){
			System.out.println("Meta key pressed");
		}
	}

	public void keyReleased(KeyEvent e) {
		char keyChar = e.getKeyChar();
		printKeyChar(keyChar, "released");

		if(Character.isISOControl(keyChar)){
			System.out.println(Integer.valueOf(keyChar) + " released");
		} else {
			System.out.println(keyChar + " released");
		}



		if(e.isAltDown()){
			System.out.println("ALT key released");
		}
		if(e.isShiftDown()){
			System.out.println("SHIFT key released");
		}
		if(e.isControlDown()){
			System.out.println("Ctrl key released");
		}
		if(e.isAltGraphDown()){
			System.out.println("AltGraph key released");
		}
		if(e.isMetaDown()){
			System.out.println("Meta key released");
		}

		if(keyChar == KEY_ESC || keyChar == 'q' || keyChar == 'Q'){
			glWindow.destroy();
		}
	}

	private void printKeyChar(char keyChar, String type){
		if(Character.isISOControl(keyChar)){
			System.out.println(Integer.valueOf(keyChar) + type);
		} else {
			System.out.println(keyChar + type);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		new NewtKeyboardInputSample();
	}

}
