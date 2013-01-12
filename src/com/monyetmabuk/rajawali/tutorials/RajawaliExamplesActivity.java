package com.monyetmabuk.rajawali.tutorials;

import ie.cathalcoffey.android.rajawali.tutorials.FragmentsExampleActivity;

import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.ui.ExamplesAdapter;

public class RajawaliExamplesActivity extends ListActivity {
	private ExampleItem[] mItems = { 
			new ExampleItem("01. Basic Example", RajawaliBasicExampleActivity.class),
			new ExampleItem("02. Load .obj model", RajawaliLoadModelActivity.class), 
			new ExampleItem("03. Materials", RajawaliMaterialsActivity.class),
			new ExampleItem("04. UI Elements", RajawaliUIElementsActivity.class),
			new ExampleItem("05. UI Fragments", FragmentsExampleActivity.class),
			new ExampleItem("06. Skybox", RajawaliSkyboxActivity.class),
			new ExampleItem("07. Custom Material/Shader", RajawaliCustomShaderActivity.class), 
			new ExampleItem("08. 2D Renderer", Rajawali2DActivity.class),
			new ExampleItem("09. Using The Accelerometer", RajawaliAccelerometerActivity.class),
			new ExampleItem("10. Particles", RajawaliMoreParticlesActivity.class), 
			new ExampleItem("11. Object Picking", RajawaliObjectPickingActivity.class),
			new ExampleItem("12. Animation", RajawaliAnimationActivity.class), 
			new ExampleItem("13. Bump Mapping", RajawaliBumpmapActivity.class),
			new ExampleItem("14. Bezier Path Animation", RajawaliBezierActivity.class), 
			new ExampleItem("15. Toon Shading", RajawaliToonShadingActivity.class),
			new ExampleItem("16. MD2 Animation", RajawaliMD2Activity.class), 
			new ExampleItem("17. Post Processing", RajawaliPostProcessingActivity.class),
			new ExampleItem("18. Collision Detection", RajawaliCollisionDetectionActivity.class),
			new ExampleItem("19. Multiple Lights", RajawaliMultipleLightsActivity.class), 
			new ExampleItem("20. Lines", RajawaliLinesActivity.class),
			new ExampleItem("21. Catmull-Rom Splines", RajawaliCatmullRomActivity.class),
			new ExampleItem("22. 2000 Textured Planes", Rajawali2000PlanesActivity.class),
			new ExampleItem("23. Transparent GLSurfaceView", RajawaliTransparentSurfaceActivity.class),
			new ExampleItem("24. Animated Sprites", RajawaliAnimatedSpritesActivity.class), 
			new ExampleItem("25. Fog", RajawaliFogActivity.class),
			new ExampleItem("26. FBX Scene Importer", RajawaliFBXActivity.class),
			new ExampleItem("27. Custom Vertex Shader", RajawaliVertexShaderActivity.class),
			new ExampleItem("28. Touch Ripples Effect", RajawaliRipplesActivity.class), 
			new ExampleItem("29. Sphere Mapping", RajawaliSphereMapActivity.class),
			new ExampleItem("30. Chase Camera", RajawaliChaseCamActivity.class),
			new ExampleItem("31. Using Geometry Data", RajawaliUsingGeometryDataActivity.class),
			new ExampleItem("32. 360� Images", Rajawali360ImagesActivity.class) };

	public void onCreate(Bundle savedInstanceState) {
		String[] strings = new String[mItems.length];
		for (int i = 0; i < mItems.length; i++) {
			strings[i] = mItems[i].title;
		}
		List<String> list = Arrays.asList(strings);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setListAdapter(new ExamplesAdapter(this, list));

		TextView linkView = (TextView) findViewById(R.id.textView1);
		linkView.setPadding(10, 0, 10, 20);
		linkView.setTextSize(20);
		linkView.setText(getString(R.string.github_url));
		linkView.setTextColor(0xaaffffff);
		linkView.setLinkTextColor(0xaaffffff);

		Linkify.addLinks(linkView, Linkify.WEB_URLS);

		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
		linkView.setTypeface(font);
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		startActivity(new Intent(this, mItems[position].exampleClass));
	}

	class ExampleItem {
		public String title;
		public Class<?> exampleClass;

		public ExampleItem(String title, Class<?> exampleClass) {
			this.title = title;
			this.exampleClass = exampleClass;
		}
	}
}