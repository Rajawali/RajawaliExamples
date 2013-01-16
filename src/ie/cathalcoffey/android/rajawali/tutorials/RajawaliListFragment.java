package ie.cathalcoffey.android.rajawali.tutorials;


import rajawali.animation.Animation3D;
import rajawali.animation.Animation3DListener;
import rajawali.animation.RotateAnimation3D;
import rajawali.math.Number3D.Axis;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RajawaliListFragment extends ListFragment 
{
	  RajawaliLoadModel fragment;
	  RajawaliLoadModelRenderer renderer; 
	  RotateAnimation3D mCameraAnim;
	  
	  float startAngle;
	  float currentAngle;
	  float targetAngle;
      float moveThru;
	  
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) 
	  {
	      super.onActivityCreated(savedInstanceState);
	      
	      String[] values = new String[] 
	      {
	          "Front", 
	          "Left", 
	          "Back", 
	          "Right"
	      };
	      
	      ArrayAdapter<String> adapter = new ArrayAdapter<String>
	      (
	          getActivity(),
	          android.R.layout.simple_list_item_1, 
	          values
	      );
	      
	      setListAdapter(adapter);
	  }
	   
	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id)
	  {
	      if(fragment == null)
	      {
	    	   fragment = (RajawaliLoadModel) getFragmentManager().findFragmentByTag("rajawali");
	      
	    	   if(fragment == null)
	    		   return;
	    	   else
	    	   {
	    		   renderer = fragment.mRenderer;
			       startAngle = currentAngle = targetAngle = 0;
	    	   }
	        }
	      
		    /* 
		     * The angle we want the car to rotate to.
		     * Front = 90 * 0 =   0
		     * Left  = 90 * 1 =  90
		     * Back  = 90 * 2 = 180
		     * Right = 90 * 3 = 270
		     */
		    float gotoAngle = 90 * position;
		    
            /* 
             * If we have just asked the car to goto an angle that its already
             * rotating towards, we should just let it to its thing.
             * This happens when you choose the same option twice in a row. 
             */
	    	if(targetAngle == gotoAngle) 
	    		return;
	  				
		    // If their is a current camera animation executing, cancel it.
		    if(mCameraAnim != null && !mCameraAnim.isHasEnded())
			    mCameraAnim.cancel();
		    
	    	// Ok, we have decided to go to a new angle.
	    	targetAngle = gotoAngle;
	    	startAngle = currentAngle;
	    	
	    	// The angle we need to move thru.
	    	moveThru = targetAngle-startAngle;
	    	
	    	// If the moveThru angle is 270 then -90 is a better choice.
	    	if(moveThru == 270)
	    		moveThru = -90;
	    	
	    	// If the moveThru angle is -270 then 90 is a better choice.
	    	else if(moveThru == -270)
	    		moveThru = 90;
	        
	    	// Start an animation to moveThru the specified angle on the Z axis.
		    mCameraAnim = new RotateAnimation3D(Axis.Z, moveThru);
		    
		    /* 
		     * We setup an AnimationListener and listen to onAnimationUpdate
		     * We do this so that we can cancel one animation early and start
		     * another gracefully. Try pressing different options really fast 
		     * without giving the animations time to complete.
		     */
		    mCameraAnim.setAnimationListener
		    ( 
	    		new Animation3DListener()
	    		{
					@Override
					public void onAnimationEnd(Animation3D animation) 
					{
						// TODO Auto-generated method stub	
					}
		
					@Override
					public void onAnimationRepeat(Animation3D animation) 
					{
						// TODO Auto-generated method stub	
					}
		
					@Override
					public void onAnimationStart(Animation3D animation) 
					{
						// TODO Auto-generated method stub	
					}
		
					@Override
					public void onAnimationUpdate(Animation3D animation, float interpolatedTime) 
					{
						/* 
						 * Keep track of the currentAngle.
						 * We will continue from this angle if the animation is canceled before completion. 
						 */
						currentAngle = (startAngle + (interpolatedTime * moveThru)) % 360;
					}
	    		}
		    );
		    
		    // Start the animation.
  		  	mCameraAnim.setDuration(1000);
  		  	mCameraAnim.setTransformable3D(renderer.mObjectGroup);	
  		  	mCameraAnim.start();
	  }
} 