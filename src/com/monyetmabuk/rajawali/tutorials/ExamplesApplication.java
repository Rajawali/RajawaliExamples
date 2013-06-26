package com.monyetmabuk.rajawali.tutorials;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Application;

import com.monyetmabuk.rajawali.tutorials.ExamplesApplication.ExampleItem.Categories;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.basic.AnimationFragment;
import com.monyetmabuk.rajawali.tutorials.examples.basic.BasicFragment;

public class ExamplesApplication extends Application {

	public static final Map<ExampleItem.Categories, ExampleItem[]> ITEMS = new HashMap<ExampleItem.Categories, ExamplesApplication.ExampleItem[]>();

	@Override
	public void onCreate() {
		super.onCreate();

		if (ITEMS.size() > 0)
			return;

		// @formatter:off
		ITEMS.put(Categories.BASIC, new ExampleItem[] { 
				new ExampleItem("Getting Started", BasicFragment.class) 
			});
		ITEMS.put(Categories.EFFECTS, new ExampleItem[] {
				new ExampleItem("Animations", AnimationFragment.class)
			});
		ITEMS.put(Categories.INTERACTIVE, new ExampleItem[] {});
		ITEMS.put(Categories.UI, new ExampleItem[] {});
		ITEMS.put(Categories.OPTIMIZATIONS, new ExampleItem[] {});
		// @formatter:on
	}

	public static final class ExampleItem {

		public enum Categories {
			BASIC, EFFECTS, INTERACTIVE, UI("UI"), OPTIMIZATIONS;

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
