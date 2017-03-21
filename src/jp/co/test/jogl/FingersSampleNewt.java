package jp.co.test.jogl;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import java.awt.event.KeyEvent;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

/**
 * @author 
 * 
 * http://toruwest.github.io/jogl-tutorial-document/<br>
 * 10.階層構造
 *
 */
public class FingersSampleNewt implements GLEventListener {
	protected GLCapabilities caps;
	private final Animator animator;
	
	private float viewScale = 0.04f;
	private int prevMouseX = -1;
	
	//
	private static final int FINGERS_COUNT = 3*5;
	private static Finger[] fingers;
	private Palm palm;
	private int fingerNumber = 1;
	
	private final float[] red =	   {1f, 0f, 0f};
	private final float[] green =  {0f, 1f, 0f};
	private final float[] blue =   {0f, 0f, 1f};
	private final float[] orange = {1f, 0f, 1f};
	
	public FingersSampleNewt(){
		GLProfile prof = GLProfile.get(GLProfile.GL2);
		caps = new GLCapabilities(prof);
		
		setupFingers();
		
		GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Finger demo (Newt)");
		glWindow.setSize(500, 500);
		glWindow.addGLEventListener(this);
		
		glWindow.addWindowListener(new WindowAdapter(){
			public void windowDestroyed(WindowEvent evt){
				quit();
			}
		});
		
		glWindow.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				prevMouseX = -1;
			}
			
			public void mouseDragged(MouseEvent evt){
				int x = evt.getX();
				if(prevMouseX != -1){
					float rotDelta = (prevMouseX - x);
					int fingerIndex = (fingerNumber -1)*3;
					if(evt.isControlDown()){
						fingers[2 + fingerIndex].updateRotation(rotDelta);
					} else if(evt.isShiftDown()){
						fingers[1 + fingerIndex].updateRotation(rotDelta);
					} else {
						fingers[0 + fingerIndex].updateRotation(rotDelta);
					}
				}
				//現在のマウスの位置を保存
				prevMouseX = x;
			}

			public void mouseWheelMoved(MouseEvent e){
				float[] rot = e.getRotation();
				viewScale *= (rot[1] > 0 ? 1.005f : 0.995f);
			}
		});

		glWindow.addKeyListener(new KeyAdapter(){
			public void KeyPressed(KeyEvent key){
				switch(key.getKeyChar()){
				case KeyEvent.VK_ESCAPE:
					quit();
					break;

				case 'q':
					quit();
					break;
				case '1':
					fingerNumber = 1;
					System.out.println("1:小指");
					break;
				case '2':
					fingerNumber = 2;
					System.out.println("2:薬指");
					break;
				case '3':
					fingerNumber = 3;
					System.out.println("3:中指");
					break;
				case '4':
					fingerNumber = 4;
					System.out.println("4:人差し指");
					break;
				case '5':
					fingerNumber = 5;
					System.out.println("5:親指");
					break;
				}
			}
		});
		
		glWindow.setVisible(true);
		animator = new Animator(glWindow);
		animator.start();
	}

	private void setupFingers(){
		fingers = new Finger[FINGERS_COUNT];
		
		palm = new Palm(10, 0, -20, orange);
		
		//positionは指の根本の場合に使い、2番目以降は親アンカーの位置に合わせる
		
		//　小指の設定
		fingers[0] = new Finger(-9.0f, 10f, 7f, 0f, red);
		fingers[1] = new Finger(fingers[0], 7f, 0f, blue);
		fingers[2] = new Finger(fingers[1], 5f, 0f, green);
		
		//　薬指の設定
		fingers[3] = new Finger(-4.5f, 10f, 8f, 0f, red);
		fingers[4] = new Finger(fingers[3], 8f, 0f, blue);
		fingers[5] = new Finger(fingers[4], 7f, 0f, green);
		
		//　中指の設定
		fingers[6] = new Finger(0.0f, 10f, 9f, 0f, red);
		fingers[7] = new Finger(fingers[6], 9f, 0f, blue);
		fingers[8] = new Finger(fingers[7], 8f, 0f, green);
		
		//　人差し指の設定
		fingers[9] = new Finger(4.5f, 10f, 7f, 0f, red);
		fingers[10] = new Finger(fingers[9], 7f, 0f, blue);
		fingers[11] = new Finger(fingers[10], 9f, 0f, green);
		
		//　親指の設定
		fingers[12] = new Finger(9.0f, 7f, 6f, 0f, red);
		fingers[13] = new Finger(fingers[12], 6f, 0f, blue);
		fingers[14] = new Finger(fingers[13], 5f, 0f, green);
	}
	
	protected void quit() {
		animator.stop();
		System.exit(0);
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glClear(GL_COLOR_BUFFER_BIT);
		gl2.glLoadIdentity();
		gl2.glScalef(viewScale, viewScale, viewScale);
		
		//手のひらを描く
		palm.render(gl2);
		
		//指を描く
		for (int i=0; i<FINGERS_COUNT; i++){
			fingers[i].render(gl2);
		}

	}

	public void dispose(GLAutoDrawable drawable) {
		if(animator != null)animator.stop();
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glClearColor(1f, 1f, 1f, 1f);
		gl2.glClearDepth(1.0f);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("指をドラッグして角度を変えられます。第一関節(指先)を操作するにはCTRLを、第二関節を操作するにはSHIFTを押しながらドラッグしてください。");
		System.out.println("操作する指を変えるには1(小指)から5(親指)を押してください。");
		new FingersSampleNewt();
	}

}
