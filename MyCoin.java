/**
 * 1円玉を定義するクラス
 */

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import static java.lang.Math.*;


public class MyCoin {

	// 1円玉の色
	float color1[] = { 0.6f, 0.6f, 0.6f, 1.0f };
	float color2[] = { 0.4f, 0.4f, 0.4f, 1.0f };
	float silver[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	
	// 1円玉の中身の頂点の座標値
	double vertex[][] = {
  	          {  0.02, 0.11, 0.031 }, // 0
	          { -0.02, 0.11, 0.031 }, // 1
	          {  0.02, 0.35, 0.031 }, // 2
	          { -0.02, 0.35, 0.031 }, // 3
	          { -0.02, 0.33, 0.031 }, // 4
	          { -0.02,  0.3, 0.031 }, // 5
	          { -0.05,  0.3, 0.031 }, // 6
	          { -0.05, 0.33, 0.031 }  // 7
	};

	int face[][] = {
	          { 0, 1, 3, 2 },
		  { 4, 5, 6, 7 },
	};


	// ドミノを表現する直方体の各面の法線ベクトル
	double normal[][] = {
	          { 0.0, 0.0, -1.0 },
		  { 0.0, 0.0, -1.0 },
	};
    
        // 1円玉の厚さと半径
        static double height = 0.03; 
        static double coinR = 0.23; 

	// 1円玉の番号
        int index = 0;

	// 1円玉の位置
	double x = 0.0;
        double y = 0.0;
        double z = 0.0;

        // 1円玉の角度
        double r = 0.0;

        // 次の1円玉の角度
        double nextR = 0.0;

        // 最初の1円玉の角度
        static double firstR = 0.0;

        boolean flag = false;
        boolean collision = false;
        boolean last = false;

        // 次の1円玉の座標
        double[] next = new double[3];
    
		
	/**
	 * 1円玉の色を設定する
	 */
	public void setColor(double r, double g, double b) {
		color1[0] = (float)r;
		color1[1] = (float)g;
		color1[2] = (float)b;
		color1[3] = 1.0f;
	}
	
	/**
	 * 1円玉の初期化
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
         * 1円玉の回転角を計算する
         */
         public void calculateMovement(){
	     
	     double pi = 3.1415;
	     
	     if(flag == true && collision == false){
		 double g = 9.8;
		 
		 if(r <= 1.0 && index == 0){
		     r += 1.0;
		     firstR += 1.0;
		 }else{
		     r += 6.0 * g * sin(firstR * 0.1 * pi / 180.0);
		     
		     if(index == 0){
			 firstR += 6.0 * g * sin(firstR * 0.1 * pi / 180.0);
		     }
		 }
	     }else if(flag == true && collision == true){
		 
		 // 次の1円玉にめりこまないようにする		 
		 if(nextR <= r){
		     double diameter = coinR * 2;
		     double rad1 = nextR * 0.1 * pi / 180.0;
		     double tangent1 = tan(rad1);
		     double bunbo1 = diameter * sqrt(1 + tangent1 * tangent1);
		     double gousei1 = asin((height / cos(rad1) - (next[2] - z)) / bunbo1);
		     double alpha1 = asin(diameter * tangent1 / bunbo1);
		     
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
	 * 1円玉の傾きをリセットする
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
	

        public void drawCoin(GLAutoDrawable drawable, double r, double height){
	       GL2 gl = drawable.getGL().getGL2();
	       int sides = 30;
	       int i;
	       double pi = 3.1415; 
	       double step = pi*2 / (double)sides;
	       double r2 = r * 2 / 3;

	        //上面
		gl.glNormal3d(1.0, -1.0, -1.0);
		gl.glBegin(GL2.GL_POLYGON);
		for(i = 0; i < sides; i++){
		    double t = step * (double)i;
		    gl.glVertex3d(r * cos(t), r * sin(t) + coinR, 0.0);
		}
		gl.glEnd();

		//下面
		gl.glNormal3d(0.0, 0.0, 1.0);
		gl.glBegin(GL2.GL_POLYGON);
		for(i = 0; i < sides; i++){
		    double t = step * (double)i;
		    gl.glVertex3d(r * cos(t), r * sin(t) + coinR, height);
		}
		gl.glEnd();
		
		//側面
		gl.glBegin(GL2.GL_QUAD_STRIP);
		for(i = 0; i <= sides; i++){
		    double t = step * (double)i;
		    double x = r * sin(t) + coinR;
		    double z = cos(t);

		    gl.glNormal3d(z, x, 0.0);
		    gl.glVertex3d(r * z, x, height);
		    gl.glVertex3d(r * z, x, 0.0);
		}
		gl.glEnd();

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, color2, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

		gl.glNormal3d(0.0, 0.0, 1.0);
		gl.glBegin(GL2.GL_LINE_LOOP);
		for(i = 0;i < sides; i++){
		    double t = step * (double)i;
		    gl.glVertex3d(r2 * cos(t), r2 * sin(t) + coinR, height);
		}
		gl.glEnd();


		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, color2, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);
		// 1円玉の中身について
		for (int j = 0; j < 2; ++j) {
			gl.glBegin(GL2.GL_POLYGON);
			gl.glNormal3dv(normal[j], 0);
			for (i = 0; i < 4; ++i) {
				gl.glVertex3dv(vertex[face[j][i]], 0);
			}
			gl.glEnd();
		}

	
	}

	
	/**
	 * 1円玉を描画する
	 */
	public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		double pi = 3.1415;
		
		// 1円玉の変位を求める
		gl.glTranslated(x, y, z + height);
		gl.glRotated(r * 0.1, 1.0, 0.0, 0.0);
		gl.glTranslated(0.0, 0.0, -height);
				
		// 1円玉の拡散反射係数・鏡面反射係数を設定する
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, color1, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

		drawCoin(drawable, coinR, height);

		double theta3 = r * 0.1 * pi / 180.0;
		double diameter = coinR * 2;
		double sine = diameter * sin(theta3);
		double cosine = diameter * cos(theta3);
		
		double ver[] = { 0.0, cosine, sine};
	
		if(last == false){
		    collision = Collision.calcCol(ver, ver, z + height, next[2]);
		    
		}

		if(collision == true && last == false){
		    nextR = MyScene.setFlag(index + 1); // 前の1円玉が倒れる
		}

		calculateMovement();
	}
	
	

}
