/**
 * ドミノを定義するクラス
 */

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import static java.lang.Math.*;


public class MyDomino {

	// ドミノの色
	float color[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	float silver[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	
	// ドミノの頂点の座標値
	double vertex[][] = {
  	          {  0.0, 0.0,  0.0 }, // 0
	          { 0.23, 0.0,  0.0 }, // 1
	          {  0.0, 0.0, 0.08 }, // 2
	          { 0.23, 0.0, 0.08 }, // 3
	          {  0.0, 0.46,  0.0 }, // 4
	          { 0.23, 0.46,  0.0 }, // 5
	          {  0.0, 0.46, 0.08 }, // 6
	          { 0.23, 0.46, 0.08 }  // 7
	};

	
	// ドミノを表現する直方体の各面を構成する頂点の番号
	int face[][] = {
	          { 0, 1, 3, 2 },
		  { 0, 1, 5, 4 },
		  { 0, 2, 6, 4 },
		  { 7, 5, 4, 6 }, 
		  { 7, 5, 1, 3 },
		  { 7, 6, 2, 3 }
	};

	// ドミノを表現する直方体の各面の法線ベクトル
	double normal[][] = {
	          { 0.0,-1.0, 0.0 },
		  {-1.0, 0.0, 0.0 },
		  { 0.0, 0.0,-1.0 },
		  { 0.0, 1.0, 0.0 },
		  { 0.0, 0.0, 1.0 },
		  { 1.0, 0.0, 0.0 }
	};

	// ドミノの番号
        int index = 0;

        // ドミノのサイズ
        double h = 0.46;
        double w = 0.23;
        double d = 0.08;

	// ドミノの位置
	double x = 0.0;
        double y = 0.0;
        double z = 0.0;

        // ドミノの角度
        double r = 0.0;

        // 次のドミノの角度
        double nextR = 0.0;

        // 最初のドミノの角度
        static double firstR = 0.0;

        boolean flag = false;
        boolean collision = false;
        boolean last = false;

        // 次のドミノの座標
        double[] next = new double[3];
    
		
	/**
	 * ドミノの色を設定する
	 */
	public void setColor(double r, double g, double b) {
		color[0] = (float)r;
		color[1] = (float)g;
		color[2] = (float)b;
		color[3] = 1.0f;
	}
	
	/**
	 * ドミノの初期化
	 */
    public void init(int i, double info[][], boolean l) {
	        index = i;
	        x = info[i][0];
	        y = info[i][1];
		z = info[i][2];
		last = l;
		if(last == false){
		    next[0] = info[i + 1][0];
		    next[1] = info[i + 1][1];
		    next[2] = info[i + 1][2];
		}
	}

        /**
         * ドミノの回転角を計算する
         */
         public void calculateMovement(){
	     
	     double pi = 3.1415;
	     
	     if(flag == true && collision == false){

		 if(r <= 1.0 && index == 0){
		     r += 1.0;
		     firstR += 1.0;
		 }else{
		     r += 8 * 9.8 * sin(firstR * 0.1 * pi / 180.0);
		     
		     if(index == 0){
			 firstR += 8 * 9.8 * sin(firstR * 0.1 * pi / 180.0);
		     }
		 }
	     }else if(flag == true && collision == true){
		 
		 // 次のドミノにめりこまないようにする		 
		 if(nextR <= r){
		     double rad1 = nextR * 0.1 * pi / 180.0;
		     double tangent1 = tan(rad1);
		     double bunbo1 = h * sqrt(1 + tangent1 * tangent1);
		     double gousei1 = asin((d / cos(rad1) - (next[2] - z)) / bunbo1);
		     double alpha1 = asin(h * tangent1 / bunbo1);
		     
		     r = toDegrees(abs(gousei1 - alpha1)) * 10;
		     
		 }
	     }
	     if(r >= 900.0){
		 r = 900.0;
	     }
	     MyScene.calculateMovement(index, r);
	 }

         public void setFlag(){
	     flag = true;
	 }
	
	/**
	 * ドミノの傾きをリセットする
	 */
	public void resetMovement() {
	    r = 0.0;
	    flag = false;
	    collision = false;
	    firstR = 0.0;
	}

        static public void resetFirstR(){
	    firstR = 0.0;
	}
	
	/**
	 * ドミノを描画する
	 */
	public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		double pi = 3.1415;
		
		// ドミノの変位を求める
		gl.glTranslated(x, y, z + d);
		gl.glRotated(r * 0.1, 1.0, 0.0, 0.0);
		gl.glTranslated(0.0, 0.0, -d);
				
		// ドミノの拡散反射係数・鏡面反射係数を設定する
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, color, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

		// ドミノの各面について
		for (int j = 0; j < 6; ++j) {
			gl.glBegin(GL2.GL_POLYGON);
			gl.glNormal3dv(normal[j], 0);
			for (int i = 0; i < 4; ++i) {
				gl.glVertex3dv(vertex[face[j][i]], 0);
			}
			gl.glEnd();
		}

		double theta3 = r * 0.1 * pi / 180.0;
		double ver1[] = {  w / 2, h * cos(theta3), h * sin(theta3)};
		double ver2[] = { -w / 2, h * cos(theta3), h * sin(theta3)};
	
		if(last == false){
		    collision = Collision.calcCol(ver1, ver2, z + d, next[2]);
		}

		if(collision == true && last == false){
		    nextR = MyScene.setFlag(index + 1); // 前のドミノが倒れる
		}

		calculateMovement();
	}
	
	

}
