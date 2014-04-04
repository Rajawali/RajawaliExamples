package com.monyetmabuk.rajawali.tutorials;

import java.util.Map;

import rajawali.RajawaliActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.ExamplesApplication.Category;
import com.monyetmabuk.rajawali.tutorials.ExamplesApplication.ExampleItem;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class RajawaliExamplesActivity extends RajawaliActivity implements
		OnChildClickListener {

	private static final String FRAGMENT_TAG = "rajawali";
	private static final String PREF_FIRST_RUN = "RajawaliExamplesActivity.PREF_FIRST_RUN";

	private DrawerLayout mDrawerLayout;
	private ExpandableListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Configure the action bar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// Configure the drawer
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
		mDrawerList.setGroupIndicator(null);
		mDrawerList.setAdapter(new ExampleAdapter(getApplicationContext(),
				ExamplesApplication.ITEMS));
		mDrawerList.setOnChildClickListener(this);

		// Configure the drawer toggle
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.setFocusable(false);

		if (savedInstanceState == null)
			onChildClick(null, null, 0, 0, 0);

		// Open the drawer the very first run.
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (!prefs.contains(PREF_FIRST_RUN)) {
			prefs.edit().putBoolean(PREF_FIRST_RUN, false).apply();
			mDrawerLayout.openDrawer(mDrawerList);
		}
	}

	@Override
	protected void onDestroy() {
		try {
			super.onDestroy();
		} catch (Exception e) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final ExampleItem exampleItem;

		switch (item.getItemId()) {
		case R.id.menu_item_community_stream:
			exampleItem = ExamplesApplication.ITEMS.get(Category.ABOUT)[0];
			launchFragment(Category.ABOUT, exampleItem);
			return true;
		case R.id.menu_item_meet_the_team:
			exampleItem = ExamplesApplication.ITEMS.get(Category.ABOUT)[1];
			launchFragment(Category.ABOUT, exampleItem);
			return true;
		case android.R.id.home:
			if (mDrawerToggle.onOptionsItemSelected(item))
				return true;
			
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		final Category category = Category.values()[groupPosition];
		final ExampleItem exampleItem = ExamplesApplication.ITEMS.get(category)[childPosition];
		launchFragment(category, exampleItem);

		return true;
	}

	/**
	 * Launch a fragment selected from the drawer or at application start.
	 * 
	 * @param fragClass
	 */
	private void launchFragment(Category category, ExampleItem exampleItem) {
		final FragmentManager fragmentManager = getFragmentManager();

		// Close the drawer
		mDrawerLayout.closeDrawers();

		// Set fragment title
		setTitle(exampleItem.title);

		final FragmentTransaction transaction = fragmentManager.beginTransaction();
		try {
			final Fragment fragment = (Fragment) exampleItem.exampleClass.getConstructors()[0].newInstance();

			final Bundle bundle = new Bundle();
			bundle.putString(AExampleFragment.BUNDLE_EXAMPLE_URL, exampleItem.getUrl(category));
			fragment.setArguments(bundle);

			if (fragmentManager.findFragmentByTag(FRAGMENT_TAG) != null)
				transaction.addToBackStack(null);
			
			transaction.replace(R.id.content_frame, fragment, FRAGMENT_TAG);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final class ExampleAdapter extends BaseExpandableListAdapter {

		private static final int COLORS[] = new int[] { 0xFF0099CC, 0xFF9933CC,
				0xFF669900, 0xFFFF8800, 0xFFCC0000 };

		private final Map<Category, ExampleItem[]> mItems;
		private final LayoutInflater mInflater;
		private final Category[] mKeys;

		public ExampleAdapter(Context context,
				Map<Category, ExampleItem[]> items) {
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mItems = items;
			mKeys = Category.values();
		}

		@Override
		public ExampleItem getChild(int groupPosition, int childPosition) {
			return mItems.get(mKeys[groupPosition])[childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final ExampleItem item = getChild(groupPosition, childPosition);
			final ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.drawer_list_child_item, null);
				holder = new ViewHolder(convertView);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.textViewItemTitle.setText(item.title);

			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return mItems.get(mKeys[groupPosition]).length;
		}

		@Override
		public Category getGroup(int groupPosition) {
			return mKeys[groupPosition];
		}

		@Override
		public int getGroupCount() {
			return mKeys.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			final String groupName = getGroup(groupPosition).getName();
			final ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.drawer_list_group_item, null);
				holder = new ViewHolder(convertView);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.imageViewItemColor.setBackgroundColor(COLORS[groupPosition
					% COLORS.length]);
			holder.textViewItemTitle.setText(groupName);

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

	private static final class ViewHolder {
		public ImageView imageViewItemColor;
		public TextView textViewItemTitle;

		public ViewHolder(View convertView) {
			imageViewItemColor = (ImageView) convertView
					.findViewById(R.id.item_color);
			textViewItemTitle = (TextView) convertView
					.findViewById(R.id.item_text);
			convertView.setTag(this);
		}
	}
}
