/**
 * 描画するシーンを定義するクラス
 */

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.GLAutoDrawable;

import static java.lang.Math.*;
import java.nio.FloatBuffer;

public class MyScene {

        static int mode = 1;
        static int size1 = 20;
        static int size2 = 25;
        static int size3 = 10;
        static MyDomino[] pattern1 = new MyDomino[size1];
        static MyCoin[] pattern2 = new MyCoin[size2];
        static MyBike[] pattern3 = new MyBike[size3];
 
    static double[][] info1 = new double[size1][4];
    static double[][] info2 = new double[size2][4];
    static double[][] info3 = new double[size3][4];

    static int[] click = new int[2];
    static boolean mouse = false;

    static void changePattern(int pattern){
	mode = pattern;
	initArray();
	init();
	CgDrawer.changeLight(pattern);
    }

    static void initArray(){
	double x = 0.0;
	double z = 0.0;
	double a = 0.3;
	double theta1 = 0.0;
	double theta2;
	double pi = 3.1415;
	double l = 0.5;
	
	if(mode == 1){
	    
	    for(int i = 0; i < size1; i++){
		info1[i][2] = z;
		z += 0.2;
	    }

	}else if(mode == 2){

	    for(int i = 0; i < size2; i++){
		info2[i][2] = z;
		z += 0.15;
	    }

	}else{
	    for(int i = 0; i < size3; i++){
		info3[i][0] = x;
		x -= 0.7;
	    }
	}

    }
	/**
	 * シーンの初期化
	 */
	public static void init() {

	    double r = 1.0;
	    double g = 0.0;
	    double b = 0.0;
	    
	    initArray();

	    if(mode == 1){
		for(int i = 0; i < size1; i++){
		    
		    pattern1[i] = new MyDomino();
		    pattern1[i].setColor(r, g, b);
		    if(i == size1 - 1){
			pattern1[i].init(i, info1, true);
		    }else{
			pattern1[i].init(i, info1, false);
		    }
		    if(r == 1.0 && g != 1.0){
			g += 0.2;
		    }else if(g == 1.0 && r > 0.0){
			r -= 0.2;
		    }else if(r <= 0.0 && b < 1.0){
			b += 0.2;
		    }else if(b == 1.0){
			g -= 0.2;
		    }
		    
		}
	    }else if(mode == 2){

		for(int i = 0; i < size2; i++){
		    pattern2[i] = new MyCoin();
		    pattern2[i].setColor(0.6, 0.6, 0.6);
		    if(i == size2 - 1){
			pattern2[i].init(i, info2, true);
		    }else{
			pattern2[i].init(i, info2, false);
		    }		
		}
	    }else{
		r = 0.0;
		g = 0.0;
		b = 1.0;

		for(int i = 0; i < size3; i++){
		    pattern3[i] = new MyBike();
		    pattern3[i].setColor(r, g, b);
		    if(i == size3 - 1){
			pattern3[i].init(i, info3, true);
		    }else{
			pattern3[i].init(i, info3, false);
		    }
		    if(b == 1.0 && r != 1.0){
			r += 0.5;
		    }else if(r == 1.0 && b > 0.0){
			b -= 0.5;
		    }else if(b <= 0.0 && g < 1.0){
			g += 0.5;
		    }else if(g == 1.0){
			r -= 0.5;
		    }
		}
	    }
	    
	}
	
	/**
	 * シーンを描画する
	 */
	public static void draw(GLAutoDrawable drawable) {
		if(drawable == null) return;
		
		GL2 gl = drawable.getGL().getGL2();

		 // 物体が裏面を向いていたとしても光を当てる
		 gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE); 
		
	    // ドミノを描画する
		 
		 if(mode == 1){
		     for(int i = 0; i < size1; i++){
			 gl.glPushMatrix();
			 if(pattern1[i] != null)
			     pattern1[i].draw(drawable);
			 gl.glPopMatrix();
		     }
		 }else if(mode == 2){
		     for(int i = 0; i < size2; i++){
			 gl.glPushMatrix();
			 if(pattern2[i] != null)
			     pattern2[i].draw(drawable);
			 gl.glPopMatrix();
		     }
		 }else{
		 
		     for(int i = 0; i < size3; i++){
			 gl.glPushMatrix();
			 if(pattern3[i] != null)
			     pattern3[i].draw(drawable);
			 gl.glPopMatrix();
		     }
		 }

		 if(mouse == true){
		     calcPos(drawable, click[0], click[1]);
		     mouse = false;
		 }
		  
	}

        public static void calculateMovement(int index, double r) {

	    if(mode == 1){
		info1[index][3] = r;
	    }else if(mode == 2){
		info2[index][3] = r;
	    }else{
		info3[index][3] = r;
	    }	
	}
	
	/**
	 * 動きをリセットする
	 */
	public static void resetMovement() {
	    int i;
	    
		// ドミノを初期状態に戻す
	    if(mode == 1){
		for(i = 0; i < size1; i++){
		    pattern1[i].resetMovement();
		    info1[i][3] = 0.0;
		}
	    }else if(mode == 2){
		for(i = 0; i < size2; i++){
		    pattern2[i].resetMovement();
		    info2[i][3] = 0.0;
		}
	    }else{
		for(i = 0; i < size3; i++){
		    pattern3[i].resetMovement();
		    info3[i][3] = 0.0;
		}
	    }
	}

    public static void clickPos(int x, int y){
	click[0] = x;
	click[1] = y;
	mouse = true;
    }

    public static double calcDepth(GLAutoDrawable drawable, int x, int y, int[] viewport){
	GL2 gl = drawable.getGL().getGL2();
	FloatBuffer z = FloatBuffer.allocate(1);
	
	gl.glReadPixels(x, viewport[3] - y, 1, 1, GL2.GL_DEPTH_COMPONENT, GL2.GL_FLOAT, z);

	return (double)z.get(0);
	}
    
    public static void calcPos(GLAutoDrawable drawable, int x, int y) {

   	    GL2 gl = drawable.getGL().getGL2();
	    GLU glu = new GLU();

		// クリックの位置取得
	    int[] viewport = new int[4];
	    double[] mvMatrix = new double[16];
	    double[] pjMatrix = new double[16];
	    double[] objPos = new double[3];

	    gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);

	    double depth = calcDepth(drawable, x, y, viewport);

	    gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, mvMatrix, 0);
	    gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, pjMatrix, 0);

	    glu.gluUnProject((double)x, (double)(viewport[3] - y), depth, mvMatrix, 0, pjMatrix, 0, viewport, 0, objPos, 0);
	    /*
	    int index = (int)(objPos[2] / 0.2);
	    if(index < size1){
		//System.out.println("index: " + index);
		pattern1[index].evaluatePoint(objPos);
	    }
	    */
	    if(mode == 1){
		if(objPos[2] >= 0.0 && objPos[2] <= 0.08){
		    setFlag(0);
		}
	    }else if(mode == 2){
		if(objPos[2] >= 0.0 && objPos[2] <= 0.03){
		    setFlag(0);
		}
	    }else{
		if(objPos[2] >= 0.0 && objPos[2] <=0.4){
		    setFlag(0);
		}
	    }
	   
	}
    
	public static double setFlag(int index) {

		// ドミノが押された!
	    if(mode == 1){
		pattern1[index].setFlag();
		return info1[index][3];
	    }else if(mode == 2){
		pattern2[index].setFlag();
		return info2[index][3];
	    }else{
		pattern3[index].setFlag();
		return info3[index][3];
	    }

	}

    	
}
