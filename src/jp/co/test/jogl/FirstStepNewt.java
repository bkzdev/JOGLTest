package jp.co.test.jogl;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import static com.jogamp.opengl.GL2.*;

/**
 * @author nagata
 * http://toruwest.github.io/jogl-tutorial-document/<br>
 * 4.5まで<br>
 * ウィンドウ作ったり点や線書いたり色つけたり
 */
public class FirstStepNewt implements GLEventListener {

	public static void main(String[] args){
		new FirstStepNewt();
	}

	public FirstStepNewt(){
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		GLWindow glWindow = GLWindow.create(caps);			//Windowのインスタンス作成
		glWindow.setTitle("First demo (Newt)");				//タイトルバー作成
		glWindow.setSize(300, 300);							//ウィンドウサイズ設定

		glWindow.addWindowListener(new WindowAdapter(){		//ウインドウ終了時の処理
			public void windowDestroyed(WindowEvent evt){
				System.exit(0);								//(アプリケーション終了)
			}
		});
		glWindow.addGLEventListener(this);		//どのクラスがJOGLのイベントを受け取るか

		Animator animator = new Animator();
		animator.add(glWindow);
		animator.start();
		glWindow.setVisible(true);				//可視化
	}

	/**
	 * @see com.jogamp.opengl.GLEventListener#display(com.jogamp.opengl.GLAutoDrawable)
	 * @param drawable GLAutoDrawableクラスのインスタンス
	 */
	public void display(GLAutoDrawable drawable) {
		//3.4 ウィンドウを青く塗りつぶす
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);		//glClearColorで指定した色で塗りつぶす

		//4.4 線に色をつける
//		gl.glColor3f(1.0f, 0.0f, 0.0f);

		//4.1 線を引く
		//https://www.khronos.org/registry/OpenGL-Refpages/gl2.1/xhtml/glBegin.xml　のコマンドを使用できるようになる
//		gl.glBegin(GL_LINE_LOOP);	//モードの指定も上記リファレンス参照

		//6.1 回転
		gl.glRotatef(2.5f, 0f, 1f, 0f);	//25度回転

		gl.glBegin(GL_POLYGON);	//線をやめて塗りつぶしに

		gl.glColor3f(1.0f, 0.0f, 0.0f);	//単色やめてグラデに
		gl.glVertex2f(-0.9f, -0.9f);

		gl.glColor3f(0.0f, 1.0f, 0.0f);	//緑
		gl.glVertex2f(0.9f, -0.9f);

		gl.glColor3f(0.0f, 0.0f, 1.0f);	//青
		gl.glVertex2f(0.9f, 0.9f);

		gl.glColor3f(1.0f, 1.0f, 0.0f);	//黄
		gl.glVertex2f(-0.9f, 0.9f);

		gl.glEnd();

		//5.4 座標変換
		//gl.glTranslatef(1.0f, 0.0f, 0.0f);		//平行移動
		//gl.glRotatef(1.0f, 1.0f, 1.0f, 1.0f);		//回転
		//gl.glScalef(1.0f, 0.5f, 1.0f);			//拡大縮小

	}

	/**
	 * OpenGLコンテキストが破棄された時に呼ばれる
	 *
	 * @see com.jogamp.opengl.GLEventListener#dispose(com.jogamp.opengl.GLAutoDrawable)
	 * @param drawable GLAutoDrawableクラスのインスタンス
	 */
	public void dispose(GLAutoDrawable drawable) {
	}

	/**
	 * OpenGLコンテキストの起動時に呼ばれる
	 *
	 * @see com.jogamp.opengl.GLEventListener#init(com.jogamp.opengl.GLAutoDrawable)
	 * @param drawable GLAutoDrawableクラスのインスタンス
	 */
	public void init(GLAutoDrawable drawable) {
		//3.4 ウィンドウを青く塗りつぶす
		GL2 gl = drawable.getGL().getGL2();
//		gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);	//glClearの色を指定する　R,G,B,透明度を0～1で指定する
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);	//glClearの色を指定する　R,G,B,透明度を0～1で指定する
	}

	/**
	 * ウィンドウサイズが変更された場合に呼ばれる
	 *
	 * @see com.jogamp.opengl.GLEventListener#reshape(com.jogamp.opengl.GLAutoDrawable, int, int, int, int)
	 * @param drawable
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

	}

}
