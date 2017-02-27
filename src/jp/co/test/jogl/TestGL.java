package jp.co.test.jogl;

//面倒くさいので、GL2とGLはstatic importしておきましょう。
//＊GL2必須
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.gl2.GLUT;

/**
 * 櫻庭さんの2006年の記事
 * http://itpro.nikkeibp.co.jp/article/COLUMN/20060718/243509/?ST=develop
 * を2013年で動くようにした物です。
 * @author nodamushi
 */
public class TestGL implements GLEventListener{
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new TestGL();
      }
    });
  }

  //バージョンはGL2以降を利用しないとメソッドが定義されていない
  private GL2 gl;
  private GLUT glut;

  public TestGL() {
    JFrame frame = new JFrame("Simple Cube");

    //3Dを描画するコンポーネント
    //com.jogamp.opengl.swt.GLCanvasってのもあるので
    //インポートを間違えないように。
    //使わないし、swt以下にはアクセス禁止設定した方が楽かも
    GLCanvas canvas = new GLCanvas();
    canvas.addGLEventListener(this);

    frame.add(canvas);
    frame.setSize(300, 300);
    frame.setDefaultCloseOperation(
        JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
     }

  public void display(GLAutoDrawable drawable) {
    gl.glClear(GL_COLOR_BUFFER_BIT);
    // 大きさ 2 の線画の立方体を描画
    glut.glutWireCube(2.0f);
  }

  public void dispose(GLAutoDrawable drawable) {

  }

  public void init(GLAutoDrawable drawable) {
    gl=drawable.getGL().getGL2();
    glut = new GLUT();
  }
  public void reshape(GLAutoDrawable drawable,
      int x, int y, int width,int height) {
    float ratio = (float)height / (float)width;
    gl.glViewport(0, 0, width, height);

    //定数はGLではなく、GL2にあります。
    //（正確にはjavax.media.opengl.fixedfunc.GLMatrixFuncみたい）
    gl.glMatrixMode(GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glFrustum(-1.0f, 1.0f, -ratio, ratio,
        5.0f, 40.0f);

    gl.glMatrixMode(GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glTranslatef(0.0f, 0.0f, -20.0f);
  }
}