package jp.co.test.jogl;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.gl2.GLUT;

public class SimpleCube implements GLEventListener {
    private GL gl;
    private GL2 gl2;
    private GLUT glut;

    public SimpleCube() {
        Frame frame = new Frame("Simple Cube");

        // 3Dを描画するコンポーネント
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(this);

        frame.add(canvas);
        frame.setSize(640, 480);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    public void init(GLAutoDrawable drawable) {
        // 初期化処理
        gl = drawable.getGL();
        gl2 = gl.getGL2();
        glut = new GLUT();
    }

    public void reshape(GLAutoDrawable drawable,
                        int x, int y,
                        int width, int height) {
        // 描画領域変更処理
        float ratio = (float)height / (float)width;

        gl2.glViewport(0, 0, width, height);

        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        gl2.glFrustum(-1.0f, 1.0f, -ratio, ratio,
                     5.0f, 40.0f);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();
        gl2.glTranslatef(0.0f, 0.0f, -20.0f);
    }

    public void display(GLAutoDrawable drawable) {
        // 描画処理
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

        // 大きさ 2 の線画の立方体を描画
        glut.glutWireCube(2.0f);
    }

    public void displayChanged(GLAutoDrawable drawable,
                               boolean modeChanged,
                               boolean deviceChanged) {
    }

    public static void main(String[] args) {
        new SimpleCube();
    }

    public void dispose(GLAutoDrawable arg0) {
    }
}