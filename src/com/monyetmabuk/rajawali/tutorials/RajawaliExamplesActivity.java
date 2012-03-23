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

import com.monyetmabuk.tutorials.rajawali.tutorials.ui.ExamplesAdapter;

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
			new ExampleItem("07. Simple Particle System",
					RajawaliSimpleParticlesActivity.class,
					"RajawaliSimpleParticlesActivity"),
			new ExampleItem("08. 2D Renderer", Rajawali2DActivity.class,
					"Rajawali2DActivity"),
			new ExampleItem("09. Using The Accelerometer",
					RajawaliAccelerometerActivity.class, "RajawaliAccelerometerActivity"),
			new ExampleItem("10. More Particles",
					RajawaliMoreParticlesActivity.class, "RajawaliMoreParticlesActivity"),
			new ExampleItem("11. Object Picking",
					RajawaliObjectPickingActivity.class, "RajawaliObjectPickingActivity")

	};

	@Override
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

	@Override
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