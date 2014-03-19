package it.francescopezzato.android.listbottompullup.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.MessageFormat;

public class MainActivity extends Activity {

	private ListView mList;

	private TextView mDebug1;
	private TextView mDebug2;

	String[] values = new String[30];

	static {

	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (int i = 0; i < 30; i++) {
			values[i] = "i:" + i;
		}

		setContentView(R.layout.activity_main);
		mList = (ListView) findViewById(R.id.list);

		mDebug1 = (TextView) findViewById(R.id.debug_textview1);
		mDebug2 = (TextView) findViewById(R.id.debug_textview2);


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);


		View footer = getLayoutInflater().inflate(R.layout.list_footer, null);
		mList.addFooterView(footer);
		mList.setAdapter(adapter);


		mList.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				String debug1 = MessageFormat.format("firstVisibleItem {0} visibleItemCount {1} totalItemCount{2}", firstVisibleItem, visibleItemCount, totalItemCount);
				String debug2 = "-";

				int containerHeight = mList.getHeight();

				//find the fake footer
				View fakeFooter = findViewById(R.id.overlayed_footer);

				if (firstVisibleItem + visibleItemCount == totalItemCount) {
					//showing  the real footer

					//find the real footer
					View realfooter = view.getChildAt(visibleItemCount - 1);

					//magician stuff

					int FAKE_FOOTER_MAX_HEIGHT = 50;

					if (fakeFooter.getHeight() < FAKE_FOOTER_MAX_HEIGHT) {
						//reduce the fake footer height
						fakeFooter.setTop(realfooter.getTop());
						fakeFooter.setVisibility(View.VISIBLE);
					} else {
						//no op  - leave as is:	the fake footer visible with it's max height
					}


					if (realfooter != null) {
						debug2 = MessageFormat.format("lastChild.getTop() {0} lastChild.getBottom() {1} ", realfooter.getTop(), realfooter.getBottom());
					}


				} else {
					//real footer not inside the listview bounds
					fakeFooter.setVisibility(View.GONE);
				}


				log(debug1, debug2);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private void log(String debug1, String debug2) {
		if (debug1 != null) {
			mDebug1.setText(debug1);
		}
		if (debug2 != null) {
			mDebug2.setText(debug2);
		}
	}

}
