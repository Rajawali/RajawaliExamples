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

import com.monyetmabuk.rajawali.tutorials.buffers.UpdateVertexBufferActivity;
import com.monyetmabuk.rajawali.tutorials.buffers.UsingGeometryDataActivity;
import com.monyetmabuk.rajawali.tutorials.cameras.CameraFollowPathActivity;
import com.monyetmabuk.rajawali.tutorials.cameras.ChaseCamActivity;
import com.monyetmabuk.rajawali.tutorials.cameras.OrthographiCamActivity;
import com.monyetmabuk.rajawali.tutorials.cameras.TwoDActivity;
import com.monyetmabuk.rajawali.tutorials.curves.BezierActivity;
import com.monyetmabuk.rajawali.tutorials.curves.CatmullRomActivity;
import com.monyetmabuk.rajawali.tutorials.curves.CurvesActivity;
import com.monyetmabuk.rajawali.tutorials.curves.SVGPathActivity;
import com.monyetmabuk.rajawali.tutorials.curves.UniformDistributionActivity;
import com.monyetmabuk.rajawali.tutorials.fragment.FragmentActivity;
import com.monyetmabuk.rajawali.tutorials.interact.ObjectDraggingActivity;
import com.monyetmabuk.rajawali.tutorials.interact.ObjectPickingActivity;
import com.monyetmabuk.rajawali.tutorials.lights.DirectionalLightActivity;
import com.monyetmabuk.rajawali.tutorials.lights.MultipleLightsActivity;
import com.monyetmabuk.rajawali.tutorials.lights.PointLightActivity;
import com.monyetmabuk.rajawali.tutorials.lights.SpotLightActivity;
import com.monyetmabuk.rajawali.tutorials.materials.AnimatedGIFTextureActivity;
import com.monyetmabuk.rajawali.tutorials.materials.AnimatedSpritesActivity;
import com.monyetmabuk.rajawali.tutorials.materials.MaterialsActivity;
import com.monyetmabuk.rajawali.tutorials.materials.NormalMapActivity;
import com.monyetmabuk.rajawali.tutorials.materials.SkyboxActivity;
import com.monyetmabuk.rajawali.tutorials.materials.SpecularAndAlphaActivity;
import com.monyetmabuk.rajawali.tutorials.materials.SphereMapActivity;
import com.monyetmabuk.rajawali.tutorials.materials.TextureCompressionActivity;
import com.monyetmabuk.rajawali.tutorials.materials.ThreeSixtyImagesActivity;
import com.monyetmabuk.rajawali.tutorials.materials.ToonShadingActivity;
import com.monyetmabuk.rajawali.tutorials.materials.VideoTextureActivity;
import com.monyetmabuk.rajawali.tutorials.primitives.ColoredLineSegmentsActivity;
import com.monyetmabuk.rajawali.tutorials.primitives.LinesActivity;
import com.monyetmabuk.rajawali.tutorials.skeletalanim.BlendTransitionActivity;
import com.monyetmabuk.rajawali.tutorials.skeletalanim.MD5Activity;
import com.monyetmabuk.rajawali.tutorials.ui.ExamplesAdapter;

public class RajawaliExamplesActivity extends ListActivity {
	private ExampleItem[] mItems = { 
			new ExampleItem("Basic Example", RajawaliBasicExampleActivity.class),
			new ExampleItem("Load .obj model", RajawaliLoadModelActivity.class), 
			new ExampleItem("Materials", MaterialsActivity.class),
			new ExampleItem("UI Elements", RajawaliUIElementsActivity.class), 
			new ExampleItem("Using Fragments", FragmentActivity.class),
			new ExampleItem("Skybox", SkyboxActivity.class),
			new ExampleItem("Custom Material/Shader", RajawaliCustomShaderActivity.class), 
			new ExampleItem("2D Renderer", TwoDActivity.class),
			new ExampleItem("Using The Accelerometer", RajawaliAccelerometerActivity.class),
			new ExampleItem("Particles", RajawaliMoreParticlesActivity.class), 
			new ExampleItem("Object Picking", ObjectPickingActivity.class),
			new ExampleItem("Animation", RajawaliAnimationActivity.class), 
			new ExampleItem("Bump Mapping", NormalMapActivity.class),
			new ExampleItem("Bezier Path Animation", BezierActivity.class),
			new ExampleItem("Curves", CurvesActivity.class),
			new ExampleItem("SVG Path", SVGPathActivity.class),
			new ExampleItem("Toon Shading", ToonShadingActivity.class),
			new ExampleItem("MD2 Animation", RajawaliMD2Activity.class), 
			new ExampleItem("Post Processing", RajawaliPostProcessingActivity.class),
			new ExampleItem("Collision Detection", RajawaliCollisionDetectionActivity.class),
			new ExampleItem("Point Light", PointLightActivity.class),
			new ExampleItem("Directional Light", DirectionalLightActivity.class),
			new ExampleItem("Spot Light", SpotLightActivity.class),
			new ExampleItem("Multiple Lights", MultipleLightsActivity.class),
			new ExampleItem("Lines", LinesActivity.class),
			new ExampleItem("Colored Line Segments", ColoredLineSegmentsActivity.class),
			new ExampleItem("Catmull-Rom Splines", CatmullRomActivity.class),
			new ExampleItem("Uniform point distribution & constant speed", UniformDistributionActivity.class),
			new ExampleItem("2000 Textured Planes", Rajawali2000PlanesActivity.class),
			new ExampleItem("Transparent GLSurfaceView", RajawaliTransparentSurfaceActivity.class),
			new ExampleItem("Animated Sprites", AnimatedSpritesActivity.class), 
			new ExampleItem("Fog", RajawaliFogActivity.class),
			new ExampleItem("FBX Scene Importer", RajawaliFBXActivity.class),
			new ExampleItem("Custom Vertex Shader", RajawaliVertexShaderActivity.class),
			new ExampleItem("Touch Ripples Effect", RajawaliRipplesActivity.class), 
			new ExampleItem("Sphere Mapping", SphereMapActivity.class),
			new ExampleItem("Chase Camera", ChaseCamActivity.class),
			new ExampleItem("Orthographic Camera", OrthographiCamActivity.class),
			new ExampleItem("Camera Follow Path Animation", CameraFollowPathActivity.class),
			new ExampleItem("Using Geometry Data", UsingGeometryDataActivity.class),
			new ExampleItem("Updating Vertex Buffer Data", UpdateVertexBufferActivity.class),
			new ExampleItem("360° Images", ThreeSixtyImagesActivity.class),
			new ExampleItem("Skeletal Animation (MD5)", MD5Activity.class),
			new ExampleItem("Texture Compression (ETC)", TextureCompressionActivity.class),
			new ExampleItem("Color Animation", RajawaliColorAnimationActivity.class),
			new ExampleItem("Scenes", RajawaliSceneActivity.class),
			new ExampleItem("Specular and Alpha Maps", SpecularAndAlphaActivity.class),
			new ExampleItem("Video Texture", VideoTextureActivity.class),
			//new ExampleItem("Camera Video Texture", CameraVideoTextureActivity.class),
			new ExampleItem("Skeletal Animation Transition Blending", BlendTransitionActivity.class),
			new ExampleItem("Touching and Dragging Objects", ObjectDraggingActivity.class),
			new ExampleItem("Animated GIF Texture", AnimatedGIFTextureActivity.class),
			new ExampleItem("Terrain", TerrainActivity.class)};

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
		linkView.setTextColor(0xff000000);
		linkView.setLinkTextColor(0xff000000);

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