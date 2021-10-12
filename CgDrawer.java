/**
 * 描画処理のためのクラス
 */


import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.GL;
import javax.media.opengl.GLEventListener;


public class CgDrawer implements GLEventListener {
	GLAutoDrawable glAD;

        static int mode = 0;
        static int pattern = 1;
	
	// 光源の位置を設定
	static float light0pos[] = { 4.0f, 3.0f, -5.0f, 1.0f };
	static float light1pos[] = { 0.0f, 1.0f, 3.0f, 0.0f };
      	
	/**
	 * 描画処理のための初期化
	 */
    public void init(GLAutoDrawable drawable) {
        float silver1[] = {0.2f, 0.2f, 0.2f, 1.0f};
        float silver2[] = {0.5f, 0.5f, 0.5f, 1.0f};
    	
        this.glAD = drawable;
      
        GL2 gl= drawable.getGL().getGL2();
	    
        // Zバッファ法を有効にする
	gl.glEnable(GL.GL_RGBA);
	gl.glEnable(GL2.GL_DEPTH);
        gl.glEnable(GL2.GL_DOUBLE);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glDisable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);
        
        // 2個の光源を有効にする
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, silver2, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, silver2, 0);
        	
        // 背景色を白にする
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        
	}
    
    /**
     * ウィンドウの大きさを変更したときに自動的に呼び出されるメソッド
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLUgl2 glu = new GLUgl2();

        // ウィンドウの横縦比を求める
        if (height <= 0) 
            height = 1;
        float h = (float) width / (float) height;

        // ビューポートを設定する
        gl.glViewport(0, 0, width, height);

        // ここから投影変換に関する設定
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(30.0, h, 1.0, 100.0);
               
        // ここから物体の幾何変換に関する設定
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        
		 
    }
    

    /**
     * 物体を描画するときに呼び出されるメソッド
     */
    public void display(GLAutoDrawable drawable) {
    	
        // 物体を描画する
        draw(drawable);
      
    }
    

    /**
     *　ディスプレイ装置自体を別の物に差し替えたときに呼び出されるメソッド
     *　（本講義では想定外なので空っぽのメソッドとしておく）
     */
    public void displayChanged(
    	GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    	;
    }

    // 視点を変更する
    static public void changeView(int m){
	mode = m;	
    }

    // 照明を変更する
    static public void changeLight(int p){
	
	pattern = p;

	if(p == 1){

	    light0pos[0] = 4.0f;
	    light0pos[1] =  3.0f;
	    light0pos[2] = -5.0f;

	    light1pos[0] = 0.0f;
	    light1pos[1] = 1.0f;
	    light1pos[2] = 3.0f;

	}else if(p == 2){

	    light0pos[0] = 0.0f;
	    light0pos[1] = 1.0f;
	    light0pos[2] = 5.5f;

	    light1pos[0] = 3.0f;
	    light1pos[1] = -1.0f;
	    light1pos[2] = -3.0f;

	}else{

	    light0pos[0] = 0.0f;
	    light0pos[1] = 1.0f;
	    light0pos[2] = 7.0f;

	    light1pos[0] = 0.0f;
	    light1pos[1] = 3.0f;
	    light1pos[2] = -3.0f;

	}

    }

	/**
	 * 物体を描画するときに呼び出されるメソッド
	 */
	public void draw(GLAutoDrawable drawable) {
		 GL2 gl = drawable.getGL().getGL2();
		 GLUgl2 glu = new GLUgl2();

		 // ウィンドウをクリアする
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		 // 幾何変換のための行列を初期化する
	     gl.glLoadIdentity();

	     if(pattern == 1){
		 if(mode == 0){
		     glu.gluLookAt(8.5, 1.0, 2.1, 0.0, 0.0, 2.1, 0.0, 1.0, 0.0);
		 }else if(mode == 1){
		     glu.gluLookAt(1.0, 8.0, 2.1, 0.0, 0.0, 2.1, 0.0, 1.0, 0.0);
		 }else if(mode == 2){
		     glu.gluLookAt(7.0, 1.0, 7.0, 0.0, 0.0, 2.5, 0.0, 1.0, 0.0);
		 }else if(mode == 3){
		     glu.gluLookAt(0.115, 0.3, -1.0, 0.115, 0.3, 0.0, 0.0, 1.0, 0.0);
	         }
	     }else if(pattern == 2){
		 if(mode == 0){
		     glu.gluLookAt(8.5, 1.0, 2.1, 0.0, 0.0, 2.1, 0.0, 1.0, 0.0);
		 }else if(mode == 1){
		     glu.gluLookAt(1.0, 8.0, 2.1, 0.0, 0.0, 2.1, 0.0, 1.0, 0.0);
		 }else if(mode == 2){
		     glu.gluLookAt(7.0, 1.0, 7.0, 0.0, 0.0, 2.5, 0.0, 1.0, 0.0);
		 }else if(mode == 3){
		     glu.gluLookAt(0.0, 0.3, -1.0, 0.0, 0.3, 0.0, 0.0, 1.0, 0.0);
	         }
	     }else{
		 
		 if(mode == 0){
		     glu.gluLookAt(15.0, 1.0, 3.5, 0.0, 0.0, 3.5, 0.0, 1.0, 0.0);
		 }else if(mode == 1){
		     glu.gluLookAt(1.0, 14.5, 3.6, 0.8, 0.0, 3.6, 0.0, 1.0, 0.0);
		 }else if(mode == 2){
		     glu.gluLookAt(11.5, 1.0, 11.5, 0.0, 0.0, 3.5, 0.0, 1.0, 0.0);
		 }else if(mode == 3){
		     glu.gluLookAt(0.7, 1.5, -7.0, 0.7, 0.3, 0.0, 0.0, 1.0, 0.0);
		 }
	     } 

		 // 光源の位置を設定する
		 gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0pos, 0);
		 gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light1pos, 0);
		 
		 // シーンを描画する
		 MyScene.draw(drawable);
		 
	}
	
	
	public GLAutoDrawable getGLAutoDrawable() {
		return glAD;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
