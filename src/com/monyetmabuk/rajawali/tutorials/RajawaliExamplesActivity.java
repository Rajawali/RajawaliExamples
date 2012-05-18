package com.monyetmabuk.rajawali.tutorials;

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
			new ExampleItem("01. Basic Example",
					RajawaliBasicExampleActivity.class,
					"RajawaliBasicExampleActivity"),
			new ExampleItem("02. Load .obj model",
					RajawaliLoadModelActivity.class,
					"RajawaliLoadModelActivity"),
			new ExampleItem("03. Materials", RajawaliMaterialsActivity.class,
					"RajawaliMaterialsActivity"),
			new ExampleItem("04. UI Elements",
					RajawaliUIElementsActivity.class,
					"RajawaliUIElementsActivity"),
			new ExampleItem("05. Skybox", RajawaliSkyboxActivity.class,
					"RajawaliSkyboxActivity"),
			new ExampleItem("06. Custom Material/Shader",
					RajawaliCustomShaderActivity.class,
					"RajawaliCustomShaderActivity"),
			new ExampleItem("07. 2D Renderer", Rajawali2DActivity.class,
					"Rajawali2DActivity"),
			new ExampleItem("08. Using The Accelerometer",
					RajawaliAccelerometerActivity.class, "RajawaliAccelerometerActivity"),
			new ExampleItem("09. Particles",
					RajawaliMoreParticlesActivity.class, "RajawaliMoreParticlesActivity"),
			new ExampleItem("10. Object Picking",
					RajawaliObjectPickingActivity.class, "RajawaliObjectPickingActivity"),
			new ExampleItem("11. Animation",
					RajawaliAnimationActivity.class, "RajawaliAnimationActivity"),
			new ExampleItem("12. Bump Mapping",
					RajawaliBumpmapActivity.class, "RajawaliBumpmapActivity"),
			new ExampleItem("13. Bezier Path Animation",
					RajawaliBezierActivity.class, "RajawaliBezierActivity"),
			new ExampleItem("14. Toon Shading",
					RajawaliToonShadingActivity.class, "RajawaliToonShadingActivity"),
			new ExampleItem("15. MD2 Animation",
					RajawaliMD2Activity.class, "RajawaliMD2Activity"),
			new ExampleItem("16. Post Processing",
					RajawaliPostProcessingActivity.class, "RajawaliPostProcessingActivity"),
			new ExampleItem("17. Collision Detection",
					RajawaliCollisionDetectionActivity.class, "RajawaliCollisionDetectionActivity"),
			new ExampleItem("18. Multiple Lights",
					RajawaliMultipleLightsActivity.class, "RajawaliMultipleLightsActivity"),
			new ExampleItem("19. Lines",
					RajawaliLinesActivity.class, "RajawaliLinesActivity"),
			new ExampleItem("20. Catmull-Rom Splines",
					RajawaliCatmullRomActivity.class, "RajawaliCatmullRomActivity"),
			new ExampleItem("21. 2000 Textured Planes",
					Rajawali2000PlanesActivity.class, "Rajawali2000PlanesActivity"),
			new ExampleItem("22. Transparent GLSurfaceView",
					RajawaliTransparentSurfaceActivity.class, "RajawaliTransparentSurfaceActivity"),
			new ExampleItem("23. Animated Sprites",
					RajawaliAnimatedSpritesActivity.class, "RajawaliAnimatedSpritesActivity"),
			new ExampleItem("24. Fog",
					RajawaliFogActivity.class, "RajawaliFogActivity"),
			new ExampleItem("25. FBX Scene Importer",
					RajawaliFBXActivity.class, "RajawaliFBXActivity"),
			new ExampleItem("26. Custom Vertex Shader",
					RajawaliVertexShaderActivity.class, "RajawaliVertexShaderActivity"),
			new ExampleItem("27. Touch Ripples Effect",
					RajawaliRipplesActivity.class, "RajawaliRipplesActivity")
	};

	public void onCreate(Bundle savedInstanceState) {
		String[] strings = new String[mItems.length];
		for (int i = 0; i < mItems.length; i++) {
			strings[i] = mItems[i].title;
		}
		List<String> list = Arrays.asList(strings);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setListAdapter(new ExamplesAdapter(this, list));
		
		TextView linkView = (TextView)findViewById(R.id.textView1);
		linkView.setPadding(10, 0, 10, 20);
		linkView.setTextSize(20);
		linkView.setText(getString(R.string.github_url));
		linkView.setTextColor(0xaaffffff);
		linkView.setLinkTextColor(0xaaffffff);
		
		Linkify.addLinks(linkView, Linkify.WEB_URLS);
		
		Typeface font=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
		linkView.setTypeface(font);
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		startActivity(new Intent(this, mItems[position].exampleClass));
	}

	class ExampleItem {
		public String title;
		public Class<?> exampleClass;
		public String fileName;

		public ExampleItem(String title, Class<?> exampleClass, String fileName) {
			this.title = title;
			this.exampleClass = exampleClass;
			this.fileName = fileName;
		}
	}
}