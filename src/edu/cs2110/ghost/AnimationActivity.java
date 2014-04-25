package edu.cs2110.ghost;


import edu.cs2110.actors.Ghosts;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;

public class AnimationActivity extends Activity {
	Ghosts animation = new Ghosts();
 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new AndroidTutorialPanel(this));
	}
 
	class AndroidTutorialPanel extends Drawable {
 
		public AndroidTutorialPanel(Context context) {
			super(context);
		}
 
		public void onDraw(Canvas canvas) {
			super.onDraw(canvas);
		//	AnimationActivity.this.animation.draw(canvas);
		}
 
		public void onInitalize() {
			AnimationActivity.this.animation.Initialize(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),32, 32, 14, 7); //example code for explosion: need to find ghost sprites
		}
 
		@Override
		public void onUpdate(long gameTime) {
			AnimationActivity.this.animation.update(gameTime);
		}
	}
 
}