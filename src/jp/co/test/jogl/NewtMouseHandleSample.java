package jp.co.test.jogl;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.newt.event.MouseAdapter;
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
 * 5.2
 * マウスイベントを使って線を描く
 */
public class NewtMouseHandleSample implements GLEventListener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NewtMouseHandleSample();
	}

	private final List points;

	public NewtMouseHandleSample(){
		points = new ArrayList();

		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		final GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Mouse Handle Sample (Newt)");
		glWindow.setSize(300, 300);

		glWindow.addWindowListener(new WindowAdapter(){
			public void windowDestroyed(WindowEvent evt){
				System.exit(0);
			}
		});

		glWindow.addGLEventListener(this);
//		glWindow.addMouseListener(this);			//マウスイベントをこのクラスで受け取る
		/**
		 * アダプタを使ってクラスにイベントリスナーを実装しないで済むようにしている
		 * 以下主要なメソッド(詳しくはAPIdoc)
		 *
		 * int getX();	ウインドウ左上からの画素の位置
		 * int getY();	ウインドウ左上からの画素の位置
		 * short getClickCount();	ダブルクリック検知で使うらしい
		 * short getButton();		どのボタンが押されたか
		 * int getModifiers();		特殊キーの検知
		 * float[] getRotation();
		 * float getRotationScale();
		 * float[] getRotationXYZ(float rotationXorY, int mods);
		 * float getMaxPressure();
		 * float[] getAllPressures();
		 * float getPressure(boolean normalized);
		 * float getPressure(int index, boolean normalized);
		 */
		glWindow.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				System.out.printf("%d, %d%n", e.getX(), e.getY());
				points.add(new Point2D.Float(e.getX(), e.getY()));		//クリックされた位置をListに格納する(displayで描写する)
				System.out.println("mouse clicked count:" + e.getClickCount());
				System.out.println("mouse source:" + e.getButton());
				System.out.println("mouse button 1:" + MouseEvent.BUTTON1);
				System.out.println("mouse button 2:" + MouseEvent.BUTTON2);
				System.out.println("mouse button 3:" + MouseEvent.BUTTON3);

			}
			public void mouseReleased(MouseEvent e) {
				System.out.println("mouset released");
			}
			//以下使ってないメソッド
			//マウスを押すとPressed > Released > Clickedの順で呼ばれる
			//public void mouseDragged(MouseEvent e) {}
			//public void mouseEntered(MouseEvent e) {}
			//public void mouseExited(MouseEvent e) {}
			//public void mouseMoved(MouseEvent e) {}
			//public void mousePressed(MouseEvent e) {}
			//public void mouseWheelMoved(MouseEvent e) {}
		});

		FPSAnimator animator = new FPSAnimator(30);
		animator.add(glWindow);
		animator.start();

		glWindow.setPosition(500, 500);
		glWindow.setVisible(true);
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL_LINES);

		for(int i=0;i<points.size()-1;i++ ){
			Point2D.Float p0 = (Point2D.Float)points.get(i);
			Point2D.Float p1 = (Point2D.Float)points.get(i+1);
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
		gl.glClearColor(1f, 1f, 1f, 1.0f);	//背景を白に

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		//本来はビューポート設定をするが、初期値(x,y,width,height)のまま使う場合は既に内部で設定されているのでしなくていい
		//For efficiency the GL viewport has already been updated via glViewport(x, y, width, height) when this method is called.
		GL2 gl = drawable.getGL().getGL2();
		gl.glMatrixMode(GL_PROJECTION);		//変換行列を指定
		gl.glLoadIdentity();				//透視変換行列を単位行列にする？
		System.out.printf("x:%d, y:%d, w:%d, h:%d, %n", x, y, width, height );
		//ウインドウをリサイズしても中の図形の大きさを維持する
		//yと-yを逆にすることでGLWindowとデバイス座標の違い(上下逆)を吸収している
		gl.glOrthof(x, x+width, y+height, y, -1.0f, 1.0f);	//左端・右端・下端・上端・手前・奥

		gl.glMatrixMode(GL_MODELVIEW);		//変換行列を指定
		gl.glLoadIdentity();				//モデルビュー変換行列を単位行列にする？

	}

}
