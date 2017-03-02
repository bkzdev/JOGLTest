package jp.co.test.jogl;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
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
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import static com.jogamp.opengl.GL2.*;

public class CubeSample implements GLEventListener, MouseListener, KeyListener {

	float[][] vertex = {
			{ 0.0f, 0.0f, 0.0f },	//A
			{ 1.0f, 0.0f, 0.0f },	//B
			{ 1.0f, 1.0f, 0.0f },	//C
			{ 0.0f, 1.0f, 0.0f },	//D
			{ 0.0f, 0.0f, 1.0f },	//E
			{ 1.0f, 0.0f, 1.0f },	//F
			{ 1.0f, 1.0f, 1.0f },	//G
			{ 0.0f, 1.0f, 1.0f }	//H
	};
	
	//8.1 多面体を塗りつぶす
	private final int face[][] = {
			{ 0, 1, 2, 3 },		//A-B-C-Dを結ぶ面
			{ 1, 5, 6, 2 },		//B-F-G-Cを結ぶ面
			{ 5, 4, 7, 6 },		//F-E-H-Gを結ぶ面
			{ 4, 0, 3, 7 },		//E-A-D-Hを結ぶ面
			{ 4, 5, 1, 0 },		//E-F-B-Aを結ぶ面
			{ 3, 2, 6, 7 }		//D-C-G-Hを結ぶ面
	};

	int[][] edge = {
			{ 0, 1},	//ア(A-B)
			{ 1, 2},	//イ(B-C)
			{ 2, 3},	//ウ(C-D)
			{ 3, 0},	//エ(D-A)
			{ 4, 5},	//オ(E-F)
			{ 5, 6},	//カ(F-G)
			{ 6, 7},	//キ(G-H)
			{ 7, 4},	//ク(H-E)
			{ 0, 4},	//ケ(A-E)
			{ 1, 5},	//コ(B-F)
			{ 2, 6},	//サ(C-G)
			{ 3, 7}		//シ(D-H)
	};
	
	//8.1 多面体を塗りつぶす
	private final float color[][] = {
			{ 1.0f, 0.0f, 0.0f },	//赤
			{ 0.0f, 1.0f, 0.0f },	//緑
			{ 0.0f, 0.0f, 1.0f },	//青
			{ 1.0f, 1.0f, 0.0f },	//黄
			{ 1.0f, 0.0f, 1.0f },	//マゼンタ
			{ 0.0f, 1.0f, 1.0f }	//シアン
	};

	private final GLU glu;
	//8.1 多面体を塗りつぶす
	private final FPSAnimator animator;
	//7.1 図形を動かす
	//private final Animator animator;
	private final GLWindow glWindow;
	private boolean willAnimatorPause = false;
	private static final char KEY_ESC = 0x1b;

	float r = 0;	//回転角

	public CubeSample(){
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		glu = new GLU();

		glWindow = GLWindow.create(caps);
		glWindow.setTitle("Cube demo (Newt)");
		glWindow.setSize(300, 300);
		glWindow.addGLEventListener(this);

		glWindow.addWindowListener(new WindowAdapter(){
			public void windowDestroyed(WindowEvent evt){
				System.exit(0);
			}
		});

		glWindow.addMouseListener(this);
		glWindow.addKeyListener(this);
		//8.1 多面体を塗りつぶす
		animator = new FPSAnimator(30);
		//animator = new Animator();
		animator.add(glWindow);
		animator.start();
		animator.pause();
		glWindow.setVisible(true);
	}

	public void display(GLAutoDrawable drawable) {
		//7.3 Animatorとスレッド
		dumpThread("display");
		GL2 gl = drawable.getGL().getGL2();
		// 8.2 デプスバッファ
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//		gl.glClear(GL_COLOR_BUFFER_BIT);

		//7.1 図形を動かす
		gl.glLoadIdentity();
		gl.glTranslatef(0.5f, 0.5f, 0.5f);	//回転
		gl.glRotatef(r, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(-0.5f, -0.5f, -0.5f);

		//gl.glColor3f(0.0f, 0.0f, 0.0f);		//描写
		//8.1 多面体を塗りつぶす
		gl.glBegin(GL_QUADS);
		for(int j=0; j<6;++j){
			gl.glColor3fv(color[j],0);
			for(int i=0; i<4;++i){
				gl.glVertex3fv(vertex[face[j][i]], 0);
			}
		}
		//gl.glBegin(GL_LINES);
		//for(int i=0;i<12;i++){
		//	gl.glVertex3fv(vertex[edge[i][0]], 0);
		//	gl.glVertex3fv(vertex[edge[i][1]], 0);
		//}
		gl.glEnd();

		//7.1 図形を動かす
		if(r++ >= 360.0f) r = 0;
		System.out.println("anim:" + animator.isAnimating() + ", r:" + r);
		if(willAnimatorPause){
			animator.pause();
			System.out.println("animator paused");
			willAnimatorPause = false;
		}
		//7.2 ダブルバッファリング
		drawable.swapBuffers();
	}

	public void dispose(GLAutoDrawable drawable) {
		if(animator != null) animator.stop();
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(1f, 1f, 1f, 1.0f);	//白で塗りつぶす
		// 8.2 デプスバッファ
		gl.glEnable(GL_DEPTH_TEST);
		// 8.3 カリング
		gl.glEnable(GL_CULL_FACE);
		gl.glCullFace(GL_FRONT);

		//7.2 ダブルバッファリング
		System.out.println("auto swap:"+drawable.getAutoSwapBufferMode());
		drawable.setAutoSwapBufferMode(false);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		//gl.glOrtho(-2.0, 2.0, -2.0, 2.0, -2.0, 2.0);

		//7.1 図形を動かす
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();

		//6.3 透視投影
		glu.gluPerspective(30.0, (double)width/(double)height, 1.0, 100.0);
		//gl.glTranslatef(0.0f, 0.0f, -5.0f);
		//6.4 視点の位置変更
		glu.gluLookAt(5.0f, 5.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

		gl.glMatrixMode(GL_MODELVIEW);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CubeSample();
	}

	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void mouseDragged(MouseEvent e) {
		//7.3 Animatorとスレッド
		dumpThread("dragging");

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
		switch(e.getButton()){
		case MouseEvent.BUTTON1:
			animator.resume();
			System.out.println("button 1, left click");
			break;
		case MouseEvent.BUTTON2:
			System.out.println("button 2");
			break;
		case MouseEvent.BUTTON3:
			System.out.println("button 3, right click");
			willAnimatorPause = true;
			animator.resume();
			break;
		default:
		}
	}

	public void mouseReleased(MouseEvent e) {
		animator.pause();
	}

	public void mouseWheelMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void keyReleased(KeyEvent e) {
		char keyChar = e.getKeyChar();
		if(keyChar == KEY_ESC || keyChar == 'q' || keyChar =='Q'){
			glWindow.destroy();
		}
	}

	//7.3 Animatorとスレッド
	private void dumpThread(String name) {
		Thread th = Thread.currentThread();
		System.out.println(name + ":" + th.getName() + ", " + th.getState());

	}

}
