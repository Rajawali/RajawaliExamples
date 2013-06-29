package com.monyetmabuk.rajawali.tutorials;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Application;

import com.monyetmabuk.rajawali.tutorials.ExamplesApplication.ExampleItem.Categories;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.animation.AnimatedSpritesFragment;
import com.monyetmabuk.rajawali.tutorials.examples.animation.AnimationFragment;
import com.monyetmabuk.rajawali.tutorials.examples.animation.BezierFragment;
import com.monyetmabuk.rajawali.tutorials.examples.animation.CatmullRomFragment;
import com.monyetmabuk.rajawali.tutorials.examples.animation.MD2Fragment;
import com.monyetmabuk.rajawali.tutorials.examples.animation.SkeletalAnimationMD5Fragment;
import com.monyetmabuk.rajawali.tutorials.examples.effects.ParticlesFragment;
import com.monyetmabuk.rajawali.tutorials.examples.effects.TouchRipplesFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.BasicFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.ChaseCameraFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.CollisionDetectionFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.LinesFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.MultipleLightsFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.SkyboxFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.TerrainFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.ThreeSixtyImagesFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.UsingGeometryDataFragment;
import com.monyetmabuk.rajawali.tutorials.examples.interactive.AccelerometerFragment;
import com.monyetmabuk.rajawali.tutorials.examples.interactive.ObjectPickingFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.BumpMappingFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.CustomMaterialShaderFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.CustomVertexShaderFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.MaterialsFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.SphereMapFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.ToonShadingFragment;
import com.monyetmabuk.rajawali.tutorials.examples.optimizations.Optimized2000PlanesFragment;
import com.monyetmabuk.rajawali.tutorials.examples.parsers.FBXFragment;
import com.monyetmabuk.rajawali.tutorials.examples.parsers.LoadModelFragment;
import com.monyetmabuk.rajawali.tutorials.examples.ui.TransparentSurfaceFragment;
import com.monyetmabuk.rajawali.tutorials.examples.ui.TwoDimensionalFragment;
import com.monyetmabuk.rajawali.tutorials.examples.ui.UIElementsFragment;

public class ExamplesApplication extends Application {

	public static final Map<ExampleItem.Categories, ExampleItem[]> ITEMS = new HashMap<ExampleItem.Categories, ExamplesApplication.ExampleItem[]>();

	@Override
	public void onCreate() {
		super.onCreate();

		if (ITEMS.size() > 0)
			return;

		// @formatter:off
		ITEMS.put(Categories.GENERAL, new ExampleItem[] { 
				new ExampleItem("Getting Started", BasicFragment.class) 
				, new ExampleItem("Skybox", SkyboxFragment.class)
				, new ExampleItem("Collision Detection", CollisionDetectionFragment.class)
				, new ExampleItem("Multiple Lights", MultipleLightsFragment.class)
				, new ExampleItem("Lines", LinesFragment.class)
				, new ExampleItem("Chase Camera", ChaseCameraFragment.class)
				, new ExampleItem("Using Geometry Data", UsingGeometryDataFragment.class)
				, new ExampleItem("360 Images", ThreeSixtyImagesFragment.class)
				, new ExampleItem("Terrain", TerrainFragment.class)
			});
		ITEMS.put(Categories.EFFECTS, new ExampleItem[] {
				new ExampleItem("Particles", ParticlesFragment.class)
				, new ExampleItem("Touch Ripples", TouchRipplesFragment.class)
			});
		ITEMS.put(Categories.INTERACTIVE, new ExampleItem[] {
				new ExampleItem("Using The Accelerometer", AccelerometerFragment.class)
				, new ExampleItem("Object Picking", ObjectPickingFragment.class)
			});
		ITEMS.put(Categories.UI, new ExampleItem[] {
				new ExampleItem("UI Elements", UIElementsFragment.class)
				, new ExampleItem("2D Renderer", TwoDimensionalFragment.class)
				, new ExampleItem("Transparent GLSurfaceView", TransparentSurfaceFragment.class)
			});
		ITEMS.put(Categories.OPTIMIZATIONS, new ExampleItem[] {
				new ExampleItem("2000 Textured Planes", Optimized2000PlanesFragment.class)
			});
		ITEMS.put(Categories.PARSERS, new ExampleItem[] {
				new ExampleItem("Load OBJ Model", LoadModelFragment.class)
				, new ExampleItem("FBX Scene Importer", FBXFragment.class)
			});
		ITEMS.put(Categories.ANIMATION, new ExampleItem[] {
				new ExampleItem("Animation", AnimationFragment.class)
				, new ExampleItem("Bezier Path Animation", BezierFragment.class)
				, new ExampleItem("MD2 Animation", MD2Fragment.class)
				, new ExampleItem("Catmul-Rom Splines", CatmullRomFragment.class)
				, new ExampleItem("Animated Sprites", AnimatedSpritesFragment.class)
				, new ExampleItem("Skeletal Animation (MD5)", SkeletalAnimationMD5Fragment.class)
			});
		ITEMS.put(Categories.MATERIALS, new ExampleItem[] {
				new ExampleItem("Materials", MaterialsFragment.class)
				, new ExampleItem("Custom Material", CustomMaterialShaderFragment.class)
				, new ExampleItem("Bump Mapping", BumpMappingFragment.class)
				, new ExampleItem("Toon Shading", ToonShadingFragment.class)
				, new ExampleItem("Custom Vertex Shader", CustomVertexShaderFragment.class)
				, new ExampleItem("Sphere Mapping", SphereMapFragment.class)
			});
		// @formatter:on
	}

	public static final class ExampleItem {

		public enum Categories {
			GENERAL, EFFECTS, INTERACTIVE, UI("UI"), OPTIMIZATIONS, PARSERS, ANIMATION, MATERIALS;

			private String name;

			Categories() {
				name = toString().toLowerCase(Locale.getDefault());
				name = name.substring(0, 1).toUpperCase(Locale.getDefault())
						+ name.substring(1, name.length());
			}

			Categories(String name) {
				this.name = name;
			}

			public String getName() {
				return name;
			}

		}

		public Class<? extends AExampleFragment> exampleClass;
		public String title;

		public ExampleItem(String title,
				Class<? extends AExampleFragment> exampleClass) {
			this.title = title;
			this.exampleClass = exampleClass;
		}
	}

}
