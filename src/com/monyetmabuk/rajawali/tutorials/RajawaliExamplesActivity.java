package com.monyetmabuk.rajawali.tutorials;

import java.util.Map;

import rajawali.RajawaliActivity;
import rajawali.RajawaliFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.ExamplesApplication.ExampleItem;
import com.monyetmabuk.rajawali.tutorials.ExamplesApplication.ExampleItem.Categories;
import com.monyetmabuk.rajawali.tutorials.examples.general.BasicFragment;

public class RajawaliExamplesActivity extends RajawaliActivity implements
		OnChildClickListener, OnGroupExpandListener {

	private static final String FRAGMENT_TAG = "rajawali";

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
		mDrawerList.setOnGroupExpandListener(this);

		// Configure the drawer toggle
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				closeOtherGroups(-1);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null)
			launchFragment(BasicFragment.class);
	}

	@Override
	protected void onDestroy() {
		try {
			super.onDestroy();
		} catch (Exception e) {
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item))
			return true;

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
		final ExampleItem item = ExamplesApplication.ITEMS.get(Categories
				.values()[groupPosition])[childPosition];

		launchFragment(item.exampleClass);

		// Close the drawer
		mDrawerLayout.closeDrawers();

		// Close any groups that may still be open
		closeOtherGroups(groupPosition);

		return true;
	}

	@Override
	public void onGroupExpand(int groupPosition) {
		closeOtherGroups(groupPosition);
	}

	/**
	 * Close all open groups in the drawer other than the currently selected group.
	 * 
	 * @param groupToKeepOpen
	 */
	private void closeOtherGroups(int groupToKeepOpen) {
		for (int i = 0, j = mDrawerList.getCount(); i < j; i++) {
			if (i != groupToKeepOpen)
				mDrawerList.collapseGroup(i);
		}
	}

	/**
	 * Launch a fragment selected from the drawer or at application start.
	 * 
	 * @param fragClass
	 */
	private void launchFragment(Class<? extends RajawaliFragment> fragClass) {
		final FragmentManager fragmentManager = getFragmentManager();
		final Fragment fragment;

		final FragmentTransaction transaction = fragmentManager
				.beginTransaction();
		try {
			Fragment oldFrag = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
			if (oldFrag != null)
				transaction.remove(oldFrag);

			fragment = (Fragment) fragClass.getConstructors()[0].newInstance();
			transaction.add(R.id.content_frame, fragment, FRAGMENT_TAG);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final class ExampleAdapter extends BaseExpandableListAdapter {

		private static final int COLORS[] = new int[] { 0xFF0099CC, 0xFF9933CC,
				0xFF669900, 0xFFFF8800, 0xFFCC0000 };

		private final Map<ExampleItem.Categories, ExampleItem[]> mItems;
		private final LayoutInflater mInflater;
		private final Categories[] mKeys;

		public ExampleAdapter(Context context,
				Map<ExampleItem.Categories, ExampleItem[]> items) {
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mItems = items;
			mKeys = ExampleItem.Categories.values();
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return mItems.get(mKeys[groupPosition])[childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final ExampleItem item = (ExampleItem) getChild(groupPosition,
					childPosition);
			final ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.drawer_list_child_item, null);
				holder = new ViewHolder(convertView);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.imageViewItemColor.setBackgroundColor(COLORS[groupPosition
					% COLORS.length]);
			holder.textViewItemTitle.setText(item.title);

			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return mItems.get(mKeys[groupPosition]).length;
		}

		@Override
		public Object getGroup(int groupPosition) {
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
			final String groupName = ((Categories) getGroup(groupPosition))
					.getName();
			final ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.drawer_list_group_item, null);
				holder = new ViewHolder(convertView);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.textViewItemTitle.setText(groupName);

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
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
