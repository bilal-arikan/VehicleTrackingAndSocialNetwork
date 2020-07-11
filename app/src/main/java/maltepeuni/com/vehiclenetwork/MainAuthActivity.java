package maltepeuni.com.vehiclenetwork;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainAuthActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	public static MainFragment mainFragment;
	public static SearchFragment searchFragment;
	public static DenounceFragment denounceFragment;
	public static CarListFragment carListFragment;
	public static ContactFragment contactFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_auth);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_m, contactFragment);
				transaction.commit();
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		mainFragment = new MainFragment();
		searchFragment = new SearchFragment();
		denounceFragment = new DenounceFragment();
		carListFragment = new CarListFragment();
		contactFragment = new ContactFragment();

		Change(denounceFragment);
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_auth, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();


		if (id == R.id.nav_main) {
			Change(mainFragment);
			setTitle(getString(R.string.app_name));
		} else if (id == R.id.nav_search) {
			Change(searchFragment);
			setTitle("Authorized Vehicle Search");
		} else if (id == R.id.nav_instructions) {
			Intent intent = new Intent(this, InstructionsAuthActivity.class);
			startActivity(intent);
		} else if (id == R.id.nav_denounce) {
			Change(denounceFragment);
			setTitle("Denounced Vehicles");
		}else if (id == R.id.nav_logout) {
			Intent intent = new Intent(this, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else /*if (id == R.id.nav_exit) */{
			System.exit(1);
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	public void Change(android.app.Fragment f ){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_m, f);
		transaction.addToBackStack(null);
		transaction.commit();
	}


	//////////////////////////////////////////////////////////////////////////////////////////
	public static class MainFragment extends android.app.Fragment {
		public static MainAuthActivity.MainFragment newInstance() {
			return new MainAuthActivity.MainFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_denounceds, container, false);
			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class DenounceFragment extends android.app.Fragment {
		public static MainAuthActivity.DenounceFragment newInstance() {
			return new MainAuthActivity.DenounceFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_denounceds, container, false);
			rootView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_m, MainAuthActivity.contactFragment);
					transaction.addToBackStack(null);
					transaction.commit();
					getActivity().setTitle("Contact Screen");
				}
			});
			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class SearchFragment extends android.app.Fragment {
		public static MainAuthActivity.SearchFragment newInstance() {
			return new MainAuthActivity.SearchFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_search, container, false);
			Button addCar = (Button)rootView.findViewById(R.id.buttonSearchCar);
			addCar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_m, MainAuthActivity.carListFragment);
					transaction.addToBackStack(null);
					transaction.commit();
					getActivity().setTitle(getString(R.string.app_name));
				}
			});
			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class CarListFragment extends android.app.Fragment {
		public static MainAuthActivity.CarListFragment newInstance() {
			return new MainAuthActivity.CarListFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_car_list, container, false);
			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class ContactFragment extends android.app.Fragment {
		public static MainAuthActivity.ContactFragment newInstance() {
			return new MainAuthActivity.ContactFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_contact, container, false);
			Button addCar = (Button)rootView.findViewById(R.id.buttonProcessed);
			addCar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Toast.makeText(getActivity(),"The Denounce is in Process now.",Toast.LENGTH_LONG).show();
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_m, MainAuthActivity.carListFragment);
					transaction.addToBackStack(null);
					transaction.commit();
					getActivity().setTitle(getString(R.string.app_name));
				}
			});
			return rootView;
		}
	}
}
