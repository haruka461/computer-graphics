/**
 * キーボード操作に関する反応を扱うクラス
 */

import java.awt.event.*;
import com.jogamp.opengl.util.Animator;

public class CgKeyListener implements KeyListener {
	CgCanvas canvas;
        Animator animator;

    public CgKeyListener(CgCanvas c, Animator a) {
		canvas = c;
		animator = a;
	}
	
	
   	/**
	 * キーを押したときに呼び出されるメソッド
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch (key) {
		
		// "Q"を押したとき
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		
		// "R"を押したとき
		case KeyEvent.VK_R:
			MyScene.resetMovement();
			canvas.display();
			break;

		// "S"を押したとき
		case KeyEvent.VK_S:
		    animator.start();
		    double r = MyScene.setFlag(0);
			break;

		// "P"を押したとき
		case KeyEvent.VK_P:
			animator.stop();
			break;
			
		// "↓"を押したとき
		case KeyEvent.VK_DOWN:
		    CgDrawer.changeView(0);
		        break;

		// "↑"を押したとき
		case KeyEvent.VK_UP:
		    CgDrawer.changeView(1);
		        break;

		// "←"を押したとき
		case KeyEvent.VK_LEFT:
		    CgDrawer.changeView(2);
		        break;

		// "→"を押したとき
		case KeyEvent.VK_RIGHT:
		    CgDrawer.changeView(3);
		        break;

		// "1"を押したとき
		case KeyEvent.VK_1:
		    MyScene.changePattern(1);
		    MyDomino.resetFirstR();
		    canvas.display();
		        break;

		// "2"を押したとき
		case KeyEvent.VK_2:
		    MyScene.changePattern(2);
		    MyCoin.resetFirstR();
		    canvas.display();
		        break;

		// "3"を押したとき
		case KeyEvent.VK_3:
		    MyScene.changePattern(3);
		    MyBike.resetFirstR();
		    canvas.display();
		        break;

		}
		
	}

	/**
	 * キーから手を離したときに呼び出されるメソッド
	 */
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * キーをタイプしたときに呼び出されるメソッド
	 */
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		
		// "Q"を押したとき
		case KeyEvent.VK_Q:
			System.exit(0);
		}
	}
}
